const express = require('express');
const bodyParser = require('body-parser');

const app = express();
const router = express.Router();
const PORT = process.env.PORT || 8080;



router.use(bodyParser.json());

router.get('/hi', (req, res) => {
    res.send('get request here')
});

module.exports = router;
