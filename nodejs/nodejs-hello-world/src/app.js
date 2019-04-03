const express = require('express');

const app = express();

// returns a simple respnse 
app.get('/', (req, res) => {
  console.log(`received request: ${req.method} ${req.url}`)
  res.status(200).send('Hello, world!')
});

// starts an http server on the $PORT environment variable
const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
  console.log(`App listening on port ${PORT}`);
  console.log('Press Ctrl+C to quit.');
});

module.exports = app
