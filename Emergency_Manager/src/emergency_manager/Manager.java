package emergency_manager;

import java.util.ArrayList;

public class Manager {
	
	public static boolean bdd = true;
	
	public static void main(String[] args) throws Exception {
		if (args[0].length() < 2){
			throw new Exception("Argument non valide, l'argument doit correspondre à host:port debug[true/false]");
		}
		ApiConnector apiConnector = new ApiConnector(args[0]);
		Boolean debug = false;
		if (args[1].trim().equals("true")) {
			debug = true;
		}else if(args[1].trim().equals("false")){
			debug = false;
		}else{
			throw new Exception("Argument non valide, l'argument doit correspondre à host:port debug[true/false]");
		}
		if (debug){
			System.out.println("--- Lancement de l'Emergency Manager en mode debug ---");
		}else {
			System.out.println("--- Lancement de l'Emergency Manager en mode normal ---");
		}
		EmergencyManager emergencyManager = new EmergencyManager(debug,apiConnector);
		emergencyManager.start();
	}
}
