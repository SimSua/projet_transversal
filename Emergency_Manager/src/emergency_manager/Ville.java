package emergency_manager;

import java.util.ArrayList;
import java.util.List;

public class Ville {
	private String nom_ville;
	private List<Caserne> casernes = new ArrayList<Caserne>();
	private Coordonnees coordonnees;

	public Ville(String nom_ville, Coordonnees coordonnees) {
		this.nom_ville = nom_ville;
		this.coordonnees = coordonnees;
	}

	public void addCaserne(Caserne caserne) {
		this.casernes.add(caserne);
	}

	@Override
	public String toString() {
		return "Ville{" +
				"nom_ville='" + nom_ville + '\'' +
				", casernes=" + casernes +
				", coordonnees=" + coordonnees.toString() +
				'}';
	}

	public void addCasernes(List<Caserne> requestCasernes) {
		this.casernes = (ArrayList<Caserne>)requestCasernes;
	}
}
