import json
import requests


def main():
    payload = dict(tags=['weiblicher_darsteller', 'beume'],
                   content=['PLACEHOLDER'])
    r = requests.post('http://localhost:5000/add_cardbox', json=payload)


if __name__ == "__main__":
    main()
