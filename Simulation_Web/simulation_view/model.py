import json

def get_coordinate(ID, coordinate) :
	latitude = None
	longitude = None

	for tab in coordinate["data"] :	
		#print(tab)
		if int(tab["id"])==ID:
			return tab["latitude"], tab["longitude"]
		
	return latitude, longitude

def find_coordinate(data, coordinate) : 

	i = 0
	for tab in data["data"] :

		for key, value in tab.items() :

			if key == "id_coordinate" :
				latitude, longitude,  = get_coordinate(value, coordinate)
				break

		data["data"][i].update({'latitude':latitude})
		data["data"][i].update({'longitude':longitude})
		i += 1
	return data

