import uuid
import base64

import utils


TABLE_CARDBOXES = 'cardboxs'


class CardBox:

    def __init__(self, _id: str, owner: str,
                 rating: int, tags: list, content: list):

        self.id = _id
        self.owner = owner
        self.rating = rating
        self.tags = tags
        self.content = content

    @staticmethod
    def gen_card_id() -> str:
        return base64.urlsafe_b64encode(uuid.uuid4().bytes).decode('utf-8')

    def store(self, db):
        db.hset(TABLE_CARDBOXES, self.id, utils.jsonify(self))

    @staticmethod
    def fetch(db, card_box_id: str):
        json_string = db.hget(TABLE_CARDBOXES, card_box_id)

        if not json_string:
            return None

        _dict = utils.unjsonify(json_string)
        return CardBox(**_dict)
