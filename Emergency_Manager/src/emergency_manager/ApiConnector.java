package emergency_manager;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class ApiConnector {
	protected String uri = "http://localhost:8081/api/";
	protected HttpClient client;
	
	public ApiConnector() {
		this.client = HttpClient.newHttpClient();
	}
	
	public String requestCasernes() {
		HttpRequest request = HttpRequest.newBuilder()
		         .uri(URI.create(uri+"fire-departments"))
		         .build();
		HttpResponse<String> response = null;
		try {
			response = this.client.send(request, BodyHandlers.ofString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.body(); 
	}
	
	public String requestFeux() {
		HttpRequest request = HttpRequest.newBuilder()
		         .uri(URI.create(uri+"fires"))
		         .build();
		HttpResponse<String> response = null;
		try {
			response = this.client.send(request, BodyHandlers.ofString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.body(); 
	
	}
	
	public String requestCoordonnees() {
		HttpRequest request = HttpRequest.newBuilder()
		         .uri(URI.create(uri+"fire-departments"))
		         .build();
		HttpResponse<String> response = null;
		try {
			response = this.client.send(request, BodyHandlers.ofString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.body(); 
	
	}
	
	public String requestVehicules() {
		HttpRequest request = HttpRequest.newBuilder()
		         .uri(URI.create(uri+"trucks"))
		         .build();
		HttpResponse<String> response = null;
		try {
			response = this.client.send(request, BodyHandlers.ofString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.body(); 
	}
	
}
