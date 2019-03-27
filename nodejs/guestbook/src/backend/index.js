'use strict';

const express = require('express');

const app = express();

app.use('/api/', require('./api'));

const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
  console.log(`App listening on port ${PORT}`);
  console.log('Press Ctrl+C to quit.');
});
