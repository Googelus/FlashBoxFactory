import json


def unjsonify(json_string: str):
    return json.loads(json_string.decode('utf-8'))


def jsonify(obj: object):
    return json.dumps(vars(obj))


def clean_boxes(db):
    db.hdel('cardboxs', *db.hgetall('cardboxs').keys())


def clean_users(db):
    db.hdel('users', *db.hgetall('users').keys())
