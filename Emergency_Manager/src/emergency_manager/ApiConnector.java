package emergency_manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApiConnector {
	protected String uri = "http://localhost:8081/api/";
	protected HttpClient client;

	public ApiConnector() {
		this.client = HttpClient.newHttpClient();
	}

	public List<Caserne> requestCasernes() {
		//liste des casernes
		List<Caserne> listCasernes = new ArrayList<>();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri+"fire-departments"))
				.build();
		HttpResponse<String> response = null;
		try {
			response = this.client.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			JSONObject reponse = new JSONObject(response.body());
			JSONArray jsonarray = new JSONArray(reponse.get("data").toString());
			for (int i = 0; i < jsonarray.length(); i++) {
				JSONObject jsonobject = jsonarray.getJSONObject(i);
				Caserne caserne = new Caserne((int) jsonobject.get("id"),(int) jsonobject.get("capacity"),
						(int) jsonobject.get("id_coordinate"));
//                    this.requestCoordonnees((int) jsonobject.get("id_coordinate")));
//                    this.requestCaserneTrucks((int) jsonobject.get("id")));
				listCasernes.add(caserne);
			}
			return listCasernes;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
//    public Caserne requestCaserne(int id) {
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(uri+"fire-departments/"+id))
//                .build();
//        HttpResponse<String> response = null;
//        try {
//            response = this.client.send(request, BodyHandlers.ofString());
//        } catch (IOException | InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        JSONObject reponse = new JSONObject(response.body());
//        JSONObject jsonobject = new JSONObject(reponse.get("data").toString());
//
//        Caserne caserne = new Caserne((int) jsonobject.get("id"),
//                (int) jsonobject.get("capacity"));
////                this.requestCoordonnees((int) jsonobject.get("id_coordinate")));
//        return caserne;
//    }

	public List<Feu> requestFeux() {
		List<Feu> listFeux = new ArrayList<>();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri+"fires"))
				.build();
		HttpResponse<String> response = null;
		try {
			response = this.client.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject reponse = new JSONObject(response.body());
		JSONArray jsonarray = new JSONArray(reponse.get("data").toString());
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject jsonobject = jsonarray.getJSONObject(i);
			Feu feu = new Feu((int) jsonobject.get("id"),(int) jsonobject.get("intensity"),
					(int) jsonobject.get("id_coordinate"));
//                    this.requestCoordonnees((int) jsonobject.get("id_coordinate")));
			listFeux.add(feu);
		}
		return listFeux;

	}

//    public Coordonnees requestCoordonnees(int id) {
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(uri+"coordinates/"+id))
//                .build();
//        HttpResponse<String> response = null;
//        try {
//            response = this.client.send(request, BodyHandlers.ofString());
//        } catch (IOException | InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        try {
//            JSONObject reponse = new JSONObject(response.body());
//            JSONObject jsonobject = new JSONObject(reponse.get("data").toString());
//            Coordonnees coordonnees = new Coordonnees((int) jsonobject.get("id"),
//                    Integer.parseInt(jsonobject.get("line").toString()),
//                    Integer.parseInt(jsonobject.get("column").toString()),
//                    Double.parseDouble(jsonobject.get("longitude").toString()),
//                    Double.parseDouble(jsonobject.get("latitude").toString()));
//            return coordonnees;
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }

	public List<Coordonnees> requestCoordonnees() {
		List<Coordonnees> listCoordonnees = new ArrayList<>();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri+"coordinates"))
				.build();
		HttpResponse<String> response = null;
		try {
			response = this.client.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject reponse = new JSONObject(response.body());
		JSONArray jsonarray = new JSONArray(reponse.get("data").toString());
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject jsonobject = jsonarray.getJSONObject(i);
			Coordonnees coordonnees = new Coordonnees((int) jsonobject.get("id"),
					Integer.parseInt(jsonobject.get("line").toString()),
					Integer.parseInt(jsonobject.get("column").toString()),
					Double.parseDouble(jsonobject.get("longitude").toString()),
					Double.parseDouble(jsonobject.get("latitude").toString()));
			listCoordonnees.add(coordonnees);
		}
		return listCoordonnees;

	}

	public List<Vehicule> requestVehicules() {
		List<Vehicule> listVehicules = new ArrayList<>();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri+"trucks"))
				.build();
		HttpResponse<String> response = null;
		try {
			response = this.client.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject reponse = new JSONObject(response.body());
		JSONArray jsonarray = new JSONArray(reponse.get("data").toString());
		for (int i = 0; i < jsonarray.length(); i++) {
			Vehicule vehicule;
			JSONObject jsonobject = jsonarray.getJSONObject(i);

			switch ((int) jsonobject.get("id_type")){
				case 1:
					vehicule = new Camion((int) jsonobject.get("id"),
							(int) jsonobject.get("id_type"),
							(int) jsonobject.get("id_department"),
							(int) jsonobject.get("id_coordinate"),
							(int) jsonobject.get("id_fire")
					);
//                            this.requestCoordonnees((int) jsonobject.get("id_coordinate")),
//                            this.requestCaserne((int) jsonobject.get("id_department")));
				default:
					vehicule = new Camion((int) jsonobject.get("id"),
							(int) jsonobject.get("id_type"),
							(int) jsonobject.get("id_department"),
							(int) jsonobject.get("id_coordinate"),
							(int) jsonobject.get("id_fire")
					);
//                            this.requestCoordonnees((int) jsonobject.get("id_coordinate")),
//                            this.requestCaserne((int) jsonobject.get("id_department")));
			}

			listVehicules.add(vehicule);
		}
		return listVehicules;
	}

	public List<TypeVehicule> requestTypesVehicule() {
		List<TypeVehicule> listTypesVehicule = new ArrayList<>();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri+"vehicle-types"))
				.build();
		HttpResponse<String> response = null;
		try {
			response = this.client.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject reponse = new JSONObject(response.body());
		JSONArray jsonarray = new JSONArray(reponse.get("data").toString());
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject jsonobject = jsonarray.getJSONObject(i);
			TypeVehicule typeVehicule =
					new TypeVehicule((int) jsonobject.get("id"),(String) jsonobject.get("label"),
							(int) jsonobject.get("speed"),(int) jsonobject.get("efficiency"));
//                    this.requestCoordonnees((int) jsonobject.get("id_coordinate")));
			listTypesVehicule.add(typeVehicule);
		}
		return listTypesVehicule;

	}

	public void requestPatchFeu(Feu feu) throws IOException {
		var values = new HashMap<String, Integer>() {{
			put ("intensity", feu.getIntensity());
		}};

		var objectMapper = new ObjectMapper();
		String requestBody = objectMapper
				.writeValueAsString(values);
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri+"fires/update-intensity/"+feu.getId()))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.header("Content-Type","application/json")
				.build();
		HttpResponse<String> response = null;
		try {
			response = this.client.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			JSONObject reponse = new JSONObject(response.body());
		}catch(Exception e){
			e.printStackTrace();
		}
//        System.out.println(reponse);
	}

	public void requestPatchVehicule(Vehicule vehicule,Feu feu) throws IOException {
		Object values;
		if (feu != null) {
			values = new HashMap<String, String>() {{
				put("id_fire", Integer.toString(feu.getId()));
			}};
		}else {
			values = new HashMap<String, String>() {{
				put("id_fire", null);
			}};
		}

		var objectMapper = new ObjectMapper();
		String requestBody = objectMapper
				.writeValueAsString(values);
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri+"trucks/assign-fire/"+vehicule.getId()))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.header("Content-Type","application/json")
				.build();
		HttpResponse<String> response = null;
		try {
			response = this.client.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject reponse = new JSONObject(response.body());
	}
	public void requestPatchVehicule(Vehicule vehicule,Coordonnees coordonnees) throws IOException {
		var values = new HashMap<String, Integer>() {{
			put("id_coordinate", coordonnees.getId());
		}};

		var objectMapper = new ObjectMapper();
		String requestBody = objectMapper
				.writeValueAsString(values);
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri+"trucks/update-coordinate/"+vehicule.getId()))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.header("Content-Type","application/json")
				.build();
		HttpResponse<String> response = null;
		try {
			response = this.client.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject reponse = new JSONObject(response.body());
	}

	public Feu requestFeu(int id_feu) {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri+"fires/"+id_feu))
				.build();
		HttpResponse<String> response = null;
		try {
			response = this.client.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			JSONObject reponse = new JSONObject(response.body());
			JSONObject jsonobject = new JSONObject(reponse.get("data").toString());
			Feu feu = new Feu(
					(int) jsonobject.get("id"),
					(int) jsonobject.get("intensity"),
					(int) jsonobject.get("id_coordinate"));
			return feu;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public void requestResetAllFeux() {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri+"fires/reset/all"))
				.build();
		HttpResponse<String> response = null;
		try {
			response = this.client.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			JSONObject reponse = new JSONObject(response.body());
			JSONObject jsonobject = new JSONObject(reponse.get("data").toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
