from flask_wtf import FlaskForm
from wtforms import StringField, PasswordField, BooleanField, SubmitField
from wtforms.validators import ValidationError, DataRequired, Email, EqualTo, Length
from werkzeug.security import generate_password_hash, check_password_hash

import utils


TABLE_USER = 'users'


class User:
    def __init__(self, _id: str, password_hash=None, cardboxs=[]):

        self.id = _id
        # self.rating = rating
        self.password_hash = password_hash
        self.cardboxs = cardboxs

        self.is_active = True
        self.is_authenticated = True
        self.is_anonymous = False

    def get_id(self) -> str:
        return self.id

    def set_password(self, password_plain: str):
        self.password_hash = generate_password_hash(password_plain)

    def check_password(self, password_plain: str) -> bool:
        return check_password_hash(self.password_hash, password_plain)

    def store(self, db):
        db.hset(TABLE_USER, self.id, utils.jsonify(self))

    @staticmethod
    def fetch(db, user_id: str):
        json_string = db.hget(TABLE_USER, user_id)

        if not json_string:
            return None

        _dict = utils.unjsonify(json_string)
        return User(**_dict)

    @staticmethod
    def exists(db, user_id: str) -> bool:
        return db.hexists(TABLE_USER, user_id)


class RegistrationForm(FlaskForm):
    def __init__(self, db, **kwargs):
        super().__init__(**kwargs)
        self.db = db

    username = StringField('Username',
                           validators=[DataRequired(),
                                       Length(min=3, max=16)])
    password = PasswordField('Password',
                             validators=[DataRequired(),
                                         Length(min=6, max=32)])
    password2 = PasswordField('Repeat Password',
                              validators=[DataRequired(),
                                          EqualTo('password')])
    submit = SubmitField('Register')

    # auto-invoked by WTForms
    def validate_username(self, username):

        user = User.exists(self.db, username.data)

        if user:
            raise ValidationError('Username is already taken.')
