const express = require('express')
const mongoose = require('mongoose')
const app = express()
const routes = require('./routes')
const PORT = process.env.PORT || 8080;

const MONGO_USERNAME = process.env.MONGO_USERNAME || 'root'
const MONGO_PASSWORD = process.env.MONGO_PASSWORD || 'password'
const MONGO_HOST = process.env.MONGO_HOST || 'localhost'
const MONGO_PORT = process.env.MONGO_PORT || '27017'

const MONGO_URI = `mongodb://${MONGO_USERNAME}:${MONGO_PASSWORD}@${MONGO_HOST}:${MONGO_PORT}/admin`

mongoose.connect(MONGO_URI, {useNewUrlParser: true})

const db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection err:'));
db.once('open', function() {
  console.log('connected to ' + MONGO_URI);
})

app.use('/', routes)
// starts an http server on the $PORT environment variable
app.listen(PORT, () => {
  console.log(`App listening on port ${PORT}`);
  console.log('Press Ctrl+C to quit.');
});
