const express = require('express');
const bodyParser = require('body-parser');

const router = express.Router();

const messageModel = require('./messages')

router.use(bodyParser.json());

router.get('/', (req, res) => {
    res.send('get request here');
    res.status(200);
});

router.get('/messages', (req, res) => {
    res.status(200);
    messageModel.find({}, function (err, messages) {
        if (err) {
            console.err(err)
            res.status(503).json(err)
        } else {
            const result = []
            messages.forEach(function (message) {
                if (message.name && message.body) {
                    result.push({'name': message.name, 'body': message.body})
                }
            });
            res.json(result);
        }
    });
});

router.post('/messages', (req, res) => {
    var title = req.body.title;
    var body = req.body.body;
    console.log('req title : ' + title)
    console.log('req body: ' + body)
    var message = new messageModel({ name: title, body: body })

    validationError = message.validateSync()
    if (validationError) {
        console.log('validation err: ' + validationError)
        res.status(400).json(validationError)
        return
    }

    message.save(function (err, message) {
        if (err) {
            console.log('could not save: ' + err)
            res.send(err)
            res.status(503)
        } else {
            console.log('made msg:' + message)
            res.status(200).json(message)
        }
    })
});

module.exports = router;
