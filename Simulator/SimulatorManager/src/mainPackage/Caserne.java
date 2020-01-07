package mainPackage;

import java.util.ArrayList;
import java.util.List;

public class Caserne {
    private int ligne;
    private int colonne;
    private Coordonnees coordonnees;
    private List<Vehicule> vehicules = new ArrayList<Vehicule>();

    public Caserne(int ligne, int colonne, Coordonnees coordonnees) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.coordonnees = coordonnees;
    }

    public void addVehicule(Vehicule vehicule){
        this.vehicules.add(vehicule);
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
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

    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    @Override
    public String toString() {
        ArrayList idVehicules = new ArrayList<>();
        for (Vehicule vehicule:vehicules) {
            idVehicules.add(vehicule.getId());
        }
        return "Caserne{" +
                "ligne=" + ligne +
                ", colonne=" + colonne +
                ", coordonnees=" + coordonnees.toString() +
                ", vehicules=" + idVehicules +
                '}';
    }
}
