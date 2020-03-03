const express = require('express');
const handlebars = require('handlebars');
const fs = require('fs').promises;
const metadata = require('gcp-metadata');

const app = express();
// Serve the files in /assets at the URI /assets.
app.use('/assets', express.static('assets'));

// The HTML content is produced by rendering a handlebars template.
// The template values are stored in global state for reuse.
const data = {
  project: process.env.GOOGLE_CLOUD_PROJECT,
  service: process.env.K_SERVICE || '???',
  revision: process.env.K_REVISION || '???',
};
let template;

app.get('/', async (req, res) => {
  // The handlebars template is stored in global state so this will only once.
  if (!template) {
    // Load Handlebars template from filesystem and compile for use.
    try {
      const source = await fs.readFile('index.html.hbs', 'utf-8');
      template = handlebars.compile(source);
    } catch (e) {
      console.error(e);
      res.status(500).send('Internal Server Error');
    }

    // Populate the Google Cloud Project template parameter.
    // If the custom environment variable GOOGLE_CLOUD_PROJECT is not set
    // check the Cloud Run metadata server for the project ID.
    if (!data.project) {
      try {
        data.project = await metadata.project();
      } catch (e) {
        data.project = undefined;
        console.error(e);
      }
    }
  }

  // Apply the template to the parameters to generate an HTML string.
  try {
    const output = template(data);
    res.status(200).send(output);
  } catch (e) {
    console.error(e);
    res.status(500).send('Internal Server Error');
  }
});

module.exports = app;
