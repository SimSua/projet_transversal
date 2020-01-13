package emergency_manager;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.*;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


public class EmergencyManager extends Thread {
	public Boolean debug;
	private Feu nouveauFeu;
	public Vehicule vehiculeChoisi;
	private Caserne caserneChoisie = null;
	public List<Ville> listVilles = new ArrayList<>();
	public List<Caserne> listCasernes = new ArrayList<>();
	public List<Vehicule> listVehicules = new ArrayList<>();
	public List<Feu> listFeux = new ArrayList<>();
	private List<Feu> listFeuxNonTraites = new ArrayList<>();
	public List<TypeVehicule> listTypesVehicule = new ArrayList<>();
	public List<Coordonnees> listCoordonnees = new ArrayList<>();
	public ApiConnector apiConnector;
	protected Ville v;
	public EmergencyManager(Boolean debug,ApiConnector apiConnector) {

		this.debug = debug;
		this.apiConnector = apiConnector;
	}
	@Override
	public void run() {
		List<Integer> listIdfeuxTraites = new ArrayList<>();
		while(true) {
			System.out.println("update data");
			getDataFromDB();
			for (Vehicule vehicule:listVehicules) {
				if (vehicule.getId_feu() != -1) {
					listIdfeuxTraites.add(vehicule.getId_feu());
				}
			}
			try {
				attribuerFeu();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void getDataFromDB(){
		listCasernes = apiConnector.requestCasernes();
		listVehicules = apiConnector.requestVehicules();
		listFeux = apiConnector.requestFeux();
		listTypesVehicule = apiConnector.requestTypesVehicule();
		listCoordonnees = apiConnector.requestCoordonnees();
		listFeuxNonTraites = apiConnector.requestFeuxNonTraites();

		//set Coordonnees
		for (Coordonnees coordonnees:listCoordonnees){
			for (Vehicule vehicule:listVehicules){
				if (vehicule.getId_coordonnees() == coordonnees.getId()){
					vehicule.setCoordonnees(coordonnees);
				}
			}
			for (Caserne caserne:listCasernes){
				if (caserne.getId_coordonnees() == coordonnees.getId()){
					caserne.setCoordonnees(coordonnees);
				}
			}
			for (Feu feu:listFeux){
				if (feu.getId_coordinate() == coordonnees.getId()){
					feu.setCoordonnees(coordonnees);
				}
			}
			for (Feu feu:listFeuxNonTraites){
				if (feu.getId_coordinate() == coordonnees.getId()){
					feu.setCoordonnees(coordonnees);
				}
			}
		}
		//set type vehicule
		for (TypeVehicule type:listTypesVehicule){
			for (Vehicule vehicule:listVehicules){
				if (vehicule.getId_type() == type.getId()){
					vehicule.setType(type);
				}
			}
		}
		//add vehicule to caserne/set caserne to vehicule
		for (Vehicule vehicule:listVehicules) {
			for (Caserne caserne:listCasernes) {
				if (vehicule.getId_caserne() == caserne.getId()) {
					caserne.addVehicule(vehicule);
					vehicule.setCaserne(caserne);
				}
			}
		}
	}

	private void attribuerFeu() throws IOException {
		int indexOfFeu = -1;
		for (Feu feuAtraiter:listFeuxNonTraites) {
			getCaserneDispo(feuAtraiter);
			if (caserneChoisie != null) {
				vehiculeChoisi = getChoixVehicule();
				vehiculeChoisi.setFeu(feuAtraiter);
				System.out.println("Vehicule n°"+vehiculeChoisi.getId()+" affecté au feu n°"+
						feuAtraiter.getId());
				apiConnector.requestPatchVehicule(vehiculeChoisi,feuAtraiter);
				indexOfFeu = listFeuxNonTraites.indexOf(feuAtraiter);
			} else {
				indexOfFeu = -1;
				System.out.println("Pas de véhicule disponible.");
			}
		}
		if (indexOfFeu != -1) {
			listFeuxNonTraites.remove(indexOfFeu);
		}
	}

	private Vehicule getChoixVehicule() {
		return caserneChoisie.getVehiculesDispo().get(
				new Random().nextInt(caserneChoisie.getVehiculesDispo().size()));
	}

	private void getCaserneDispo(Feu feuAtraiter) {
		double ancDistance = 0;
		double nouvelleDistance = 0;
		boolean first = true;
		caserneChoisie = null;
		vehiculeChoisi = null;
		for (Caserne caserne : listCasernes) {
			if (first && caserne.getVehiculesDispo().size() > 0) {
				ancDistance = sqrt(pow(feuAtraiter.getCoordonnees().getColonne() - caserne.getCoordonnees().getColonne(), 2)
						+ pow(feuAtraiter.getCoordonnees().getLigne() - caserne.getCoordonnees().getLigne(), 2));
				caserneChoisie = caserne;
				first = false;
			} else if (!first && caserne.getVehiculesDispo().size() > 0) {
				nouvelleDistance = sqrt(pow(feuAtraiter.getCoordonnees().getColonne() - caserne.getCoordonnees().getColonne(), 2)
						+ pow(feuAtraiter.getCoordonnees().getLigne() - caserne.getCoordonnees().getLigne(), 2));
				if (nouvelleDistance < ancDistance) {
					caserneChoisie = caserne;
				}
				ancDistance = nouvelleDistance;
			}
		}
	}
}
