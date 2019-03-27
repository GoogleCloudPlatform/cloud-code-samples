"""
A sample frontend server. Hosts a web page to display messages
"""
import json
import os
import time
from flask import Flask, render_template, redirect, url_for, request, jsonify
import ptvsd
import requests

# pylint: disable=C0103
app = Flask(__name__)
app.config["BACKEND_URI"] = 'http://{}/messages'.format(
    os.environ.get('GUESTBOOK_API_ADDR', 'localhost:8080'))

@app.route('/')
def main():
    """ Retrieve a list of messages from the backend, and use them to render the HTML template """
    response = requests.get(app.config["BACKEND_URI"], timeout=0.1)
    message_list = json.loads(response.text)
    return render_template('home.tpl', messages=message_list)

@app.route('/post', methods=['POST'])
def post():
    """ Send the new message to the backend and redirect to the homepage """
    new_message = {'Author': request.form['name'],
                   'Message':  request.form['message'],
                   'Date': time.time()}
    requests.post(url=app.config["BACKEND_URI"],
                  data=jsonify(new_message).data,
                  headers={'content-type': 'application/json'},
                  timeout=0.1)
    return redirect(url_for('main'))

if __name__ == '__main__':
    debug_port = os.getenv('DEBUG_PORT', None)
    server_port = os.getenv('PORT', 8080)

    if debug_port is not None:
        ptvsd.enable_attach(address=('0.0.0.0', debug_port))

    app.run(debug=False, port=server_port, host='0.0.0.0')
