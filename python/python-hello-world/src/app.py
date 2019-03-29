"""
A sample Hello World server.
"""
import os
from flask import Flask

# pylint: disable=C0103
app = Flask(__name__)

@app.route('/')
def hello():
    """Return a friendly HTTP greeting."""
    message = "Hello World"
    return message

if __name__ == '__main__':
    server_port = os.getenv('PORT', 8080)

    app.run(debug=False, port=server_port, host='0.0.0.0')
