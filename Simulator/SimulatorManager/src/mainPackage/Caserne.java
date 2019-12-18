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
}
