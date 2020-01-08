package mainPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Simulator extends Thread {
    private Feu nouveauFeu;
    public Vehicule vehiculeChoisi;
    private Caserne caserneChoisie = null;
    public List<Feu> listFeuxNonTraites = new ArrayList<>();
    public List<Caserne> listCasernes;
    public List<Vehicule> listVehicules;
    public List<Ville> listVilles;

    public Simulator (List<Caserne> listCasernes, List<Vehicule> listVehicules,List<Ville> listVilles) {
        this.listCasernes = listCasernes;
        this.listVehicules = listVehicules;
        this.listVilles = listVilles;
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
            if (nouveauFeu) {
                System.out.println("nouveau feu");
                this.creerFeu();
                this.attribuerFeu();
            }else{
                System.out.println("Pas de nouveau feu");
            }
        }
    }

    private void creerFeu() {
        int intensite = (int) (1 + (Math.random() * 10));
        int ligneFeu = (int) (1 + (Math.random() * 30));
        int colonneFeu = (int) (1 + (Math.random() * 30));
        Feu feu = new Feu(intensite,ligneFeu,colonneFeu,new Coordonnees(7,5));
        this.listFeuxNonTraites.add(feu);
    }

    private void attribuerFeu() {
        int indexOfFeu = -1;
        for (Feu feuAtraiter:this.listFeuxNonTraites) {
            //System.out.println(this.listFeuxNonTraites);

            this.getCaserneDispo(feuAtraiter);
            if (caserneChoisie != null) {
                System.out.println(caserneChoisie.getVehiculesDispo().size());
                vehiculeChoisi = getChoixVehicule();
                vehiculeChoisi.setFeu(feuAtraiter);
                System.out.println(caserneChoisie.getVehicules().get(caserneChoisie.getVehicules().indexOf(vehiculeChoisi)));
                indexOfFeu = this.listFeuxNonTraites.indexOf(feuAtraiter);
            } else {
                indexOfFeu = -1;
                System.out.println("Pas de véhicule disponible.");
            }
            //this.checkVehiculeSurFeu(feuAtraiter);
        }
        if (indexOfFeu != -1) {
            this.listFeuxNonTraites.remove(indexOfFeu);
        }
    }

    public void traiterFeux() {
        for (Vehicule vehicule:this.listVehicules){
//            if(vehicule.getFeu() != null) {
//                System.out.println(vehicule.estSurLeFeu());
//            }
            if (vehicule.getFeu() != null && vehicule.estSurLeFeu()){
                if (vehicule.getFeu().estEteint()){
                    vehicule.setFeu(null);
                    this.listFeuxNonTraites.remove(vehicule.getFeu());
                    vehicule.allerALaCaserne();
                }else {
                    vehicule.getFeu().baisserIntensite();
                    System.out.println("intensité baissé du feu " + vehicule.getFeu().toString());
                }
                break;
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
                ancDistance = sqrt(pow(feuAtraiter.getColonne() - caserne.getColonne(), 2)
                        + pow(feuAtraiter.getLigne() - caserne.getLigne(), 2));
                caserneChoisie = caserne;
                first = false;
            } else if (!first && caserne.getVehiculesDispo().size() > 0) {
                nouvelleDistance = sqrt(pow(feuAtraiter.getColonne() - caserne.getColonne(), 2)
                        + pow(feuAtraiter.getLigne() - caserne.getLigne(), 2));
                if (nouvelleDistance < ancDistance) {
                    caserneChoisie = caserne;
                }
                ancDistance = nouvelleDistance;
            }
        }
    }

    private Boolean getChanceFeu() {
//        int pourcentageChance = (int) (1 + (Math.random() * 10));
        int pourcentageChance = 2;
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
