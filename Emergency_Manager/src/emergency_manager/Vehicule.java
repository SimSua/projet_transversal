package emergency_manager;

public abstract class Vehicule {
	protected static int id = 0;
	protected int vitesse;
	protected Caserne c;
	
	public Vehicule(int vitesse, Caserne c) {
		Vehicule.id++;
		this.vitesse = vitesse;
		this.c = c;
		System.out.println("Creation de "+this.getClass()+ " avec id " + Vehicule.id);
	}
	
	public void envoyer(Feu e) {
		System.out.println("Envoi du camion "+Vehicule.id+" sur le feu "+e);
		//Envoi via l'API que le vehicule gère le feu
	}
}
