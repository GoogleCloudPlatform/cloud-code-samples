const express = require('express')
const app = express()
const routes = require('./routes')
const PORT = process.env.PORT || 8080;
const messages = require('./routes/messages')

app.use('/', routes)

// Connect to MongoDB, will retry only once
messages.connectToMongoDB()

// starts an http server on the $PORT environment variable
app.listen(PORT, () => {
  console.log(`App listening on port ${PORT}`);
  console.log('Press Ctrl+C to quit.');
});

module.exports = app