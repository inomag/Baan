const dotenv = require('dotenv');
dotenv.config();

// Credentials for Twilio Service
const accountSid = process.env.TWILIO_ACCOUNT_SID;
const authToken = process.env.TWILIO_AUTH_TOKEN;
const client = require('twilio')(accountSid, authToken);

// Import model
const User = require('../models/user');

const maxAge = 3 * 24 * 60 * 60;
const createToken = user => {
    return jwt.sign({ user }, process.env.JWT_SECRET, {
        expiresIn: maxAge
    });
};

exports.phone_signup = async (req, res) => {
    try {
        console.log(req.body);
        const data = await client.verify.services(process.env.TWILIO_SERVICE_ID).verifications.create({
            to: `+91${req.body.phone}`,
            channel: 'sms'
        });

        if (data.status === 'pending') {
            res.json({ success: 'Please check your inbox for OTP!' });
        } else {
            return res.json({ error: 'Sorry! Please input a valid phone number!' });
        }
    } catch (err) {
        console.log(err);
        return res.json({ error: 'Some error occured!!!', err });
    }
};

exports.verifyOTP = async (req, res) => {
    try {
        console.log(req.body);
        const { phone, code, name, default_loc } = req.body;
        const data = await client.verify.services(process.env.TWILIO_SERVICE_ID).verificationChecks.create({
            to: `+91${phone}`,
            code
        });

        if (data.status === 'approved') {

            const user = await User.findOne({ phone });

            if (user) {
                const token = await createToken(user);

                res.cookie('INUNDATION', token, { httpOnly: true, maxAge: maxAge * 1000 });

                return res.status(404).json({
                    user,
                    error: 'This phone is already registered!'
                });
            } else {
                const newUser = new User({
                    phone,
                    name,
                    default_loc
                });
                await newUser.save();

                const token = await createToken(newUser);
                res.cookie('INUNDATION', token, { httpOnly: true, maxAge: maxAge * 1000 });

                return res.json({
                    user: newUser,
                    success: 'User authenticated successfully!'
                });
            }
        } else {
            return res.json({
                error: 'Please input the correct OTP!'
            });
        }
    } catch (err) {
        console.log(err);
        return res.json({ error: 'Some error occured!' });
    }
};

exports.get_all_users = async (req, res) => {
    const users = await User.find({
        role: {
            $ne: 1
        }
    });
    res.json({ users });
}

exports.get_user_by_phone_no = async (req, res) => {
    console.log(req.params);
    const user = await User.findOne({ phone: req.params.phone });
    res.json({ user });
}

exports.admin_phone_signup = async (req, res) => {
    try {
        console.log(req.body);
        const data = await client.verify.services(process.env.TWILIO_SERVICE_ID).verifications.create({
            to: `+91${req.body.phone}`,
            channel: 'sms'
        });

        if (data.status === 'pending') {
            res.json({ success: 'Please check your inbox for OTP!' });
        } else {
            return res.json({ error: 'Sorry! Please input a valid phone number!' });
        }
    } catch (err) {
        return res.json({ error: 'Some error occured!!!', err });
    }
};

exports.admin_otp_verify = async (req, res) => {
    try {
        console.log(req.body);
        const { phone, code, name, default_loc, role } = req.body;
        const data = await client.verify.services(process.env.TWILIO_SERVICE_ID).verificationChecks.create({
            to: `+91${phone}`,
            code
        });

        if (data.status === 'approved') {

            const user = await User.findOne({ phone });

            if (user) {
                const token = await createToken(user);

                res.cookie('INUNDATION', token, { httpOnly: true, maxAge: maxAge * 1000 });

                return res.status(400).json({
                    user,
                    error: 'This phone is already registered!'
                });
            } else {
                const newUser = new User({
                    phone,
                    name,
                    role,
                    default_loc
                });
                await newUser.save();

                const token = await createToken(newUser);
                res.cookie('INUNDATION', token, { httpOnly: true, maxAge: maxAge * 1000 });

                return res.json({
                    user: newUser,
                    success: 'User authenticated successfully!'
                });
            }
        } else {
            return res.json({
                error: 'Please input the correct OTP!'
            });
        }
    } catch (err) {
        console.log(err);
        return res.json({ error: 'Some error occured!' });
    }
};