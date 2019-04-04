const express = require('express')
const path = require('path');
const app = express();
const bodyParser = require('body-parser')
const axios = require('axios')

const util = require('./utils')

const GUESTBOOK_API_ADDR = process.env.GUESTBOOK_API_ADDR

const BACKEND_URI = `http://${GUESTBOOK_API_ADDR}/messages`

const TRANSLATE_API_ADDR = process.env.TRANSLATE_API_ADDR

var TRANSLATE_URI = `http://${TRANSLATE_API_ADDR}/?text=`

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
        console.log('got response: ' + JSON.stringify(response.data))
        var entries = response.data || [];
        var strs = [];
        for (var i = 0; i < entries.length; i++) {
          strs.push(entries[i].body);
        }

        axios.get(encodeURI(TRANSLATE_URI + JSON.stringify(strs)))
          .then(resp => {
            console.log('got response: ' + JSON.stringify(resp.data))
            var translations = resp.data || [];
            var messages = [];
            for (var i = 0; i < translations.length; i++) {
              var e = entries[i];
              var t = translations[i];
              messages.push({
                name: e.name,
                body: e.body,
                timestamp: e.timestamp,
                english: t.english,
                german: t.english
              })
            }
            const result = util.formatMessages(messages)
            res.render("home", {messages: result})
          })
          .catch(err => {
            console.log('error with promise: ' + err)
          })
      }).catch(error => {
        console.log('error with promise: ' + error)
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
