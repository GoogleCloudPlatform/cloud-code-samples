const express = require('express');
const bodyParser = require('body-parser');

const router = express.Router();

const Message = require('./messages')

router.use(bodyParser.json());

router.get('/', (req, res) => {
    res.send('get request here');
    res.status(200);
});

router.get('/messages', (req, res) => {
    try {
        const result = Message.getAll()
        res.status(200).json(result)
    } catch (exception) {
        res.status(503).json(exception)
    }
});

router.post('/messages', (req, res) => {
    try {
        const msg = Message.create(({name: req.body.name, body: req.body.body}))
        res.status(200).json(msg)
    } catch (err) {
        if (err.name == "ValidationError") {
            console.log('validation err: ' + validationError)
            res.status(400).json(validationError)
        } else {
            console.log('could not save: ' + err)
            res.status(503).json(err)
        }
    }
});

module.exports = router;
