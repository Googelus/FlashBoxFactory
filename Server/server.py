import redis
from flask import Flask, request, abort, jsonify, redirect, url_for, flash, render_template
from flask_login import LoginManager, current_user, login_user, login_required, logout_user
from flask_bootstrap import Bootstrap

from auth import User, RegistrationForm, LoginForm
from model import CardBox
from flask_table import Table, Col, ButtonCol, LinkCol

import utils
from display import CardBoxTable, FilterForm


app = Flask(__name__)
app.secret_key = ('34c059badbbd38455b4eb44865c25303'
                  '582a6056565be9eee146f46b7079ff95')

db = redis.StrictRedis(host='localhost', port=6379, db=0)

login_manager = LoginManager(app)
Bootstrap(app)


# TODO: improve error code responses


@app.route('/')
@app.route('/index')
def index():
    if current_user.is_authenticated:
        return render_template('welcome.html')
    return render_template('index.html')


@app.route('/cardboxes/<_id>')
@login_required
def show_box(_id):
    box = CardBox.fetch(db, _id)

    if not box:
        flash('not box', 'error')
        return redirect(url_for('index'))

    return render_template('show_box.html', box=box)


@app.route('/cardboxes', methods=['POST', 'GET'])
@login_required
def huge_list():

    args = request.args

    sort_key = args.get('sort') or 'rating'
    sort_direction = args.get('direction')

    if not sort_direction:
        sort_direction = True
    else:
        sort_direction = sort_direction == 'desc'

    filter_option = args.get('foption')
    filter_term = args.get('fterm')

    form = FilterForm()
    if form.validate_on_submit():
        filter_option = form.option.data
        filter_term = form.term.data

        kwargs = {key: value for key, value in args.items()}
        kwargs.update(foption=filter_option, fterm=filter_term)

        return redirect(url_for('huge_list', **kwargs))

    form.term.data = filter_term
    filter_option = filter_option or 'tags'
    form.option.data = filter_option

    def sort_key_of(box):
        if sort_key == 'name':
            return box.name.lower()

        return getattr(box, sort_key)

    cardboxes = CardBox.fetch_all(db)

    if filter_term:
        cardboxes = [c for c in cardboxes
                     if filter_term in getattr(c, filter_option)]

    if sort_key and cardboxes and hasattr(cardboxes[0], sort_key):
        cardboxes.sort(key=sort_key_of, reverse=sort_direction)
    else:
        sort_key = None

    table = CardBoxTable(cardboxes, sort_reverse=sort_direction, sort_by=sort_key)

    return render_template('huge_list.html', table=table, filter_form=form)


@app.route('/cardboxes/<_id>/rate')
@login_required
def rate_cardbox(_id):
    box = CardBox.fetch(db, _id)

    if not box:
        flash('not box')
        # TODO return to /carboxes/<_id>
        return redirect(url_for('index'))

    if box.increment_rating(db, current_user):
        flash('u succ(ess)')
        # TODO return to /carboxes/<_id>
        return redirect(url_for('show_box', _id=_id))

    flash("Already rated. Don't try to fool us!", 'error')
    # TODO return to /carboxes/<_id>
    return redirect(url_for('show_box', _id=_id))


# TODO filter malevolent input
@app.route('/add_cardbox', methods=['POST'])
def add_cardbox():
    if not request.is_json:
        abort(404)

    # already returns dictionary
    payload = request.get_json()

    req = ('username', 'password', 'tags', 'content', 'name')
    if not payload or not all(r in payload for r in req):
        abort(404)

    if User.exists(db, payload['username']):
        user = User.fetch(db, payload['username'])
        if not user.check_password(payload['password']):
            abort(404)

        new_box = CardBox(CardBox.gen_card_id(), name=payload['name'],
                          owner=user._id, rating=0,
                          tags=payload['tags'], content=payload['content'])
        new_box.store(db)

        user.cardboxs.append(new_box._id)

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

    return render_template('register.html', form=form)


@app.route('/login', methods=['GET', 'POST'])
def login():
    if current_user.is_authenticated:
        return redirect(url_for('index'))

    form = LoginForm(db)

    if form.validate_on_submit():

        user = User.fetch(db, form.username.data)
        if not user.check_password(form.password.data):
            flash('You shall not password.', 'error')
            return redirect(url_for('login'))

        login_user(user)

        flash('Login succ!')
        return redirect(url_for('index'))

    return render_template('login.html', form=form)


@app.route('/logout')
@login_required
def logout():
    logout_user()
    flash('Logout succ')
    return redirect(url_for('index'))


@login_manager.user_loader
def load_user(user_id: str):
    return User.fetch(db, user_id)


if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5000, debug=True)
    # utils.clean_boxes(db)
    # utils.clean_users(db)
