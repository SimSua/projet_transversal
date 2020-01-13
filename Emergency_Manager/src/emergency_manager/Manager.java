package emergency_manager;

import java.util.ArrayList;

public class Manager {
	
	public final static boolean bdd = false;
	
	public static void main(String[] args) {
		Boolean debug = false;
		if (debug){
			System.out.println("--- Lancement de l'Emergency Manager en mode debug ---");
		}else {
			System.out.println("--- Lancement de l'Emergency Manager en mode normal ---");
		}
		EmergencyManager emergencyManager = new EmergencyManager(debug);
		emergencyManager.start();
	}
}
