const mongoose = require('mongoose')

const GUESTBOOK_DB_ADDR = process.env.GUESTBOOK_DB_ADDR || 'mongodb://localhost:27017/test'; 
const mongoURI = "mongodb://" + GUESTBOOK_DB_ADDR + "/guestbook"

const db = mongoose.connection;
db.on('error', (err) => {
    console.error(`unable to connect to ${mongoURI}: ${err}`);
    setTimeout(connectToMongoDB, 1000);
});
db.once('open', () => {
  console.log(`connected to ${mongoURI}`);
});

const connectToMongoDB = () => {
    mongoose.connect(mongoURI, {
        useNewUrlParser: true,
        connectTimeoutMS: 2000,
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

