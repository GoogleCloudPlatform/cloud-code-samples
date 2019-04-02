const express = require('express');
const logging= require('./logging')

const app = express();
// Logger to capture any top-level errors and outputs them to the console
app.use(logging.errorLogger)
// Logger to capture all requests and outputs them to the console
app.use(logging.requestLogger)

// returns a simple respnse 
app.get('/', (req, res) => {
  res
    .status(200)
    .send('Hello, world!')
});

// starts an http server on the $PORT environment variable
const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
  logging.info(`App listening on port ${PORT}`);
  logging.info('Press Ctrl+C to quit.');
});

module.exports = app
