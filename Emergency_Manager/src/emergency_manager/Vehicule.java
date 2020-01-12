package emergency_manager;

public abstract class Vehicule {
	protected static int compteur = 0;
	private int id;
	private int id_type;
	private int id_caserne;
	protected int id_coordonnees;
	protected int vitesse;
	protected Caserne c;
	protected boolean occupe = false;
	protected Coordonnees coord;

	public Vehicule(int id,int id_type,int id_caserne,int id_coordonnees){
		this.id = id;
		this.id_type = id_type;
		this.id_caserne = id_caserne;
		this.id_coordonnees = id_coordonnees;
	}
	
	public Vehicule(int vitesse, Caserne c, Coordonnees coord) {
		this.id = Vehicule.compteur++;
		this.vitesse = vitesse;
		this.c = c;
		this.coord = c.getCoordonnees();
		System.out.println("Creation de "+this.getClass()+ " avec id " + this.id+" pour la caserne "+c);
	}
	
	public void envoyer(Feu e) {
		System.out.println("Envoi du camion "+this.id+" sur le feu "+e);
		this.occupe = true;
		//Envoi via l'API que le vehicule gère le feu
	}
	
	public boolean estRevenu() {
		if(this.coord == c.getCoordonnees()) return true;
		return false;	
	}

	public boolean estOccupe() {
		// TODO Auto-generated method stub
		return occupe;
	}

	public int getId(){ return id;}
}
