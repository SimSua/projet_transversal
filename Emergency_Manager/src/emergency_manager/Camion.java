package emergency_manager;

public class Camion extends Vehicule {

	public Camion(Caserne c, Coordonnees coord) {
		// TODO Auto-generated constructor stub
		super(1, c, coord);
	}

    public Camion(int id, int id_type, int id_caserne, int id_coordonnees) {
        super(id, id_type, id_caserne, id_coordonnees);
    }
}
