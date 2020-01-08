package mainPackage;

public class Camion extends Vehicule {


    public Camion(int id,int vitesse, int ligne, int colonne, Coordonnees coordonnees,Caserne caserne) {
        super(id,vitesse, ligne, colonne,coordonnees,caserne);
    }

    @Override
    public String toString() {
        return "Camion n°" + this.getId() + " | Feu : "+ this.getFeu().toString() + " | ligne : " + this.getLigne() + " | colonne : " + this.getColonne();
    }
}
