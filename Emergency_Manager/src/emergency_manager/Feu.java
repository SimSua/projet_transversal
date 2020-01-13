package emergency_manager;

import java.util.Comparator;

public class Feu {
	private int id;
	private int id_coordinate;
	private int intensity;
	private Coordonnees coordonnees;

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

	public void baisserIntensite(int efficaciteVehicule) {
		this.intensity = this.intensity - efficaciteVehicule;
	}

	public void augmenterIntensite() { this.intensity++; }

	public boolean estEteint() {
		return this.intensity <= 0;
	}

	public int getId() {
		return id;
	}

	public int getId_coordinate() {
		return id_coordinate;
	}

	public Coordonnees getCoordonnees() {
		return coordonnees;
	}

	public void setCoordonnees(Coordonnees coordonnees) {
		this.coordonnees = coordonnees;
	}

	public int getIntensity() {
		return intensity;
	}

	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}

	@Override
	public String toString() {
		return "Feu{" +
				"id="+id+
				"intensite=" + intensity +
				'}';
	}
}
