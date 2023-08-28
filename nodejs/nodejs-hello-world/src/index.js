/**
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

const express = require('express');
const {readFileSync} = require('fs');
const path = require('path');
const handlebars = require('handlebars');

const app = express();
// Serve the files in /assets at the URI /assets.
app.use('/assets', express.static(path.join(__dirname, 'assets')));

// The HTML content is produced by rendering a handlebars template.
// The template values are stored in global state for reuse.
let template;
const data = {
  message: "It's running!"
};

app.get('/', async (req, res) => {
  // The handlebars template is stored in global state so this will only load once.
  if (!template) {
    // Load Handlebars template from filesystem and compile for use.
    try {
      template = handlebars.compile(readFileSync(path.join(__dirname, 'index.html.hbs'), 'utf8'));
    } catch (e) {
      console.error(e);
      res.status(500).send('Could not find index');
    }
  }

  // Apply the template to generate an HTML string.
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
    'Hello! The container started successfully and is listening for HTTP requests on $PORT'
  );
  console.log('Press Ctrl+C to quit.');
});
