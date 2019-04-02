const mongoose = require('mongoose')

const MONGO_USERNAME = process.env.MONGO_USERNAME || 'root'
const MONGO_PASSWORD = process.env.MONGO_PASSWORD || 'password'
const MONGO_HOST = process.env.MONGO_HOST || 'mongo-service'
const MONGO_PORT = process.env.MONGO_PORT || '27017'
const MONGO_URI = `mongodb://${MONGO_USERNAME}:${MONGO_PASSWORD}@${MONGO_HOST}:${MONGO_PORT}/admin`

const db = mongoose.connection;
db.on('error', (err) => {
    console.error(`unable to connect to ${MONGO_URI}: ${err}`);
    setTimeout(connectToMongoDB, 1000);
});
db.once('open', () => {
  console.log(`connected to ${MONGO_URI}`);
});

const connectToMongoDB = () => {
    mongoose.connect(MONGO_URI, {
        useNewUrlParser: true,
        connectTimeoutMS: 2000
    });
};
connectToMongoDB();

const messageSchema = mongoose.Schema({
    name: { type: String, required: [true, 'Name is required'] },
    body: { type: String, required: [true, 'Message Body is required'] },
    timestamps: {}
});

const messageModel = mongoose.model('Message', messageSchema);

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
    message.save((err) => {
        if (err) { throw err }
    })
};

const create = (params) => {
    try {
        console.log("creating...")
        const msg = construct(params)
        const validationError = msg.validateSync()
        if (validationError) { throw validationError }
        console.log("msg: " + msg)
        save(msg)
    } catch (exception) {
        throw exception
    }
}

module.exports = {
    create: create,
    messageModel: messageModel
}

