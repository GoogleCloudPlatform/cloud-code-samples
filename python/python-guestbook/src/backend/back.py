"""
A sample backend server. Saves and retrieves entries using mongodb
"""
import os
from flask import Flask, jsonify, request
from flask_pymongo import PyMongo
from functools import reduce
import bleach

app = Flask(__name__)
app.config["MONGO_URI"] = 'mongodb://{}:{}@{}:{}/admin'.format(
    os.environ.get('MONGO_USERNAME', 'root'),
    os.environ.get('MONGO_PASSWORD', 'password'),
    os.environ.get('MONGO_HOST', 'localhost'),
    os.environ.get('MONGO_PORT', '27017'))
mongo = PyMongo(app)
valid_keys = set(['Date', 'Author', 'Message'])

@app.route('/messages', methods=['GET'])
def get_messages():
    """ retrieve and return the list of messages on GET request """
    raw_data = list(mongo.db.messages.find())
    cleaned_list = []
    for msg in raw_data:
        cleaned_msg = {k: bleach.clean(msg[k]) for k in msg if k in valid_keys}
        cleaned_list.append(cleaned_msg)
    return jsonify(cleaned_list)

@app.route('/messages', methods=['POST'])
def add_message():
    """ save a new message on POST request """
    raw_data = request.get_json()
    data = {k: bleach.clean(raw_data[k]) for k in raw_data if k in valid_keys}
    if len(data) == len(valid_keys):
        mongo.db.messages.insert_one(data)
        return jsonify(message={'success':True}), 201
    return jsonify(message={'success':False}), 400

if __name__ == '__main__':
    server_port = os.getenv('PORT', 8080)

    app.run(debug=False, port=server_port, host='0.0.0.0')
