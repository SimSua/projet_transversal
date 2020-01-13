package mainPackage;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Simulator extends Thread {
    private static final int POURCENTAGE_CHANCE_FEU = 20;
    public Boolean debug;
    private Feu nouveauFeu;
    public Vehicule vehiculeChoisi;
    private Caserne caserneChoisie = null;
    public List<Ville> listVilles = new ArrayList<>();
    public List<Caserne> listCasernes = new ArrayList<>();
    public List<Vehicule> listVehicules = new ArrayList<>();
    public List<Feu> listFeux = new ArrayList<>();
    public List<Feu> listFeuxNonTraites = new ArrayList<>();
    public List<TypeVehicule> listTypesVehicule = new ArrayList<>();
    public List<Coordonnees> listCoordonnees = new ArrayList<>();
    public ApiConnector apiConnector;
    public Simulator (Boolean debug,ApiConnector apiConnector) {
        this.apiConnector = apiConnector;
        this.debug = debug;
    }

    public void resetAll(){
        apiConnector.requestResetAllFeux();
        apiConnector.requestResetAllVehicules();
    }

    public void getDataFromDB(){
        listCasernes = apiConnector.requestCasernes();
        listVehicules = apiConnector.requestVehicules();
        listFeux = apiConnector.requestFeux();
        listTypesVehicule = apiConnector.requestTypesVehicule();
        listCoordonnees = apiConnector.requestCoordonnees();

        apiConnector.requestResetAllFeux();
        //set Coordonnees
        for (Coordonnees coordonnees:listCoordonnees){
            for (Vehicule vehicule:listVehicules){
                if (vehicule.getId_coordonnees() == coordonnees.getId()){
                    vehicule.setCoordonnees(coordonnees);
                }
            }
            for (Caserne caserne:listCasernes){
                if (caserne.getId_coordonnees() == coordonnees.getId()){
                    caserne.setCoordonnees(coordonnees);
                }
            }
            for (Feu feu:listFeux){
                if (feu.getId_coordinate() == coordonnees.getId()){
                    feu.setCoordonnees(coordonnees);
                }
            }
        }
        //set type vehicule
        for (TypeVehicule type:listTypesVehicule){
            for (Vehicule vehicule:listVehicules){
                if (vehicule.getId_type() == type.getId()){
                    vehicule.setType(type);
                }
            }
        }
        //add vehicule to caserne/set caserne to vehicule
        for (Vehicule vehicule:listVehicules) {
            for (Caserne caserne:listCasernes) {
                if (vehicule.getId_caserne() == caserne.getId()) {
                    caserne.addVehicule(vehicule);
                    vehicule.setCaserne(caserne);
                }
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            Boolean nouveauFeu = getChanceFeu();
            vehiculeChoisi = null;
            if (nouveauFeu) {
                System.out.println("nouveau feu");
                try {
                    creerFeu();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (debug) {
                    try {
                        attribuerFeu();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                System.out.println("Pas de nouveau feu");
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void updateDataFeuxFromDB() {
        List<Vehicule> listVehiculesFromDB = apiConnector.requestVehicules();
        for (Vehicule vehiculeDB:listVehiculesFromDB) {
            for (Vehicule vehicule:listVehicules) {
                if (vehicule.getId() == vehiculeDB.getId() && vehiculeDB.getId_feu() != -1){
                    vehicule.setFeu(apiConnector.requestFeu(vehiculeDB.getId_feu()));
                }
            }
        }
    }

    private void creerFeu() throws IOException {
        Feu feu = listFeux.get(new Random().nextInt(listFeux.size()));
        feu.setIntensity((int) (5 + (Math.random() * 4)));
        apiConnector.requestPatchFeu(feu);//sim
        listFeuxNonTraites.add(feu);
    }

    private void attribuerFeu() throws IOException {
        int indexOfFeu = -1;
        for (Feu feuAtraiter:listFeuxNonTraites) {
            getCaserneDispo(feuAtraiter);
            if (caserneChoisie != null) {
                vehiculeChoisi = getChoixVehicule();
                vehiculeChoisi.setFeu(feuAtraiter);
                apiConnector.requestPatchVehicule(vehiculeChoisi,feuAtraiter);
                indexOfFeu = listFeuxNonTraites.indexOf(feuAtraiter);
            } else {
                indexOfFeu = -1;
                System.out.println("Pas de véhicule disponible.");
            }
        }
        if (indexOfFeu != -1) {
            listFeuxNonTraites.remove(indexOfFeu);
        }
    }

    public void traiterFeux(Vehicule vehicule) throws IOException {
            if (vehicule.getFeu() != null && vehicule.estSurLeFeu()){
//                System.out.println("TEST INTENSITE DU FEU n°"+vehicule.getFeu().getId()+
//                        " INTENSITE "+vehicule.getFeu().getIntensity());
                if (vehicule.getFeu().estEteint()){
                    //Feu éteint
                    System.out.println("n°"+vehicule.getId()+" a éteint un feu");
                    vehicule.setFeu(null);
                    vehicule.setId_feu(-1);
                    apiConnector.requestPatchVehicule(vehicule, (Feu) null);
                    vehicule.allerALaCaserne();
                }else {
                    //Feu intensité baissée
                    vehicule.getFeu().baisserIntensite(vehicule.getType().getEfficacite());
                    apiConnector.requestPatchFeu(vehicule.getFeu());
                    System.out.println("intensité baissée du feu  n°"+vehicule.getFeu().getId()
                            +"  -"+vehicule.getType().getEfficacite()
                            +" | "+ vehicule.getFeu().toString());
                }
            }


    }

    private Vehicule getChoixVehicule() {
        return caserneChoisie.getVehiculesDispo().get(
                new Random().nextInt(caserneChoisie.getVehiculesDispo().size()));
    }

    private void getCaserneDispo(Feu feuAtraiter) {
        double ancDistance = 0;
        double nouvelleDistance = 0;
        boolean first = true;
        caserneChoisie = null;
        vehiculeChoisi = null;
        for (Caserne caserne : listCasernes) {
            if (first && caserne.getVehiculesDispo().size() > 0) {
                ancDistance = sqrt(pow(feuAtraiter.getCoordonnees().getColonne() - caserne.getCoordonnees().getColonne(), 2)
                        + pow(feuAtraiter.getCoordonnees().getLigne() - caserne.getCoordonnees().getLigne(), 2));
                caserneChoisie = caserne;
                first = false;
            } else if (!first && caserne.getVehiculesDispo().size() > 0) {
                nouvelleDistance = sqrt(pow(feuAtraiter.getCoordonnees().getColonne() - caserne.getCoordonnees().getColonne(), 2)
                        + pow(feuAtraiter.getCoordonnees().getLigne() - caserne.getCoordonnees().getLigne(), 2));
                if (nouvelleDistance < ancDistance) {
                    caserneChoisie = caserne;
                }
                ancDistance = nouvelleDistance;
            }
        }
    }

    private Boolean getChanceFeu() {
//        int pourcentageChance = (int) (1 + (Math.random() * 10));
        int pourcentageChance = POURCENTAGE_CHANCE_FEU / 10;//20%
        List<Boolean> tirage = new ArrayList<>();
        for(int i=0;i<10;i++){
            if(i<pourcentageChance){
                tirage.add(true);
            }else {
                tirage.add(false);
            }
        }
        return tirage.get(new Random().nextInt(tirage.size()));
    }
}
