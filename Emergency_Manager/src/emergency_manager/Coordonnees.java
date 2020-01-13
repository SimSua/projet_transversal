package emergency_manager;

public class Coordonnees {
	private int id;
	private int ligne;
	private int colonne;
	private double x_coord;
	private double y_coord;

	public Coordonnees(int id, int ligne, int colonne, double x_coord, double y_coord) {
		this.id = id;
		this.ligne = ligne;
		this.colonne = colonne;
		this.x_coord = x_coord;
		this.y_coord = y_coord;
	}

	public int getId() {
		return id;
	}

	public int getLigne() {
		return ligne;
	}

	public int getColonne() {
		return colonne;
	}

	public double getX_coord() {
		return x_coord;
	}

	public double getY_coord() {
		return y_coord;
	}

	@Override
	public String toString() {
		return "Coordonnees("+ligne+","+colonne+")";
	}
}

