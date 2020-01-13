package mainPackage;

public abstract class Vehicule {
    private int id;
    private int id_type;
    private int id_caserne;
    private int id_coordonnees;
    private int id_feu;
    private TypeVehicule type;
    //coordonnées gps
    private Coordonnees coordonnees;
    //feu attribué
    private Feu feu;
    private Caserne caserne;

    public Vehicule(int id,int id_type,int id_caserne,int id_coordonnees, String id_feu){
        this.id = id;
        this.id_type = id_type;
        this.id_caserne = id_caserne;
        this.id_coordonnees = id_coordonnees;
        if (id_feu != "null") {
            this.id_feu = Integer.parseInt(id_feu);
        }else{
            this.id_feu = -1;
        }
    }
    public Vehicule(int id,int id_type,int id_caserne,int id_coordonnees, int id_feu){
        this.id = id;
        this.id_type = id_type;
        this.id_caserne = id_caserne;
        this.id_coordonnees = id_coordonnees;
        this.id_feu = id_feu;
    }
    void deplacer(Coordonnees coordonnees){
        this.coordonnees = coordonnees;
    }

    public TypeVehicule getType() {
        return type;
    }

    public void setType(TypeVehicule type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getId_type() {
        return id_type;
    }

    public int getId_caserne() {
        return id_caserne;
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

    public Feu getFeu() {
        return feu;
    }

    public void setFeu(Feu feu) {
        this.feu = feu;
    }

    public Caserne getCaserne() {
        return caserne;
    }

    public void setCaserne(Caserne caserne) {
        this.caserne = caserne;
    }

    public void allerAuFeu(){
        System.out.println("n°"+this.id+" va au feu n°"+feu.getId());
        this.setCoordonnees(this.feu.getCoordonnees());
    }

    public void allerALaCaserne(){
        this.setCoordonnees(this.caserne.getCoordonnees());
        System.out.println("Camion n°"+id+" retour à la caserne n°"+caserne.getId());
    }

    public boolean estSurLeFeu() {
        return this.coordonnees.getColonne() == this.feu.getCoordonnees().getColonne()
                && this.coordonnees.getLigne() == this.feu.getCoordonnees().getLigne();
    }

    public int getId_feu() {
        return id_feu;
    }

    public void setId_feu(int i) {
        this.id_feu = i;
    }
}
