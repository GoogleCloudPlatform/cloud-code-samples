const mongoose = require('mongoose')
const MONGO_URI = process.env.DB_ADDRESS || 'mongodb://localhost:27017/test';
mongoose.connect(MONGO_URI, { useNewUrlParser: true })

const messageSchema = mongoose.Schema({
    name: { type: String, required: [true, 'Name is required'] },
    body: { type: String, required: [true, 'Message Body is required'] },
    timestamps: {}
});

const messageModel = mongoose.model('Message', messageSchema);

const getAll = () => {
    // messageModel.find({}, null, { sort: { '_id': -1 } }, function (err, messages) {
    //     messageHandler(messages)
    // });

    const result = async function () {
        return await messageModel.find({}, null, { sort: { '_id': -1 } })
    }
}


const construct = (params) => {
    const name = params.name
    const body = params.body
    console.log('name : ' + name)
    console.log('body: ' + body)
    const message = new messageModel({ name: name, body: body })
    return message
};

const save = (message) => {
    console.log("saving message...")
    message.save(function (err) {
        if (err) { throw err }
    })
};

const create = (params) => {
    try {
        console.log("creating...")
        const msg = construct(params)
        validationError = msg.validateSync()
        if (validationError) { throw validationError }
        console.log("msg: " + msg)
        save(msg)
    } catch (exception) {
        throw exception
    }
}
const messageHandler = (messages) => {
    let list = []
    messages.forEach(function (message) {
        if (message.name && message.body) {
            list.push({ 'name': message.name, 'body': message.body, 'timestamp': message._id.getTimestamp() })
        }
    });
    return list
}

module.exports = {
    create: create,
    getAll: getAll,
}

