"""
A sample backend server. Saves and retrieves entries using mongodb
"""
import json
import os
from flask import Flask, jsonify, request
from flask_pymongo import PyMongo
from functools import reduce
import bleach
try:
    import ptvsd
except:
    pass

# pylint: disable=C0103
app = Flask(__name__)
app.config["MONGO_URI"] = 'mongodb://{}:{}@{}:{}/admin'.format(
    os.environ.get('MONGO_USERNAME', 'root'),
    os.environ.get('MONGO_PASSWORD', 'password'),
    os.environ.get('MONGO_HOST', 'localhost'),
    os.environ.get('MONGO_PORT', '27017'))
mongo = PyMongo(app)

@app.route('/messages', methods=['GET'])
def get_messages():
    """ retrieve and return the list of messages on GET request """
    message_list = list(mongo.db.messages.find())
    # make json serializable
    for m in message_list:
        m['_id'] = str(m['_id'])
    return jsonify(message_list)

@app.route('/messages', methods=['POST'])
def add_message():
    """ save a new message on POST request """
    data = json.loads(request.data)
    validKeys = set(['Date', 'Author', 'Message'])
    isValid = reduce((lambda prevIsValid, thisKey: prevIsValid and 
                                          thisKey in validKeys and
                                          isinstance(data[thisKey], str)), data.keys())
    isValid = isValid and data.keys() == validKeys
    data = {key: bleach.clean(val) for key, val in data.items()}
    if isValid:
        result = mongo.db.messages.insert_one(data)
        return make_response(jsonify(message='Message created'), status.HTTP_201_CREATED)
    else:
        abort(400)

if __name__ == '__main__':
    server_port = os.getenv('PORT', 8080)

    app.run(debug=False, port=server_port, host='0.0.0.0')
