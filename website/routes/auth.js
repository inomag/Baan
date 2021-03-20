const { Router } = require('express');
const router = Router();

const { phone_signup, verifyOTP, get_all_users, get_user_by_phone_no } = require('../controllers/auth');

// @route   POST api/signup
// @desc    Send OTP to the user's phone number
// @access  Public
router.post('/signup', phone_signup);

// @route   POST api/verifyOTP
// @desc    Verify the OTP put by the user
//           if the OTP is okay, save the data from the user to mongodb database
//           otherwise, show error
// @access  Public
router.post('/verifyOTP', verifyOTP);

// @route   GET api/get/all/users
// @desc    Get all users from the database
// @access  Public
router.get('/get/all/users', get_all_users);

// @route   GET api/get/user/:phone
// @desc    Get user by phone number
// @access  Public
router.get('/get/user/:phone', get_user_by_phone_no);

module.exports = router;