const express = require('express')
const path = require('path');
const moment = require('moment')
const app = express();
const bodyParser = require('body-parser')
const axios = require('axios')

const util = require('./utils')

const GUESTBOOK_API_ADDR = process.env.GUESTBOOK_API_ADDR || 'localhost:8080'

const BACKEND_URI = `http://${GUESTBOOK_API_ADDR}/messages`

app.set("view engine", "pug")

app.set("views", path.join(__dirname, "views"))

const router = express.Router()

app.use(router)

app.use(express.static('public'))
router.use(bodyParser.urlencoded({ extended: false }))

// starts an http server on the $PORT environment variable
const PORT = process.env.PORT || 8001;
app.listen(PORT, () => {
  console.log(`App listening on port ${PORT}`);
  console.log('Press Ctrl+C to quit.');
});

router.get("/", (req, res) => {
    // retrieve list of messages from the backend, and use them to render the HTML template
    axios.get(BACKEND_URI)
      .then(response => {
        console.log('got response: ' + response.data)
        const result = util.formatMessages(response.data)
        res.render("home", {messages: result})
      }).catch(error => {
        console.log('error with promise: ' + error)
    })
});

router.post('/post', (req, res, next) => {
  // send the new message to the backend and redirect to the homepage
  console.log(req.params)
  console.log(req.body)

  axios.post(BACKEND_URI, {
    name: req.body.name,
    body: req.body.message
  }).then(response => {
      console.log('got response: ' + response.data)
      res.redirect('/')
  }).catch(error => {
      console.log('error with promise: ' + error)
  })
});



