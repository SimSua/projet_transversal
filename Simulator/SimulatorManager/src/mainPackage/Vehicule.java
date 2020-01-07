package mainPackage;

public class Vehicule {
    private int id;
    //vitesse de déplacement
    private int vitesse;
    //coordonnées gps
    private Coordonnees coordonnees;
    //position x du vehicule
    private int ligne;
    //position y du vehicule
    private int colonne;
    //feu attribué
    private Feu feu;

    public Vehicule(int id,int vitesse,int ligne,int colonne,Coordonnees coordonnees){
        this.id = id;
        this.vitesse = vitesse;
        this.ligne = ligne;
        this.colonne = colonne;
        this.coordonnees = coordonnees;
    }

    void deplacer(Coordonnees coordonnees){
        this.coordonnees = coordonnees;
    }

    public int getId() { return id; }

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

    public Feu getFeu() { return this.feu; }

    public void setFeu(Feu feu) {
        this.feu = feu;
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
}
