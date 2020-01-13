from flask import *
import requests, json
import sys
from .model import *

api="http://164.4.11:8081/api/sim/"
if len(sys.argv) == 4:
	if sys.argv[3] == "docker":
		api="http://"+str(sys.argv[2])+"/api/sim/"
print(api)
app = Flask(__name__)

@app.route('/')
def Lyon():
	script_map = url_for('static', filename='js/map_index.js')
	script_fire = url_for('static', filename='js/fire.js')
	return render_template("index.html", map_index = script_map, fire = script_fire, X=45.7579341, Y=4.8307417, Z=15)

@app.route('/Villeurbannes')
def Villerbanne():
	script_map = url_for('static', filename='js/map_index.js')
	script_fire = url_for('static', filename='js/fire.js')
	return render_template("index.html", map_index = script_map, fire = script_fire, X=45.7699875, Y=4.8870721, Z=15)


# API request Fire
@app.route('/Fires',methods = ['GET'])
def fire():
	fires = requests.get(api+"fires")
	coordinates = requests.get(api+"coordinates")
	
	fires_str = json.loads(fires.text)
	coordinates_str = json.loads(coordinates.text)
	
	result = find_coordinate(fires_str, coordinates_str)

	return result

if __name__ == "__main__":
	app.run(debug=true)
