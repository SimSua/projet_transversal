package mainPackage;

public class MainSimulator {
    public static void main(String[] args){
        Caserne caserne1 = new Caserne(5,5,new Coordonnees(8,10));
        Camion camion1 = new Camion(5,1,1,
                new Coordonnees(30.2,35.5),caserne1
        );
        Ville ville1 = new Ville("Lyon",new Coordonnees(19,24));
        Feu feu1 = new Feu(10,3,4,new Coordonnees(7,5));

        System.out.println(caserne1.toString());
        System.out.println(camion1.toString());
        System.out.println(ville1.toString());
        System.out.println(feu1.toString());

        /*
        Scénario :
        un camion à la position 1,1 de vitesse 5 va se déplacer instantanément au feu1 à la position 7,5 puis
        le feu va progressivement diminuer en intensité de 1 en 1 jusqu'à 0
         */
        caserne1.addVehicule(camion1);
        System.out.println("Ajout du camion1 dans la caserne1");
        ville1.addCaserne(caserne1);
        System.out.println("Ajout de la caserne1 dans la ville de lyon");
        camion1.setLigne(feu1.getLigne());
        camion1.setColonne(feu1.getColonne());
        System.out.println("On déplace le camion1 vers le feu1.");
        while (camion1.getColonne() == feu1.getColonne() && camion1.getLigne() == feu1.getLigne()){
            System.out.println(feu1.getIntensite());
            feu1.baisserIntensite();
            if (feu1.estEteint()){
                System.out.println("Le Feu "+feu1.toString()+ " est éteint.Le camion1 retourne à la caserne");
                camion1.setColonne(camion1.getCaserne().getColonne());
                camion1.setLigne(camion1.getCaserne().getLigne());
            }
        }
    }
}
