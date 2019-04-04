"""
A sample Hello World server.
"""
import os
from flask import Flask
import ptvsd

# pylint: disable=C0103
app = Flask(__name__)

@app.route('/')
def hello():
    """Return a friendly HTTP greeting."""
    message = "Hello World"
    return message

if __name__ == '__main__':
    server_port = os.environ.get('PORT')
    if server_port is None:
        print("error: PORT environment variable not set")
        exit(1)
    
    # activate the debugger on port 3000
    ptvsd.enable_attach(('0.0.0.0', '3000'))

    # start Flask server
    # Flask's debug mode is unrelated to ptvsd debugger used by Cloud Code
    app.run(debug=False, port=server_port, host='0.0.0.0')
