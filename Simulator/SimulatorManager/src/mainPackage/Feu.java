package mainPackage;

public class Feu {

    private int intensite;
    private int ligne;
    private int colonne;
    private Coordonnees coordonnees;

    public Feu(int intensite, int ligne, int colonne, Coordonnees coordonnees) {
        this.intensite = intensite;
        this.ligne = ligne;
        this.colonne = colonne;
        this.coordonnees = coordonnees;
    }

    public void baisserIntensite() {
        this.intensite--;
    }

    public void augmenterIntensite() { this.intensite++; }

    public boolean estEteint() {
        return this.intensite <= 0;
    }

    public int getIntensite() {
        return intensite;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    @Override
    public String toString() {
        return "Feu{" +
                "intensite=" + intensite +
                ", ligne=" + ligne +
                ", colonne=" + colonne +
                ", coordonnees=" + coordonnees.toString() +
                '}';
    }
}
