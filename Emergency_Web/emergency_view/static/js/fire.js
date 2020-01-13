var result = null

function get_fire()
{

	$.ajax(
	{
		url : "http://localhost:5000/Fires",
		type : 'get',
		dataType : 'json',

		success : function(resultat){
			result  = resultat
		},

		error : function(resultat){
			result =  null
		}

	})

	if(result == null)
	{
		return null
	}
	else
	{
		return result
	}
}

function indexfire(fire)
{
	if(fire['intensity'] != 0)
	 {
	 	addfire(fire['latitude'], fire['longitude'], fire['intensity'])
	 }
}



function setfire()
{
	setInterval(function()
	{ 

		var fires = get_fire()
		
		if (fires != null)
		{

				managefire()
				fires['data'].forEach(fire => indexfire(fire))
			
		}


 	}, 5000);


}
