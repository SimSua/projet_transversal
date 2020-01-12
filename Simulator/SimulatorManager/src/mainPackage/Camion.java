package mainPackage;

public class Camion extends Vehicule {
    public Camion(int id, int id_type, int id_caserne, int id_coordonnees) {
        super(id, id_type, id_caserne, id_coordonnees);
    }

    @Override
    public String toString() {
        return "Camion n°" + this.getId();
    }
}
