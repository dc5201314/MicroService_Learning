from flask import Flask, request, jsonify
from flask_mysqldb import MySQL
import nacos

app = Flask(__name__)

# 服务和 Nacos 配置
SERVICE_CONFIG = {
    "name": "users-service",
    "port": 8090,
    "nacos": {
        "server": "localhost:8848",
        "username": "nacos",
        "password": "nacos"
    },
    "fixed_amount": 5200.00
}

# 初始化 MySQL
app.config.from_object('config.Config')
mysql = MySQL(app)

# 初始化 Nacos 客户端
nacos_client = nacos.NacosClient(
    server_addresses=SERVICE_CONFIG["nacos"]["server"],
    username=SERVICE_CONFIG["nacos"]["username"],
    password=SERVICE_CONFIG["nacos"]["password"]
)

def register_to_nacos():
    """注册服务到 Nacos"""
    nacos_client.add_naming_instance(
        service_name=SERVICE_CONFIG["name"],
        ip="127.0.0.1",  # 使用实际IP或127.0.0.1
        port=SERVICE_CONFIG["port"],
        cluster_name="DEFAULT",
        metadata={"preserved.heart_beat_interval": "5"}
    )
    print(f"[Nacos] Service {SERVICE_CONFIG['name']} registered")

# 删除原来的 before_first_request 装饰器

# 获取用户信息 by ID
@app.route('/user/<int:id>', methods=['GET'])
def get_user(id):
    cursor = mysql.connection.cursor()
    cursor.execute("SELECT * FROM user_p WHERE id = %s", (id,))
    result = cursor.fetchone()
    cursor.close()

    if result:
        user = {"id": result[0], "user_name": result[1], "avatar_url": result[2], "mobile": result[3]}
        return jsonify(user), 200
    return jsonify({"message": "User not found"}), 404

# 创建新用户
@app.route('/user', methods=['POST'])
def create_user():
    data = request.json
    cursor = mysql.connection.cursor()
    cursor.execute("INSERT INTO user_p (user_name, avatar_url, mobile) VALUES (%s, %s, %s)",
                   (data['user_name'], data['avatar_url'], data['mobile']))
    mysql.connection.commit()
    cursor.close()
    return jsonify({"message": "User created"}), 201

# 更新用户信息
@app.route('/user/<int:id>', methods=['PUT'])
def update_user(id):
    data = request.json
    cursor = mysql.connection.cursor()
    cursor.execute("UPDATE user_p SET user_name = %s, avatar_url = %s, mobile = %s WHERE id = %s",
                   (data['user_name'], data['avatar_url'], data['mobile'], id))
    mysql.connection.commit()
    cursor.close()
    return jsonify({"message": "User updated"}), 200

# 删除用户
@app.route('/user/<int:id>', methods=['DELETE'])
def delete_user(id):
    cursor = mysql.connection.cursor()
    cursor.execute("DELETE FROM user_p WHERE id = %s", (id,))
    mysql.connection.commit()
    cursor.close()
    return jsonify({"message": "User deleted"}), 200

@app.route('/test')
def test():
    return "Test endpoint works!"

if __name__ == '__main__':
    register_to_nacos()  # 在启动应用前注册服务
    app.run(port=SERVICE_CONFIG["port"])