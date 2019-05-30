"""
A sample backend server. Saves and retrieves entries using mongodb
"""
import os
import time
from flask import Flask, jsonify, request
from flask_pymongo import PyMongo
import bleach

app = Flask(__name__)
app.config["MONGO_URI"] = 'mongodb://{}/guestbook'.format(os.environ.get('GUESTBOOK_DB_ADDR'))
mongo = PyMongo(app)

@app.route('/messages', methods=['GET'])
def get_messages():
    """ retrieve and return the list of messages on GET request """
    field_mask = {'author':1, 'message':1, 'date':1, '_id':0}
    msg_list = list(mongo.db.messages.find({}, field_mask).sort("_id", -1))
    return jsonify(msg_list), 201

@app.route('/messages', methods=['POST'])
def add_message():
    """ save a new message on POST request """
    raw_data = request.get_json()
    msg_data = {'author':bleach.clean(raw_data['author']),
                'message':bleach.clean(raw_data['message']),
                'date':time.time()}
    mongo.db.messages.insert_one(msg_data)
    return  jsonify({}), 201

if __name__ == '__main__':
    for v in ['PORT', 'GUESTBOOK_DB_ADDR']:
        if os.environ.get(v) is None:
            print("error: {} environment variable not set".format(v))
            exit(1)

    # start Flask server
    # Flask's debug mode is unrelated to ptvsd debugger used by Cloud Code
    app.run(debug=False, port=os.environ.get('PORT'), host='0.0.0.0')
