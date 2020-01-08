var map = null
var list_fire_layout = []
var list_camion = []

function setmap(x,y,z)
{
	map = L.map('map').setView([x, y], z);

	L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
		attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
		id: 'mapbox/streets-v11',
		accessToken: 'pk.eyJ1Ijoid2VsbGllbiIsImEiOiJjazRiMHhiM3AwOWM5M3NvMnNpZ2U5cnR0In0.4wn-kNouTu3UVKCFaTWfZA'
	}).addTo(map);

}

function removeLayout(id)
{
	map.removeLayer(id)
}

function addfire(x, y, size)
{	
	var fire = L.circle([x, y], {
	    color: 'red',
	    fillColor: '#f03',
	    fillOpacity: 0.5,
	    radius: (size * 10)
	}).addTo(map);

	list_fire_layout.push(fire)
}

function managefire()
{

	list_fire_layout.forEach(fire => removeLayout(fire))
	list_fire_layout =[]


}

function addcamion(x, y)
{
	camion = L.marker([x, y],).addTo(map);
	list_camion.push(camion)
}

function movecamion(camion, x, y)
{
	var newLatLng = new L.LatLng(x, y);
	camion.setLatLng(newLatLng);
}
