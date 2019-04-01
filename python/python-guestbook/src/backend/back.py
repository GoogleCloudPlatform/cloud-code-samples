"""
A sample backend server. Saves and retrieves entries using mongodb
"""
import os
from flask import Flask, jsonify, request
from flask_pymongo import PyMongo
from functools import reduce
import bleach
import datetime

app = Flask(__name__)
app.config["MONGO_URI"] = 'mongodb://{}/guestbook'.format(os.environ.get('GUESTBOOK_DB_ADDR'))
mongo = PyMongo(app)

@app.route('/messages', methods=['GET'])
def get_messages():
    """ retrieve and return the list of messages on GET request """
    msg_list = list(mongo.db.messages.find({}, {'author':1, 'message':1, 'date':1, '_id':0}).sort("_id", -1))
    return jsonify(msg_list), 201

@app.route('/messages', methods=['POST'])
def add_message():
    """ save a new message on POST request """
    raw_data = request.get_json()
    msg_data = {'author':bleach.clean(raw_data['author']),
                'message':bleach.clean(raw_data['message']),
                'date':datetime.datetime.today().strftime('%b %d, %-H:%M')}
    mongo.db.messages.insert_one(msg_data)
    return  jsonify({}), 201

if __name__ == '__main__':
    for v in ['PORT', 'GUESTBOOK_DB_ADDR']:
        if os.environ.get(v) is None:
            print("error: {} environment variable not set".format(v))
            exit(1)
    app.run(debug=False, port=os.environ.get('PORT'), host='0.0.0.0')
