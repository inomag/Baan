const mongoose = require('mongoose');

const DisasterSchema = mongoose.Schema({
    coordinates: {
        type: String
    },
    level: {
        type: Number,
        default: 1
    }
});

module.exports = mongoose.model('disaster', DisasterSchema);