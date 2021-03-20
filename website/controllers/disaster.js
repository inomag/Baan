const Disaster = require('../models/disaster');

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