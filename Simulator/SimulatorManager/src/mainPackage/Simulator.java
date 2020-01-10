package mainPackage;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Simulator extends Thread {
    private Boolean debug;
    private Feu nouveauFeu;
    public Vehicule vehiculeChoisi;
    private Caserne caserneChoisie = null;
    private List<Ville> listVilles = new ArrayList<>();
    private List<Caserne> listCasernes = new ArrayList<>();
    private List<Vehicule> listVehicules = new ArrayList<>();
    private List<Feu> listFeux = new ArrayList<>();
    private List<Feu> listFeuxNonTraites = new ArrayList<>();
    private List<TypeVehicule> listTypesVehicule = new ArrayList<>();
    private List<Coordonnees> listCoordonnees = new ArrayList<>();
    public ApiConnector apiConnector = new ApiConnector();
    public Simulator (Boolean debug) {
        this.debug = debug;
    }

    public void initData(){
        listCasernes = apiConnector.requestCasernes();
        listVehicules = apiConnector.requestVehicules();
        listFeux = apiConnector.requestFeux();
        listTypesVehicule = apiConnector.requestTypesVehicule();
        listCoordonnees = apiConnector.requestCoordonnees();
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
        while (true){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Boolean nouveauFeu = getChanceFeu();
            vehiculeChoisi = null;
            if (nouveauFeu) {
                System.out.println("nouveau feu");
                try {
                    creerFeu();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                try {
                    attribuerFeu();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("Pas de nouveau feu");
            }
        }
    }

    private void creerFeu() throws JsonProcessingException {
        if (this.debug) {
            int intensite = (int) (1 + (Math.random() * 10));
            int ligneFeu = (int) (1 + (Math.random() * 30));
            int colonneFeu = (int) (1 + (Math.random() * 30));
            Feu feu = new Feu(1, intensite, new Coordonnees(7, 5, 5, 7, 8));
            listFeuxNonTraites.add(feu);
        }else {
            Feu feu = listFeux.get(new Random().nextInt(listFeux.size()));
            feu.setIntensity((int) (5 + (Math.random() * 10)));
            apiConnector.requestPatchFeu(feu);
            listFeuxNonTraites.add(feu);
        }
    }

    private void attribuerFeu() throws JsonProcessingException {
        int indexOfFeu = -1;
        for (Feu feuAtraiter:listFeuxNonTraites) {
            getCaserneDispo(feuAtraiter);
            if (caserneChoisie != null) {
                vehiculeChoisi = getChoixVehicule();
                vehiculeChoisi.setFeu(feuAtraiter);
                if (!debug){
                    apiConnector.requestPatchVehicule(vehiculeChoisi,feuAtraiter);
                }
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

    public void traiterFeux() {
        //System.out.println("traiter feux");
        for (Vehicule vehicule:listVehicules){
//            if(vehicule.getFeu() != null) {
//                System.out.println(vehicule.estSurLeFeu());
//            }
            if (vehicule.getFeu() != null && vehicule.estSurLeFeu()){
                if (vehicule.getFeu().estEteint()){
                    System.out.println("n°"+vehicule.getId()+" a éteint un feu");
                    vehicule.setFeu(null);
                    listFeuxNonTraites.remove(vehicule.getFeu());
//                    vehicule.allerALaCaserne();
                }else {
                    vehicule.getFeu().baisserIntensite();
                    System.out.println("intensité baissé du feu " + vehicule.getFeu().toString());
                }
            }else if(vehicule.getFeu() != null){
                System.out.println("intensité augmenté du feu "+ vehicule.getFeu().toString());
                vehicule.getFeu().augmenterIntensite();
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
        int pourcentageChance = 2;//20%
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
