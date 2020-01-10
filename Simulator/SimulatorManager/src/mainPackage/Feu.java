package mainPackage;

public class Feu {
    private int id;
    private int id_coordonnees;
    private int intensite;
    private Coordonnees coordonnees;

    public Feu(int id, int intensite,int id_coordonnees) {
        this.id = id;
        this.intensite = intensite;
        this.id_coordonnees = id_coordonnees;
    }

    public Feu(int id, int intensite, Coordonnees coordonnees) {
        this.id = id;
        this.intensite = intensite;
        this.coordonnees = coordonnees;
    }

    public void baisserIntensite() {
        this.intensite--;
    }

    public void augmenterIntensite() { this.intensite++; }

    public boolean estEteint() {
        return this.intensite <= 0;
    }

    public int getId() {
        return id;
    }

    public int getId_coordonnees() {
        return id_coordonnees;
    }

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(Coordonnees coordonnees) {
        this.coordonnees = coordonnees;
    }

    public int getIntensite() {
        return intensite;
    }

    @Override
    public String toString() {
        return "Feu{" +
                "id="+id+
                "intensite=" + intensite +
                '}';
    }
}
