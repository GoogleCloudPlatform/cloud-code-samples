const express = require('express');
const handlebars = require('handlebars');
const fs = require('fs').promises;
const metadata = require('gcp-metadata');

const app = express();
app.use('/assets', express.static('assets'));

// Initialize handlebars template values for reuse.
const data = {
  project: process.env.GOOGLE_CLOUD_PROJECT,
  service: process.env.K_SERVICE || '???',
  revision: process.env.K_REVISION || '???',
};
let template;

app.get('/', async (req, res) => {
  // Prepare the template for use if not ready.
  if (!template) {
    // Only check the Cloud Run metadata server if it looks like the service
    // is deployed to Cloud Run or GOOGLE_CLOUD_PROJECT is not set.
    if (!data.project) {
      try {
        data.project = await metadata.project();
      } catch (e) {
        data.project = undefined;
        console.error(e);
      }
    }

    // Load template file into memory and compile.
    try {
      const source = await fs.readFile('index.html.hbs', 'utf-8');
      template = handlebars.compile(source);
    } catch (e) {
      console.error(e);
      res.status(500).send('Internal Server Error');
    }
  }

  // Execute the template and send an HTTP response.
  try {
    const output = template(data);
    res.status(200).send(output);
  } catch (e) {
    console.error(e);
    res.status(500).send('Internal Server Error');
  }
});

module.exports = app;
