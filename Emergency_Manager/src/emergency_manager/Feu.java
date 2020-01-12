package emergency_manager;

import java.util.Comparator;

public class Feu implements Comparator<Feu>{
	protected Coordonnees coordonnees;
	private int id;
	private int id_coordinate;
	protected int intensity;
	protected int ligne;
	protected int colonne;

	public Feu(int id, int intensity, int id_coordinate) {
		this.id = id;
		this.intensity = intensity;
		this.id_coordinate = id_coordinate;
	}

	public Feu(int id, int intensite, Coordonnees coordonnees) {
		this.id = id;
		this.intensity = intensite;
		this.coordonnees = coordonnees;
	}
	
	public Feu(Coordonnees coord, int i) {
		this.coordonnees = coord;
		if(i <= 9 && i >= 0) {
			this.intensity = i;
		}else {
			this.intensity = 0;
		}
		
		//System.out.println("Création d'un feu en x : "+coord.getX()+" et y : "+coord.getY()+" d'intensite "+this.intensite+" a la ligne "+this.ligne+" et la colonne "+this.colonne);
	}

	public Feu(int i) {
		// TODO Auto-generated constructor stub
		this.intensity = i;
	}

	public int getIntensite() {
		// TODO Auto-generated method stub
		return this.intensity;
	}

	@Override
	public int compare(Feu o1, Feu o2) {
		// TODO Auto-generated method stub
		return o2.getIntensite()-o1.getIntensite();
	}

	public int getIntensity() {
		return intensity;
	}

	public int getId() {
		return id;
	}
}
