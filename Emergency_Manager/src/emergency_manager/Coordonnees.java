package emergency_manager;

public class Coordonnees {
	protected int x;
	protected int y;
	protected int ligne;
	protected int colonne;
	
	public Coordonnees(int x, int y, int l, int c) {
		this.x = x;
		this.y = y;
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
	}
	
	public void getXYfromBDD() {
		
	}
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public int getLigne() {
		return this.ligne;
	}
	public int getColonne() {
		return this.colonne;
	}
}
