const mongoose = require('mongoose')

const messageSchema = mongoose.Schema({
    name: {type: String, required: [true, 'Name is required']},
    body: {type: String, required: [true, 'Message Body is required']},
    timestamps: {}
});

const messageModel = mongoose.model('Message', messageSchema);

module.exports = messageModel

