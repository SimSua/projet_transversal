package emergency_manager;

import java.util.ArrayList;

public class Caserne {
	private int id;
	private int capacite;
	private int id_coordonnees;
	private ArrayList<Vehicule> vehicules;
	protected Coordonnees coord;
	
	public Caserne(Coordonnees coord) {
		System.out.println("Creation d'une caserne");
		this.coord = coord;
		vehicules = new ArrayList<Vehicule>();
		this.generateVehicules();
	}

	public Caserne(int id, int capacite,int id_coordonnees) {
		this.id = id;
		this.capacite = capacite;
		this.id_coordonnees = id_coordonnees;
	}

    public ArrayList<Vehicule> generateVehicules() {
		//if(true) {
		if(Manager.bdd) {
			ApiConnector connector = new ApiConnector();
			vehicules = (ArrayList<Vehicule>)connector.requestVehicules();
		}else {
			//Connection bdd
			for (int i = 0; i < 5; i++) {
				this.vehicules.add(new Camion(this, this.getCoordonnees()));
			}
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
