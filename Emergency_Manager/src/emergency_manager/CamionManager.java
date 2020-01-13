package emergency_manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class CamionManager extends Thread {
	protected Ville v;
	public CamionManager(Ville v) {
		// TODO Auto-generated constructor stub
		super();
		this.v=v;
	}
	@Override
	public void run() {
		System.out.println("Thread Camion manager de la ville "+v.getName());
		while(true) {
			v.setFeux(this.getFeux(v));
			envoiCamion();
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void envoiCamion() {
		//Pour envoyer les vehicule, on vérifie les vehicules disponibles dans la caserne
		//On vérifie ensuite quels sont les feux allumés
		//On tri les feux du plus intense au moins intense
		//On envoie les vehicules disponibles sur les feux
		
		//Liste des véhicules disponibles
		ArrayList<Vehicule> vehiculesDispo = new ArrayList<Vehicule>();
		if(v.getCasernes().size() != 0) {
			for(Vehicule ve : v.getCasernes().get(0).getVehicules()) {
				ve.estRevenu(); //On actualise l'état des vehicules
				if(ve.estOccupe()==false) {
					vehiculesDispo.add(ve);
				}
			}
		}
		//Liste des feux allumés
		ArrayList<Feu> feuAllume = new ArrayList<Feu>();		
		for(Feu f: v.getFeux()) {
			if(f.getIntensite() > 0) {
				feuAllume.add(f);
			}
		}
		
		//Tri de la liste des feux
		feuAllume.sort(new Feu(0));
		System.out.println(feuAllume.size());
		
		//Envoi de camions sur les feux;
		int nb = 0;
		for(Vehicule vehi:vehiculesDispo) {
			try {
				if(nb < feuAllume.size()) {
					vehi.envoyer(feuAllume.get(nb++));
				}else {
					System.out.println("Le véhicule "+vehi+" n'a pas de feu a éteindre");
				}
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<Feu> getFeux(Ville v){
		ArrayList<Feu> feux = new ArrayList<Feu>();
		if(!Manager.bdd) {
			for(int i = 0; i < 10; i++) {
				for(int j = 0; j < 6; j++) {
					int intensite = 0;
					if((int)Math.abs(Math.random()*10)==3) {
						intensite=(int)Math.abs(Math.random()*10);
					}
					Feu f = new Feu(new Coordonnees(i+j,100*i, 100*j, j, i), intensite);
					feux.add(f);
					System.out.println(v.getName()+" : "+f.toString());
				}
			}
		}else {
			System.out.println("Récupération des feux dans la ville "+v.getName()+" depuis la base de données réel");
			ApiConnector conn = new ApiConnector();
			System.out.println(conn.requestFeux());
		}
		System.out.println("Nombre de feux : "+feux.size());
		return feux;
	}
}
