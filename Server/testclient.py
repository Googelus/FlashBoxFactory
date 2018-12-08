import json
import requests

import redis


def create_sample_box():
    payload = dict(username='weirdpedo',
                   password='raeploli',
                   name='Nymphs',
                   tags=['weiblicher_darsteller', 'beume'],
                   content=['PLACEHOLDER'])
    r = requests.post('http://localhost:5000/add_cardbox', json=payload)


def main():
    create_sample_box()


if __name__ == "__main__":
    db = redis.StrictRedis(host='localhost', port=6379, db=0)
    print(db.hgetall('cardboxs'))
