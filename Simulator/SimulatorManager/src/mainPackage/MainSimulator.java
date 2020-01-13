package mainPackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainSimulator {
    public static void main(String[] args) throws Exception {
        if (args[0].length() < 2){
            throw new Exception("Argument non valide, l'argument doit correspondre à host:port debug[true/false]");
        }
        ApiConnector apiConnector = new ApiConnector(args[0].toString());
        Boolean debug = false;

        if (args[1].trim().equals("true")) {
            debug = true;
        }else if(args[1].trim().equals("false")){
            debug = false;
        }else{
            throw new Exception("Argument non valide, l'argument doit correspondre à host:port debug[true/false]");
        }
        if (debug){
            System.out.println("--- Lancement du Simulator en mode debug ---");
        }else {
            System.out.println("--- Lancement du Simulator en mode normal ---");
        }
        /*
        Scénario :
        boucle infini
        pour chaque tour de boucle, il y a un pourcentage de chance (constante) qu'un feu se produise
        dans 1 des 2 villes (lyon/villeurbanne)
        un camion aléatoire va être affecté au feu appartenant à la caserne la plus proche du feu
        les véhicules sont téléporté vers le feu
        une fois le véhicule sur place, l'intensité diminue de 1 en 1 jusqu'à ce que le feu s'éteigne
        une fois éteint le camion est téléporté vers sa caserne
         */
        Simulator simulator = new Simulator(debug,apiConnector);
        simulator.resetAll();
        simulator.getDataFromDB();
        simulator.start();
        deplacementVehicule(simulator);
        return;
    }

    private static void deplacementVehicule(Simulator simulator) throws IOException, InterruptedException {
        while(true){
            if (simulator.debug){
                if(simulator.vehiculeChoisi != null && simulator.vehiculeChoisi.getFeu() != null){
                    simulator.vehiculeChoisi.allerAuFeu();
                    simulator.apiConnector.requestPatchVehicule(
                            simulator.vehiculeChoisi,
                            simulator.vehiculeChoisi.getFeu().getCoordonnees()
                    );
                    //vehicule téléporté au feu
                }
//                simulator.traiterFeux();
            }else {
                List<Vehicule> listVehiculesFromEmergency = simulator.apiConnector.requestVehiculesFromEmergency();
                List<Vehicule> listVehiculesAffectes = new ArrayList<>();
                for (Vehicule vehicule:listVehiculesFromEmergency) {
                    if(vehicule.getId_feu() != -1){
                        listVehiculesAffectes.add(vehicule);
                    }
                }
                System.out.println(simulator.listFeuxNonTraites);
                for (Vehicule vehicule:listVehiculesAffectes) {
                    for (Vehicule vehiculeAenvoyer:simulator.listVehicules) {
                        if(vehicule.getId() == vehiculeAenvoyer.getId()) {
                            Feu feu = simulator.apiConnector.requestFeu(vehicule.getId_feu());
                            feu.setCoordonnees(simulator.apiConnector.requestCoordonnees(feu.getId_coordinate()));
                            if (vehiculeAenvoyer.getCoordonnees() == vehiculeAenvoyer.getCaserne().getCoordonnees()) {
                                vehiculeAenvoyer.setFeu(feu);
                                vehiculeAenvoyer.allerAuFeu();
                                simulator.apiConnector.requestPatchVehicule(
                                        vehiculeAenvoyer,
                                        vehiculeAenvoyer.getFeu().getCoordonnees()
                                );
                                simulator.listFeuxNonTraites.removeIf(feu1 -> (feu1.getId() == feu.getId()));
                            }
                            simulator.traiterFeux(vehiculeAenvoyer);

                        }
                    }
                }
                System.out.println(simulator.listFeuxNonTraites);
                for (Feu feu:simulator.listFeuxNonTraites) {
                    System.out.println("intensité augmentée du feu n°"+feu.getId()+" "+ feu.toString());
                    feu.augmenterIntensite();
                    simulator.apiConnector.requestPatchFeu(feu);
                }
            }
            Thread.sleep(1800);
        }
    }
}

