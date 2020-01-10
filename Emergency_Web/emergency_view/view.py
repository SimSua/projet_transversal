from flask import *
import requests, json
from .model import *

app = Flask(__name__)

@app.route('/')
def Lyon():
	script_map = url_for('static', filename='js/map_index.js')
	script_fire = url_for('static', filename='js/fire.js')
	script_camion = url_for('static', filename='js/camion.js')
	return render_template("index.html", map_index = script_map, fire = script_fire, camion = script_camion, X=45.7579341, Y=4.8307417, Z=15)

@app.route('/Villerbanne')
def Villerbanne():
	script_map = url_for('static', filename='js/map_index.js')
	script_fire = url_for('static', filename='js/fire.js')
	script_camion = url_for('static', filename='js/camion.js')
	return render_template("index.html", map_index = script_map, fire = script_fire, camion = script_camion, X=45.7699875, Y=4.8870721, Z=15)


# API request Fire
@app.route('/Fires',methods = ['GET'])
def fire():
	fires = requests.get("http://164.4.1.11:8081/api/fires")
	coordinates = requests.get("http://164.4.1.11:8081/api/coordinates")
	
	fires_str = json.loads(fires.text)
	coordinates_str = json.loads(coordinates.text)
	
	result = find_coordinate(fires_str, coordinates_str)

	return result

# API request Truck
@app.route('/Camions',methods = ['GET'])
def camion():
	trucks = requests.get("http://164.4.1.11:8081/api/trucks")
	coordinates = requests.get("http://164.4.1.11:8081/api/coordinates")

	trucks_str = json.loads(trucks.text)
	coordinates_str = json.loads(coordinates.text)

	result = find_coordinate(trucks_str, coordinates_str)
	return result


if __name__ == "__main__":
	app.run(debug=true)
