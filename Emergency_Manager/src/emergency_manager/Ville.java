package emergency_manager;

import java.util.ArrayList;

public class Ville {
	protected String name;
	protected ArrayList<Caserne> casernes;
	protected ArrayList<Feu> feux;
	
	public Ville(String name) {
		this.name = name;
		this.casernes = new ArrayList<Caserne>();
		this.feux = new ArrayList<Feu>();
	}
	
	public void addCaserne(Caserne c) {
		this.casernes.add(c);
	}
	
	public void setCasernes(ArrayList<Caserne> casernes) {
		this.casernes = casernes;
	}
	
	public void addFeu(Feu f) {
		this.feux.add(f);
	}
	
	public void setFeux(ArrayList<Feu> feux) {
		this.feux = feux;
	}
}
