/**
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
