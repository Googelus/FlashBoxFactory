import json


def unjsonify(json_string: str):
    return json.loads(json_string.decode('utf-8'))


def jsonify(obj: object):
    return json.dumps(vars(obj))
