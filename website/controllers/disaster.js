const distance = require('distance-matrix-api');

const Disaster = require('../models/disaster');
const User = require('../models/user');

exports.set_location = async (req, res) => {
    try {
        console.log(req.body);
        const { coordinates } = req.body;
        const disaster = new Disaster({
            coordinates
        });
        await disaster.save();
        res.json({ flooded_location: disaster });
    } catch (err) {
        return res.json({ error: 'Some error occured!!!', err });
    }
};

exports.get_locations = async (req, res) => {
    const disasters = await Disaster.find();
    res.json({ flooded_locations: disasters });
}

exports.compare_distance = async (req, res) => {
    try {
        const disasters = await Disaster.find().select('coordinates');
        const userLoc = await User.find().select('default_loc');

        const origins = [];
        const destinations = [];

        userLoc.map(uLoc => {
            origins.push(uLoc.default_loc);
        });

        disasters.map(disaster => {
            destinations.push(disaster.coordinates);
        });

        distance.key('AlphaDMACMzobKdnQbF9WWL0han6Bjv6rcQqv19H');
        distance.units('metric');
        const userLocations = [];
        const userLocationsArray = [];

        distance.matrix(origins, destinations, async (err, distances) => {
            if (err) {
                return res.json({ error: 'Something went wrong!' });
            }
            if (!distances) {
                return res.json({ error: 'Distance not measurable!' });
            }
            if (distances.status == 'OK') {
                for (let i = 0; i < origins.length; i++) {
                    for (let j = 0; j < destinations.length; j++) {
                        if (distances.rows[0].elements[j].status == 'OK') {
                            const distance = await distances.rows[i].elements[j].distance.text;
                            const result = await distance.split(' ');
                            if (result[0] > 10) {
                                userLocations.push(origins[i]);
                                const users = await User.find({
                                    default_loc: origins[i],
                                    role: { $ne: 1 }
                                });
                                users[0].affected_verify = true;
                                await users[0].save()
                                userLocationsArray.push(users[0]);
                            }
                        } else {
                            console.log('Not reachable!');
                        }
                    }
                }

                const distinctUsers = Array.from(new Set(userLocationsArray.map(user => user.phone)))
                    .map(phone => {
                        return {
                            phone,
                            name: userLocationsArray.find(user => user.phone === phone).name,
                            role: userLocationsArray.find(user => user.phone === phone).role,
                            affected: userLocationsArray.find(user => user.phone === phone).affected,
                            default_loc: userLocationsArray.find(user => user.phone === phone).default_loc
                        }
                    });

                return res.json({
                    size: distinctUsers.length,
                    success: 'Notify users living within this location!',
                    distinctUsersAffected: distinctUsers
                });
            }
        });
    } catch (error) {
        console.log(error);
        return res.json({ error });
    }
}

exports.send_aware_message = async (req, res) => {
    try {
        const users = await User.find({
            affected_verify: true,
            role: {
                $ne: 1
            }
        });

        users.map(user => {
            client.messages
                .create({
                    body: 'Beware! Your area is identified as severly flood-affected by Govt. of Assam. We hope you are safe and healthy! - Team Inundation, Govt. of Assam',
                    messagingServiceSid: 'MG3b2e919b1ef67c8abce7438bd6d89374',
                    to: `+91${user.phone}`
                }).then(message => console.log(message))
                .done();
        });
        res.json({ success: 'Sent awareness message sucessfully!', users });
    } catch (err) {
        return res.json({ err });
    }
}