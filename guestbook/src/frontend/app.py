"""
Copyright 2019 Google LLC. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
"""

import os
from flask import Flask, render_template, redirect, url_for, request
import ptvsd
import requests
import json

# pylint: disable=C0103
app = Flask(__name__)

@app.route('/')
def main():
    return render_template('home.tpl', messages=get_messages())

@app.route('/post', methods=['POST'])
def post():
    error_text = saveMessage(request.form['name'], request.form['message'])
    print(error_text)
    if error_text is not None:
        return render_template('home.tpl', error=error_text)
    else:
        return redirect(url_for('main'))

def saveMessage(author, message):
    if author == "":
        return "Please enter your name"
    elif message == "":
        return "Please enter a message"
    print("message: {} author: {}".format(message, author))
    return None

def get_messages():
    response = requests.get("http://python-guestbook-backend:8080/messages")
    #todo: handle errors

    message_list = json.loads(response.text)
    return message_list


if __name__ == '__main__':
    debug_port = os.getenv('DEBUG_PORT', None)
    server_port = os.getenv('PORT', 8080)

    if debug_port is not None:
        ptvsd.enable_attach(address=('0.0.0.0', debug_port))

    app.run(debug=False, port=server_port, host='0.0.0.0')
