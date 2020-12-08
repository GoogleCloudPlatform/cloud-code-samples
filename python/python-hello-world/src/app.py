import os

from signal import signal, SIGINT
from flask import Flask, render_template

def handler(signal_received, frame):
    # SIGINT or  ctrl-C detected, exit without error
    exit(0)

# pylint: disable=C0103
app = Flask(__name__)


@app.route('/')
def hello():
    """Return a simple HTML page with a friendly message."""
    message = "It's running!"

    return render_template('index.html', message=message)

if __name__ == '__main__':
    signal(SIGINT, handler)
    server_port = os.environ.get('PORT', '8080')
    app.run(debug=False, port=server_port, host='0.0.0.0')
