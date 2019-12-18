package mainPackage;

public class Vehicule {
    //vitesse de déplacement
    int vitesse;
    //coordonnées gps
    Coordonnees coordonnees;
    //position x du vehicule
    int ligne;
    //position y du vehicule
    int colonne;

    public Vehicule(int vitesse,int ligne,int colonne){
        this.vitesse = vitesse;
        this.ligne = ligne;
        this.colonne = colonne;
    }

    void deplace(Coordonnees coordonnees){
        this.coordonnees = coordonnees;
    }
}
