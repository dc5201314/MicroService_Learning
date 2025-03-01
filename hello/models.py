from flask import Flask
from flask_mysqldb import MySQL

app = Flask(__name__)
app.config.from_object('config.Config')

mysql = MySQL(app)

class User:
    def __init__(self, id, user_name, avatar_url, mobile):
        self.id = id
        self.user_name = user_name
        self.avatar_url = avatar_url
        self.mobile = mobile

    def to_dict(self):
        return {
            'id': self.id,
            'user_name': self.user_name,
            'avatar_url': self.avatar_url,
            'mobile': self.mobile
        }
