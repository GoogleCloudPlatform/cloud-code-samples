const express = require('express')
const path = require('path');
const app = express();
const bodyParser = require('body-parser')
const axios = require('axios')

const util = require('./utils')

const GUESTBOOK_API_ADDR = process.env.GUESTBOOK_API_ADDR

const BACKEND_URI = `http://${GUESTBOOK_API_ADDR}/messages`

app.set("view engine", "pug")
app.set("views", path.join(__dirname, "views"))

const router = express.Router()
app.use(router)

app.use(express.static('public'))
router.use(bodyParser.urlencoded({ extended: false }))

// Application will fail if environment variables are not set
if(!process.env.PORT) {
  const errMsg = "PORT environment variable is not defined"
  console.error(errMsg)
  throw new Error(errMsg)
}

if(!process.env.GUESTBOOK_API_ADDR) {
  const errMsg = "GUESTBOOK_API_ADDR environment variable is not defined"
  console.error(errMsg)
  throw new Error(errMsg)
}

// Starts an http server on the $PORT environment variable
const PORT = process.env.PORT;
app.listen(PORT, () => {
  console.log(`App listening on port ${PORT}`);
  console.log('Press Ctrl+C to quit.');
});

// Handles GET request to /
router.get("/", (req, res) => {
    // retrieve list of messages from the backend, and use them to render the HTML template
    axios.get(BACKEND_URI)
      .then(response => {
        console.log(`response from ${BACKEND_URI}: ` + response.status)
        const result = util.formatMessages(response.data)
        res.render("home", {messages: result})
      }).catch(error => {
        console.error('error: ' + error)
    })
});

// Handles POST request to /post
router.post('/post', (req, res) => {
  console.log(`received request: ${req.method} ${req.url}`)

  // validate request
  const name = req.body.name
  const message = req.body.message
  if (!name || name.length == 0) {
    res.status(400).send("name is not specified")
    return
  }

  if (!message || message.length == 0) {
    res.status(400).send("message is not specified")
    return
  }

  // send the new message to the backend and redirect to the homepage
  console.log(`posting to ${BACKEND_URI}- name: ${name} body: ${message}`)
  axios.post(BACKEND_URI, {
    name: name,
    body: message
  }).then(response => {
      console.log(`response from ${BACKEND_URI}` + response.status)
      res.redirect('/')
  }).catch(error => {
      console.error('error: ' + error)
  })
});
