const { Router } = require('express');
const router = Router();

const { phone_signup, verifyOTP } = require('../controllers/auth');

//SIGNUP
//Send OTP
router.post('/signup', phone_signup);
//Verify OTP 
router.post('/verifyOTP', verifyOTP);

module.exports = router;