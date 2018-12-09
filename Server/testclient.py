import json
import random
import requests

import string
import redis


TAGS = ['weiblicher_darsteller', 'epischer_soundtrack', 'beume', 'side_scroller',
        'adventure', 'tofu', 'cheese', 'more cheese', 'hanamura', 'both_ends_count',
        'ganondorf', 'loliraep']

USER = 'herbert'  # 'a a a a a'  #
PASSWORD = 'bierbauch'  # 'superfail'  #


def create_many_boxes(username, password, number=100):
    for _ in range(number):
        upper = random.randint(4, 12)

        name = ''.join(random.choice(string.ascii_lowercase)
                       for _ in range(upper))
        tags = random.sample(TAGS, 2)
        create_sample_box(username, password, name, tags, 'PLACEHOLDER')


def create_sample_box(username, password, name, tags, content):
    payload = dict(username=username,
                   password=password,
                   name=name,
                   tags=tags,
                   content=content)
    r = requests.post('http://localhost:5000/add_cardbox', json=payload)


def test_download():
    r = requests.get(
        'http://localhost:5000/cardboxes/iqPLZZxkQKeAYEIWBVyDgA==/download')
    print(r.content)


def print_boxes():
    db = redis.StrictRedis(host='localhost', port=6379, db=0)
    print(db.hgetall('cardboxs'))


def main():
    create_many_boxes(USER, PASSWORD, 15)
    # test_download()


if __name__ == "__main__":
    main()
