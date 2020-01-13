package mainPackage;

public class Camion extends Vehicule {
    public Camion(int id, int id_type, int id_caserne, int id_coordonnees,String id_feu) {
        super(id, id_type, id_caserne, id_coordonnees,id_feu);
    }
    public Camion(int id, int id_type, int id_caserne, int id_coordonnees,int id_feu) {
        super(id, id_type, id_caserne, id_coordonnees,id_feu);
    }

    @Override
    public String toString() {
        return "Camion n°" + this.getId();
    }
}
