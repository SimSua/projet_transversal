package emergency_manager;

import java.util.ArrayList;

public class Manager {
	
	private final static boolean bdd = false;
	
	public static void main(String[] args) {
		System.out.println("--- Lancement de l'Emergency Manager ---");
		Ville lyon = new Ville("Lyon");
		Ville villeurbannes = new Ville("Villeurbannes");
		creerCaserne(lyon);
		creerCaserne(villeurbannes);
		lyon.setFeux(creerFeu());
		villeurbannes.setFeux(creerFeu());
	}
	
	public static void creerCaserne(Ville v) {
		if(!Manager.bdd) {
			v.addCaserne(new Caserne());
		}else {
			System.out.println("Récupération des casernes depuis la base de données réel");
		}
	}
	
	public static ArrayList<Feu> creerFeu(){
		ArrayList<Feu> feux = new ArrayList<Feu>();
		if(!Manager.bdd) {
			for(int i = 0; i < 10; i++) {
				for(int j = 0; j < 6; j++) {
					feux.add(new Feu(new Coordonnees(100*i, 100*j), (int)Math.abs(Math.random()*10), j, i));
				}
			}
		}else {
			System.out.println("Récupération des feux depuis la base de données réel");
		}
		return feux;
	}

}
