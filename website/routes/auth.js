const { Router } = require('express');
const router = Router();

const { phone_signup, verifyOTP, get_all_users, get_user_by_phone_no, get_affected_user ,admin_phone_signup, admin_otp_verify, signout } = require('../controllers/auth');

// @route   POST api/signup
// @desc    Send OTP to the user's phone number
// @access  Public
router.post('/signup', phone_signup);

// @route   POST api/verifyOTP
// @desc    Verify the OTP put by the user
//           if the OTP is okay, save the data from the user to database
//           otherwise, show error
// @access  Public
router.post('/verifyOTP', verifyOTP);

// @route   GET api/get/all/users
// @desc    Get all users from the database
// @access  Public
router.get('/get/all/users', get_all_users);
router.get('/get/affected-user', get_affected_user);

// @route   GET api/get/user/:phone
// @desc    Get user by phone number
// @access  Public
router.get('/get/user/:phone', get_user_by_phone_no);

// @route   GET api/admin/signup
// @desc    Send OTP to the admin's phone number
// @access  Private
router.post('/admin/signup', admin_phone_signup);

// @route   GET api/admin/verifyOTP
// @desc    Verify the OTP put by the admin
//           if the OTP is okay, save the data from the admin to database
//           otherwise, show error
// @access  Private
router.post('/admin/verifyOTP', admin_otp_verify);

// @route   GET api/signout
// @desc    Signout the user or admin
// @access  Private(only for logged is users)
router.get('/signout', signout);

module.exports = router;