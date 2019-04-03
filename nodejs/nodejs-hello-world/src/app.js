const express = require('express');
const requestLogger = require('./logging')

const app = express();

// Logger to capture all requests and outputs them to the console
app.use(requestLogger)

// returns a simple respnse 
app.get('/', (req, res) => {
  res
    .status(200)
    .send('Hello, world!')
});

// starts an http server on the $PORT environment variable
const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
  console.log(`App listening on port ${PORT}`);
  console.log('Press Ctrl+C to quit.');
});

module.exports = app
