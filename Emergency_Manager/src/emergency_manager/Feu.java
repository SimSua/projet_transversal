package emergency_manager;

public class Feu {
	protected Coordonnees coord;
	protected int intensite;
	protected int ligne;
	protected int colonne;
	
	public Feu(Coordonnees coord, int i, int l, int c) {
		this.coord = coord;
		if(i <= 9 && i >= 0) {
			this.intensite = i;
		}else {
			this.intensite = 0;
		}
		
		if(l >= 0 && l < 6) {
			this.ligne = l;
		}else {
			this.ligne = 0;
		}
		
		if(c <= 9 && c >= 0) {
			this.colonne = c;
		}else {
			this.colonne = 0;
		}
		System.out.println("Création d'un feu en x : "+coord.getX()+" et y : "+coord.getY()+" d'intensite "+this.intensite+" a la ligne "+this.ligne+" et la colonne "+this.colonne);
	}
}
