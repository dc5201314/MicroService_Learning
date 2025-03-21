from flask import Flask, request, jsonify, send_from_directory
import matplotlib.pyplot as plt
from wordcloud import WordCloud
import os

app = Flask(__name__)

# 图像保存目录
IMAGE_DIR = 'D:\\testimg'
if not os.path.exists(IMAGE_DIR):
    os.makedirs(IMAGE_DIR)

@app.route('/generate_wordcloud', methods=['POST'])
def generate_wordcloud():
    text = request.json.get('text', '')

    # 生成词云
    wordcloud = WordCloud(
        width=800,
        height=400,
        background_color='white',
        contour_color='steelblue',  # 设置轮廓颜色
        contour_width=3,  # 轮廓宽度
        max_words=200,  # 最大单词数量
        random_state=42,  # 随机状态
        colormap='viridis'  # 设置配色方案
    ).generate(text)

    img_file_path = os.path.join(IMAGE_DIR, 'wordcloud.png')  # 指定保存路径

    # 将词云图保存为图像
    wordcloud.to_file(img_file_path)  # 使用 to_file 保存图像

    # 返回图像的 URL
    return jsonify({'wordcloud_url': f'/static/images/wordcloud.png'})

@app.route('/generate_bar_chart', methods=['POST'])
def generate_bar_chart():
    data = request.json.get('data', {})

    if not data or not isinstance(data, dict):
        return jsonify({'error': 'Invalid data format. Data should be a dictionary.'}), 400

    # 确保所有值都是数字
    try:
        labels = list(data.keys())
        values = [float(value) for value in data.values()]
    except ValueError:
        return jsonify({'error': 'All values must be numbers.'}), 400

    # 生成柱状图
    plt.clf()  # 清除当前图形，以防止重叠
    plt.bar(labels, values)
    plt.xlabel('类别')
    plt.ylabel('数量')
    plt.title('柱状图示例')

    img_file_path = os.path.join(IMAGE_DIR, 'barchart.png')  # 指定保存路径
    plt.savefig(img_file_path)
    plt.close()  # 关闭图形以释放内存

    # 返回图像的 URL
    return jsonify({'barchart_url': f'/static/images/barchart.png'})

@app.route('/static/images/<path:filename>', methods=['GET'])
def send_image(filename):
    return send_from_directory(IMAGE_DIR, filename)

if __name__ == '__main__':
    app.run(debug=True)
