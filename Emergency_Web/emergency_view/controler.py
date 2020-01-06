from flask import *

app = Flask(__name__)

@app.route('/')
def Lyon():
	script_map = url_for('static', filename='js/map_index.js')
	script_fire = url_for('static', filename='js/getfire.js')
	return render_template("index.html", map_index = script_map, fire = script_fire, X=45.7579341, Y=4.8307417, Z=15)

@app.route('/Villerbanne')
def Villerbanne():
	script_map = url_for('static', filename='js/map_index.js')
	script_fire = url_for('static', filename='js/getfire.js')
	return render_template("index.html", map_index = script_map, fire = script_fire, X=45.7699875, Y=4.8870721, Z=15)

if __name__ == "__main__":
	app.run()

