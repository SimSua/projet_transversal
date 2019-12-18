package mainPackage;

public class Camion extends Vehicule {
    private Caserne caserne;

    public Camion(int vitesse, int ligne, int colonne,Caserne caserne) {
        super(vitesse, ligne, colonne);
        this.caserne = caserne;
    }

    @Override
    void deplace(Coordonnees coordonnees) {
        super.deplace(coordonnees);
    }
}
