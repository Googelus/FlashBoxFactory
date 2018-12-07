import redis
from flask import Flask, request, abort, jsonify, redirect, url_for, flash, render_template
from flask_login import LoginManager, current_user, login_user, login_required
from flask_bootstrap import Bootstrap

from auth import User, RegistrationForm
from model import CardBox

app = Flask(__name__)
app.secret_key = ('34c059badbbd38455b4eb44865c25303'
                  '582a6056565be9eee146f46b7079ff95')

db = redis.StrictRedis(host='localhost', port=6379, db=0)

login_manager = LoginManager(app)
Bootstrap(app)


OWNER = 'herbert'

# TODO: improve error code responses


@app.route('/')
@app.route('/index')
def index():
    return 'Hello there!'


@app.route('/login')
def login():
    return 'No forms today.'


@app.route('/add_cardbox', methods=['POST'])
def add_cardbox():
    if not request.is_json:
        abort(404)

    # already returns dictionary
    payload = request.get_json()

    if not payload or 'tags' not in payload or 'content' not in payload:
        abort(404)

    # TODO: authenticate USER

    new_box = CardBox(CardBox.gen_card_id(), owner=OWNER, rating=0,
                      tags=payload['tags'], content=payload['content'])
    new_box.store(db)

    return 'OK'


@app.route('/cardboxes/<_id>/download', methods=['GET'])
def download_cardbox(_id: str):
    box = CardBox.fetch(db, _id)

    if not box:
        abort(404)

    return jsonify(vars(box))


@app.route('/register', methods=['GET', 'POST'])
def register():
    if current_user.is_authenticated:
        return redirect(url_for('index'))

    form = RegistrationForm(db)

    if form.validate_on_submit():
        user = User(form.username.data)
        user.set_password(form.password.data)

        user.store(db)

        flash('Successfully placed forehead to release soul!')
        return redirect(url_for('login'))

    return render_template('register.html', form=form, test='EYOO')


def clean_boxes():
    db.hdel('cardboxs', *db.hgetall('cardboxs').keys())


if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5000, debug=True)
    # clean_boxes()
