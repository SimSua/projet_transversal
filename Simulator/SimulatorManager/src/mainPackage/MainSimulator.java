package mainPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.*;

public class MainSimulator {
    public static void main(String[] args) throws InterruptedException {
        Caserne caserne1 = new Caserne(5,5,new Coordonnees(8,10));
        Caserne caserne2 = new Caserne(10,20,new Coordonnees(16,20));
        Caserne caserne3 = new Caserne(15,15,new Coordonnees(20,15));
        List<Caserne> listCasernes = new ArrayList<>();
        listCasernes.add(caserne1);
        listCasernes.add(caserne2);
        listCasernes.add(caserne3);

        List<Vehicule> listVehicules= new ArrayList<>();
        //Mise en place des casernes avec les camions (test avec 3 casernes et 3 camions par caserne vitesse aléatoire)
        for(int i=0;i<9;i=i+3){
            int vitesseCamion = (int) (1 + (Math.random() * (10 - 1)));
            Camion camion1 = new Camion(i,vitesseCamion,caserne1.getLigne(),caserne1.getColonne()
                    ,caserne1.getCoordonnees(),caserne1);
            Camion camion2 = new Camion(i+1,vitesseCamion,caserne2.getLigne(),caserne2.getColonne()
                    ,caserne1.getCoordonnees(),caserne2);
            Camion camion3 = new Camion(i+2,vitesseCamion,caserne3.getLigne(),caserne3.getColonne()
                    ,caserne3.getCoordonnees(),caserne3);
            listVehicules.add(camion1);
            listVehicules.add(camion2);
            listVehicules.add(camion3);
            listCasernes.get(new Random().nextInt(listCasernes.size())).addVehicule(camion1);
            listCasernes.get(new Random().nextInt(listCasernes.size())).addVehicule(camion2);
            listCasernes.get(new Random().nextInt(listCasernes.size())).addVehicule(camion3);
        }
        for (Caserne caserne:listCasernes) {
            System.out.println(caserne.toString());
        }

        //Mise en place des Villes et des feux
        Ville lyon = new Ville("Lyon",new Coordonnees(19,24));
        Ville villeurbanne = new Ville("Villeurbanne",new Coordonnees(19,30));
        lyon.addCaserne(caserne1);
        lyon.addCaserne(caserne2);
        villeurbanne.addCaserne(caserne3);
        System.out.println(lyon.toString());
        System.out.println(villeurbanne.toString());
        List<Ville> listVilles = new ArrayList<>();
        List<Feu> listFeux = new ArrayList<>();
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
        List<Feu> listFeuxNonTraites = new ArrayList<>();
        int indexOfFeu = -1;
        while (true){
            Thread.sleep(2000);
            int pourcentageChance = (int) (1 + (Math.random() * 10));
            List<Boolean> tirage = new ArrayList<>();
            for(int i=0;i<10;i++){
                if(i<pourcentageChance){
                    tirage.add(true);
                }else {
                    tirage.add(false);
                }
            }
            Boolean nouveauFeu = tirage.get(new Random().nextInt(tirage.size()));
            if (nouveauFeu) {
                System.out.println("nouveau feu");
                int intensite = (int) (1 + (Math.random() * 10));
                int ligneFeu = (int) (1 + (Math.random() * 30));
                int colonneFeu = (int) (1 + (Math.random() * 30));
                Feu feu = new Feu(intensite,ligneFeu,colonneFeu,new Coordonnees(7,5));
                listFeuxNonTraites.add(feu);
                for (Feu feuAtraiter:listFeuxNonTraites) {
                    System.out.println(listFeuxNonTraites);
                    double ancDistance = 0;
                    double nouvelleDistance = 0;
                    boolean first = true;
                    Caserne caserneChoisie = null;
                    for (Caserne caserne : listCasernes) {
                        if (first && caserne.getVehiculesDispo().size() > 0) {
                            ancDistance = sqrt(pow(feuAtraiter.getColonne() - caserne.getColonne(), 2)
                                    + pow(feuAtraiter.getLigne() - caserne.getLigne(), 2));
                            caserneChoisie = caserne;
                            first = false;
                        } else if (!first && caserne.getVehiculesDispo().size() > 0) {
                            nouvelleDistance = sqrt(pow(feuAtraiter.getColonne() - caserne.getColonne(), 2)
                                    + pow(feuAtraiter.getLigne() - caserne.getLigne(), 2));
                            if (nouvelleDistance < ancDistance) {
                                caserneChoisie = caserne;
                            }
                            ancDistance = nouvelleDistance;
                        }
                    }
                    if (caserneChoisie != null) {
                        Vehicule vehiculeChoisi = caserneChoisie.getVehiculesDispo().get(
                                new Random().nextInt(caserneChoisie.getVehiculesDispo().size()));
                        vehiculeChoisi.setFeu(feuAtraiter);
                        System.out.println(caserneChoisie.getVehicules().get(caserneChoisie.getVehicules().indexOf(vehiculeChoisi)));
                        indexOfFeu = listFeuxNonTraites.indexOf(feuAtraiter);
                        //Une fois le vehicule assigné à un feu

                    } else {
                        indexOfFeu = -1;
                        System.out.println("Pas de véhicule disponible.");
                    }
                }
                if (indexOfFeu != -1) {
                    listFeuxNonTraites.remove(indexOfFeu);
                }
            }else{
                System.out.println("Pas de nouveau feu");
            }
        }

    }
}
