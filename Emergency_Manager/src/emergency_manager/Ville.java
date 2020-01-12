package emergency_manager;

import java.util.ArrayList;
import java.util.List;

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
	
	public ArrayList<Caserne> getCasernes() {
		return this.casernes;
	}
	
	public void addFeu(Feu f) {
		this.feux.add(f);
	}
	
	public void setFeux(ArrayList<Feu> feux) {
		this.feux = feux;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	public ArrayList<Feu> getFeux() {
		// TODO Auto-generated method stub
		return this.feux;
	}

	public void addCasernes(List<Caserne> requestCasernes) {
		this.casernes = (ArrayList<Caserne>)requestCasernes;
	}
}
