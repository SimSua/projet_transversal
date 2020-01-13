package mainPackage;

import java.util.ArrayList;
import java.util.List;

public class Caserne {
    private int id;
    private int id_coordonnees;
    private Coordonnees coordonnees;
    private int capacite;
    private List<Vehicule> vehicules = new ArrayList<Vehicule>();

    public Caserne(int id, int capacite,int id_coordonnees) {
        this.id = id;
        this.capacite = capacite;
        this.id_coordonnees = id_coordonnees;
    }

    public void addVehicule(Vehicule vehicule){
        this.vehicules.add(vehicule);
    }

    public List<Vehicule> getVehiculesDispo() {
        List<Vehicule> vehiculesDispo = new ArrayList<>();
        for (Vehicule vehicule:this.getVehicules()) {
            if (vehicule.getFeu() == null) {
                vehiculesDispo.add(vehicule);
            }
        }
        return vehiculesDispo;
    }

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(Coordonnees coordonnees) {
        this.coordonnees = coordonnees;
    }

    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    public int getId() {
        return id;
    }

    public int getId_coordonnees() {
        return id_coordonnees;
    }

    public int getCapacite() {
        return capacite;
    }

    @Override
    public String toString() {
        ArrayList idVehicules = new ArrayList<>();
        for (Vehicule vehicule:vehicules) {
            idVehicules.add(vehicule.getId());
        }
        return "Caserne{" +
                "id="+id+
                ", vehicules=" + idVehicules +
                '}';
    }
}
