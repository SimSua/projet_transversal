package emergency_manager;

import java.util.ArrayList;


public class Caserne {
	private ArrayList<Vehicule> vehicules;
	protected Coordonnees coord;
	
	public Caserne(Coordonnees coord) {
		System.out.println("Creation d'une caserne");
		this.coord = coord;
		vehicules = new ArrayList<Vehicule>();
		this.generateVehicules();
	}

	public ArrayList<Vehicule> generateVehicules() {
		//if(true) {
		if(Manager.bdd) {
			ApiConnector connector = new ApiConnector();
			String s = connector.requestCasernes();
			System.out.println(s);
		}
		//Connection bdd
		for(int i = 0; i < 5; i++) {
			this.vehicules.add(new Camion(this, this.getCoordonnees()));
		}
		return vehicules;
	}
	
	public ArrayList<Vehicule> getVehicules(){
		return this.vehicules;
	}

	public void setVehicules(ArrayList<Vehicule> vehicules) {
		this.vehicules = vehicules;
	} 
	
	public Coordonnees getCoordonnees() {
		return this.coord;
	}
}
