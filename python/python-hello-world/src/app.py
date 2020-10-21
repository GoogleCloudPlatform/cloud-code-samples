import os

from flask import Flask, render_template

# pylint: disable=C0103
app = Flask(__name__)


@app.route('/')
def hello():
    """Return a simple HTML page with a friendly message."""
    message = "It's running!"

    return render_template('index.html', message=message)

if __name__ == '__main__':
    server_port = os.environ.get('PORT', '8080')
    app.run(debug=False, port=server_port, host='0.0.0.0')
