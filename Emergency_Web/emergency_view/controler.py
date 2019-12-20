from flask import *

app = Flask(__name__)

@app.route('/')
def index():
	script_map = url_for('static', filename='js/map_index.js')
	script_fire = url_for('static', filename='js/getfire.js')
	return render_template("index.html", map_index = script_map, fire = script_fire, X=45.7730904, Y=4.8410339, Z=14)


if __name__ == "__main__":
	app.run()

