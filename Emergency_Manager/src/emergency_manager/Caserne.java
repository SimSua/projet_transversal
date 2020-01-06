package emergency_manager;

import java.util.ArrayList;

public class Caserne {
	private ArrayList<Vehicule> vehicules;
	
	public Caserne() {
		System.out.println("Creation d'une caserne");
		vehicules = new ArrayList<Vehicule>();
		this.getVehicules();
	}

	public ArrayList<Vehicule> getVehicules() {
		//Connection bdd
		for(int i = 0; i < 5; i++) {
			this.vehicules.add(new Camion(this));
		}
		return vehicules;
	}

	public void setVehicules(ArrayList<Vehicule> vehicules) {
		this.vehicules = vehicules;
	} 
}
