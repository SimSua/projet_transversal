//var map = L.map('map').setView([45.7730904, 4.8410339], 14);
var map = null
var list_layout = []
var list_fire = []

function setmap(x,y,z)
{
	map = L.map('map').setView([x, y], z);

	L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
		attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
		id: 'mapbox/streets-v11',
		accessToken: 'pk.eyJ1Ijoid2VsbGllbiIsImEiOiJjazRiMHhiM3AwOWM5M3NvMnNpZ2U5cnR0In0.4wn-kNouTu3UVKCFaTWfZA'
	}).addTo(map);

}


function addfire(x, y, size)
{	
	var fire = L.circle([x, y], {
	    color: 'red',
	    fillColor: '#f03',
	    fillOpacity: 0.5,
	    radius: (size * 10)
	}).addTo(map);

	list_layout.push(fire)
}

function setfire()
{
	fires = null
	setInterval(function(){ 

	fires = get_fire()

 }, 5000);

}

function removefire(fire)
{
	map.removeLayer(fire)
}

//list_fire.push(addFire(45.77,4.84,10))

