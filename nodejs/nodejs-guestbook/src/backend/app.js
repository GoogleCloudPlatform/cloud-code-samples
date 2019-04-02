const express = require('express')
const app = express()
const routes = require('./routes')
const PORT = process.env.PORT
const messages = require('./routes/messages')

app.use('/', routes)

if(!process.env.PORT) {
  const errMsg = "PORT environment variable is not defined"
  console.error(errMsg)
  throw new Error(errMsg)
}

if(!process.env.GUESTBOOK_DB_ADDR) {
  const errMsg = "GUESTBOOK_DB_ADDR environment variable is not defined"
  console.error(errMsg)
  throw new Error(errMsg)
}

// Connect to MongoDB, will retry only once
messages.connectToMongoDB()

// starts an http server on the $PORT environment variable
app.listen(PORT, () => {
  console.log(`App listening on port ${PORT}`);
  console.log('Press Ctrl+C to quit.');
});

module.exports = app