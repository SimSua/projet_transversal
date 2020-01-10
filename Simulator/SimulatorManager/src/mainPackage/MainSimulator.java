package mainPackage;
public class MainSimulator {
    public static void main(String[] args) throws InterruptedException {
        Boolean debug = false;


        if (debug) {
//            Caserne caserne1 = new Caserne(5, 5, new Coordonnees(8, 10));
//            Caserne caserne2 = new Caserne(10, 20, new Coordonnees(16, 20));
//            Caserne caserne3 = new Caserne(15, 15, new Coordonnees(20, 15));
//
//            listCasernes.add(caserne1);
//            listCasernes.add(caserne2);
//            listCasernes.add(caserne3);
//
//
//            //Mise en place des casernes avec les camions (test avec 3 casernes et 3 camions par caserne vitesse aléatoire)
//            for (int i = 0; i < 9; i = i + 3) {
//                int vitesseCamion = (int) (1 + (Math.random() * (10 - 1)));
//                Camion camion1 = new Camion(i, vitesseCamion, caserne1.getLigne(), caserne1.getColonne()
//                        , caserne1.getCoordonnees(), caserne1);
//                Camion camion2 = new Camion(i + 1, vitesseCamion, caserne2.getLigne(), caserne2.getColonne()
//                        , caserne1.getCoordonnees(), caserne2);
//                Camion camion3 = new Camion(i + 2, vitesseCamion, caserne3.getLigne(), caserne3.getColonne()
//                        , caserne3.getCoordonnees(), caserne3);
//                listVehicules.add(camion1);
//                listVehicules.add(camion2);
//                listVehicules.add(camion3);
//                listCasernes.get(new Random().nextInt(listCasernes.size())).addVehicule(camion1);
//                listCasernes.get(new Random().nextInt(listCasernes.size())).addVehicule(camion2);
//                listCasernes.get(new Random().nextInt(listCasernes.size())).addVehicule(camion3);
//            }
//            for (Caserne caserne : listCasernes) {
//                System.out.println(caserne.toString());
//            }
//
//            //Mise en place des Villes et des feux
//            Ville lyon = new Ville("Lyon", new Coordonnees(19, 24));
//            Ville villeurbanne = new Ville("Villeurbanne", new Coordonnees(19, 30));
//            lyon.addCaserne(caserne1);
//            lyon.addCaserne(caserne2);
//            villeurbanne.addCaserne(caserne3);
//            System.out.println(lyon.toString());
//            System.out.println(villeurbanne.toString());

        }else{
            Simulator simulator = new Simulator(debug);
            simulator.initData();
            simulator.start();
            while(true){
                Thread.sleep(1800);
                if(simulator.vehiculeChoisi != null && simulator.vehiculeChoisi.getFeu() != null){
                    simulator.vehiculeChoisi.allerAuFeu();
                    simulator.apiConnector.request

                    //vehicule téléporté au feu

                }
                simulator.traiterFeux();
            }
        }
        return;
        /*
        Scénario :
        boucle infini
        pour chaque tour de boucle, il y a un pourcentage de chance qu'un ou plusieurs feux se produisent
        dans 1 des 2 villes (lyon/villeurbanne)
        un camion aléatoire va être affecté au feu appartenant à la caserne la plus proche du feu
        les véhicules sont téléporté vers le feu
        une fois le véhicule sur place, l'intensité diminue de 1 en 1 jusqu'à ce que le feu s'éteigne
        une fois éteint le camion est téléporté vers sa caserne
         */
//        Simulator simulator = new Simulator(listCasernes,listVehicules,listVilles);
//        simulator.start();
//        //Emergency Manager local
//        while(true){
//            Thread.sleep(1800);
//            if(simulator.vehiculeChoisi != null && simulator.vehiculeChoisi.getFeu() != null){
//                simulator.vehiculeChoisi.allerAuFeu();
//                //vehicule téléporté au feu
//
//            }
//            simulator.traiterFeux();
//        }


    }
}
