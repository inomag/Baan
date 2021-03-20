const { Router } = require('express');
const router = Router();

const { set_location, get_locations, compare_distance, send_aware_message } = require('../controllers/disaster');

// @route   POST api/admin/set-locations
// @desc    Create a location with coordinates(latitude, longitude) and save them to database
// @access  Private(for admin only)
router.post('/admin/set-locations', set_location);

// @route   GET api/get-locations
// @desc    Get all locations from the database
// @access  Public
router.get('/get-locations', get_locations);

// @route   GET api/compare-distance
// @desc    Check if any user is within the flood affected locations
// @access  Private
router.get('/compare-distance', compare_distance);

// @route   GET api/compare-distance
// @desc    Send awareness message to all affected users
// @access  Private
router.get('/send-awareness-message', send_aware_message);

module.exports = router;