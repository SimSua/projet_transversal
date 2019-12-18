package mainPackage;

public class Camion extends Vehicule {
    private Caserne caserne;

    public Camion(int vitesse, int ligne, int colonne, Coordonnees coordonnees,Caserne caserne) {
        super(vitesse, ligne, colonne,coordonnees);
        this.caserne = caserne;
    }

    @Override
    void deplace(Coordonnees coordonnees) {
        super.deplace(coordonnees);
    }

    public Caserne getCaserne() {
        return caserne;
    }

    @Override
    public String toString() {
        return "Camion{" +
                "caserne=" + caserne.toString() +
                ", vitesse=" + this.getVitesse() +
                ", coordonnees=" + this.getCoordonnees().toString() +
                ", ligne=" + this.getLigne() +
                ", colonne=" + this.getColonne() +
                '}';
    }
}
