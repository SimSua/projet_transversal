package mainPackage;

import com.fasterxml.jackson.core.JsonProcessingException;

public class MainSimulator {
    public static void main(String[] args) throws InterruptedException, JsonProcessingException {
        Boolean debug = false;
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
        Simulator simulator = new Simulator(debug);
        simulator.getDataFromDB();
        simulator.start();
        deplacementVehicule(simulator);
        return;
    }

    private static void deplacementVehicule(Simulator simulator) throws JsonProcessingException, InterruptedException {
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
                simulator.traiterFeux();
            }else {
                simulator.updateDataFeuxFromDB();
                for (Vehicule vehicule:simulator.listVehicules) {
                    if (vehicule.getFeu() != null){
                        vehicule.allerAuFeu();
                        simulator.apiConnector.requestPatchVehicule(
                                vehicule,
                                vehicule.getFeu().getCoordonnees()
                        );
                    }
                }
                simulator.traiterFeux();
            }
            Thread.sleep(1800);
        }
    }
}

