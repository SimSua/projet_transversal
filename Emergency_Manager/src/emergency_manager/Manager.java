package emergency_manager;

import java.util.ArrayList;

public class Manager {
	
	public static boolean bdd = true;
	
	public static void main(String[] args) {
		System.out.println("--- Lancement de l'Emergency Manager ---");
		ArrayList<Ville> villes = new ArrayList<Ville>();
		Ville lyon = new Ville("Lyon");
		villes.add(lyon);
		creerCaserne(lyon);
		for(Ville v:villes) {
			CamionManager cm = new CamionManager(lyon);
			cm.start();
		}
	}
	
	public static void creerCaserne(Ville v) {
		if(!Manager.bdd) {
			v.addCaserne(new Caserne(new Coordonnees(0, 0, 0, 0)));
		}else {
			System.out.println("Récupération des casernes depuis la base de données réel");
		}
	}
	
	public static void testApi() {
		ApiConnector conn = new ApiConnector();
		Manager.bdd = conn.test();
	}
}
