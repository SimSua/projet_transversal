package emergency_manager;

public class TypeVehicule {
    private int id;
    private String nom;
    private int vitesse;
    private int efficacite;

    public TypeVehicule(int id, String nom, int vitesse, int efficacite) {
        this.id = id;
        this.nom = nom;
        this.vitesse = vitesse;
        this.efficacite = efficacite;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getVitesse() {
        return vitesse;
    }

    public int getEfficacite() {
        return efficacite;
    }
}

