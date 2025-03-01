import threading
import time
from flask import Flask, request, jsonify
import nacos

app = Flask(__name__)

SERVICE_CONFIG = {
    "name": "hello-service",
    "port": 8090,
    "nacos": {
        "server": "localhost:8848",
        "username": "nacos",
        "password": "nacos"
    },
    "fixed_amount": 5200.00
}

# 初始化Nacos客户端
nacos_client = nacos.NacosClient(
    server_addresses=SERVICE_CONFIG["nacos"]["server"],
    username=SERVICE_CONFIG["nacos"]["username"],
    password=SERVICE_CONFIG["nacos"]["password"]
)

def register_to_nacos():
    """注册服务到Nacos"""
    nacos_client.add_naming_instance(
        service_name=SERVICE_CONFIG["name"],
        ip="127.0.0.1",  # 这里使用 127.0.0.1 或服务器实际 IP
        port=SERVICE_CONFIG["port"],
        cluster_name="DEFAULT",
        metadata={"preserved.heart_beat_interval": "5"}
    )
    print(f"[Nacos] Service {SERVICE_CONFIG['name']} registered")

def send_heartbeat():
    """定时心跳维护"""
    while True:
        try:
            nacos_client.send_heartbeat(
                service_name=SERVICE_CONFIG["name"],
                ip="127.0.0.1",
                port=SERVICE_CONFIG["port"]
            )
            time.sleep(5)
        except Exception as e:
            print(f"Heartbeat error: {str(e)}")

@app.route('/api/payment', methods=['POST'])
def handle_payment():
    """处理支付请求"""
    try:
        data = request.json
        amount = float(data.get('amount', 0))

        if amount < SERVICE_CONFIG["fixed_amount"]:
            return jsonify({
                "status": "failed",
                "message": f"金额不足，还需支付 ¥{SERVICE_CONFIG['fixed_amount'] - amount:,.2f}"
            }), 400

        change = amount - SERVICE_CONFIG["fixed_amount"]

        return jsonify({
            "status": "success",
            "data": {
                "total": SERVICE_CONFIG["fixed_amount"],
                "received": amount,
                "change": round(change, 2)
            }
        })

    except ValueError:
        return jsonify({"status": "error", "message": "无效金额格式"}), 400
    except Exception as e:
        return jsonify({"status": "error", "message": str(e)}), 500

def start_service():
    register_to_nacos()
    heartbeat_thread = threading.Thread(target=send_heartbeat)
    heartbeat_thread.daemon = True
    heartbeat_thread.start()
    app.run(port=SERVICE_CONFIG["port"], host='0.0.0.0')  # 允许外部访问

if __name__ == '__main__':
    start_service()
