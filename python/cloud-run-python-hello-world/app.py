"""
A sample Hello World server.
"""
import os
import requests

from flask import Flask, render_template

# pylint: disable=C0103
app = Flask(__name__)


def get_metadata(item_name):
    metadata_url = 'http://metadata.google.internal/computeMetadata/v1/'
    headers = {'Metadata-Flavor': 'Google'}

    try:
        r = requests.get(metadata_url + item_name, headers=headers)
        return r.text
    except:
        return 'Unavailable'


@app.route('/')
def hello():
    """Return a friendly HTTP greeting."""
    message = "It's running!"

    project = get_metadata('project/project-id')
    service = os.environ.get('K_SERVICE', 'Unknown service')
    revision = os.environ.get('K_REVISION', 'Unknown revision')

    return render_template('index.html',
        message=message,
        Project=project,
        Service=service,
        Revision=revision)

if __name__ == '__main__':
    server_port = os.environ.get('PORT', '8080')
    app.run(debug=False, port=server_port, host='0.0.0.0')
