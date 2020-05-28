const express = require('express');
const {readFileSync} = require('fs');
const metadata = require('gcp-metadata');
const handlebars = require('handlebars');
const pkg = require('./package');

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
      template = handlebars.compile(readFileSync('index.html.hbs', 'utf8'));
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

const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
  console.log(
    'Hello from Cloud Run! The container started successfully and is listening for HTTP requests on $PORT'
  );
  console.log(`${pkg.name} listening on port ${PORT}`);
  console.log('Press Ctrl+C to quit.');
});
