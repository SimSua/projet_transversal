package emergency_manager;

import java.util.Comparator;

public class Feu implements Comparator<Feu>{
	protected Coordonnees coord;
	protected int intensite;
	protected int ligne;
	protected int colonne;
	
	public Feu(Coordonnees coord, int i) {
		this.coord = coord;
		if(i <= 9 && i >= 0) {
			this.intensite = i;
		}else {
			this.intensite = 0;
		}
		
		System.out.println("Création d'un feu en x : "+coord.getX()+" et y : "+coord.getY()+" d'intensite "+this.intensite+" a la ligne "+this.ligne+" et la colonne "+this.colonne);
	}

	public Feu(int i) {
		// TODO Auto-generated constructor stub
		this.intensite = i;
	}

	public int getIntensite() {
		// TODO Auto-generated method stub
		return this.intensite;
	}

	@Override
	public int compare(Feu o1, Feu o2) {
		// TODO Auto-generated method stub
		return o2.getIntensite()-o1.getIntensite();
	}
}
