package mainPackage;

public class Vehicule {
    //vitesse de déplacement
    private int vitesse;
    //coordonnées gps
    private Coordonnees coordonnees;
    //position x du vehicule
    private int ligne;
    //position y du vehicule
    private int colonne;

    public Vehicule(int vitesse,int ligne,int colonne,Coordonnees coordonnees){
        this.vitesse = vitesse;
        this.ligne = ligne;
        this.colonne = colonne;
        this.coordonnees = coordonnees;
    }

    void deplacer(Coordonnees coordonnees){
        this.coordonnees = coordonnees;
    }

    public int getVitesse() {
        return vitesse;
    }

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }
    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    @Override
    public String toString() {
        return "Vehicule{" +
                "vitesse=" + vitesse +
                ", coordonnees=" + coordonnees.toString() +
                ", ligne=" + ligne +
                ", colonne=" + colonne +
                '}';
    }
}
