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
from flask import Flask
import ptvsd
from flask_pymongo import PyMongo

# pylint: disable=C0103
app = Flask(__name__)
app.config["MONGO_URI"] = 'mongodb://{}:{}@{}:27017/admin'.format(  os.environ.get('MONGO_USERNAME', 'root'), 
                                                                    os.environ.get('MONGO_PASSWORD', 'password'), 
                                                                    os.environ.get('MONGO_HOST', 'localhost'), 
                                                                    os.environ.get('MONGO_PORT', '27017'))
mongo = PyMongo(app)

@app.route('/')
def hello():
    """Return a friendly HTTP greeting."""
    message = "Hello World"
    return message

def getMessages():
    message_list = mongo.db.messages.find()
    return list(message_list)

def addMessage(data):
    result = mongo.db.messages.insert_one(data)
    return result.inserted_id


def get_mocks():
    message_list = [
        {"Author": "test", "Message": "test2", "Date":"test3"},
        {"Author": "Dan", "Message": "Gr8", "Date":"Mar 12"}
        ]
    return message_list

if __name__ == '__main__':
    debug_port = os.getenv('DEBUG_PORT', None)
    server_port = os.getenv('PORT', 8080)

    if debug_port is not None:
        ptvsd.enable_attach(address=('0.0.0.0', debug_port))

    app.run(debug=False, port=server_port, host='0.0.0.0')
