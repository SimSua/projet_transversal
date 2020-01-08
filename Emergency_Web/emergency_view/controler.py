from flask import *

app = Flask(__name__)

@app.route('/')
def index():
	script_map = url_for('static', filename='js/map_index.js')
	return render_template("index.html", map_index = script_map)


if __name__ == "__main__":
	app.run()

