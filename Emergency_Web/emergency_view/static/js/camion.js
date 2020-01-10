bool = 1

function get_trucks()
{

	$.ajax(
	{
		url : "http://localhost:5000/Camions",
		type : 'get',
		dataType : 'json',

		success : function(resultat){
			result_camions  = resultat
		},

		error : function(resultat){
			result_camions =  null
		}

	})

	if(result_camions == undefined)
	{
		return null
	}
	else
	{
		return result_camions
	}
}

function index_camions(camion)
{

	 if(bool == 1)
	 {
	 	add_trucks(camion['id'], camion['latitude'], camion['longitude'])
	 	bool = 0
	 	
	 }
	 else
	 {
	 	manage_trucks(camion['id'], camion['latitude'], camion['longitude'])
	 }
}



function set_trucks()
{
	setInterval(function()
	{ 

		var camions = get_trucks()

		if (camions != null)
		{
			camions['data'].forEach(element => index_camions(element))
		}


 	}, 5000);
}
