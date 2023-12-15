package com.plaquesolaire.entity.information;

import annotation.*;

import com.plaquesolaire.entity.MonComparator;
import com.plaquesolaire.entity.Utils;
import com.plaquesolaire.entity.infrastructure.Secteur;
import com.plaquesolaire.entity.objet.PlaqueSolaire;
import dao.BddObject;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Table(name = "coupure")
public class Coupure extends BddObject<Coupure>
{
    public java.sql.Time getHeureCoupure() {
        return heureCoupure;
    }

    public void setHeureCoupure(java.sql.Time heureCoupure) {
        this.heureCoupure = heureCoupure;
    }

    public double[] getInitialPower() {
        return initialPower;
    }

    public void setInitialPower(double[] initialPower) {
        this.initialPower = initialPower;
    }

    public Time getHeureFin() {
        return heureFin;
    }

    public double getPuissanceDepart() {
        return puissanceDepart;
    }

    public void setPuissanceDepart(double puissanceDepart) {
        this.puissanceDepart = puissanceDepart;
    }

    public double getTotalBesoin() {
        return totalBesoin;
    }

    public void setTotalBesoin(double totalBesoin) {
        this.totalBesoin = totalBesoin;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public double getNombreEleveCoupure() {
        return nombreEleveCoupure;
    }

    public void setNombreEleveCoupure(double nombreEleveCoupure) {
        this.nombreEleveCoupure = nombreEleveCoupure;
    }

    @PrimaryKey(prefix = "CP", sequence = "seq_coupure", length = 7)
    @Column(name = "id_coupure")
    public String idCoupure;

    public double getNombreEleveCoupureMidi() {
        return nombreEleveCoupureMidi;
    }

    public void setNombreEleveCoupureMidi(double nombreEleveCoupureMidi) {
        this.nombreEleveCoupureMidi = nombreEleveCoupureMidi;
    }

    @Column(name = "date_coupure")
    public java.sql.Timestamp dateCoupure;    

    @ForeignKey
    @Column(name = "id_secteur")
    public String idSecteur;    

    @Column(name = "heure_coupure")
    public java.sql.Time heureCoupure;

    double[] initialPower;

    Time heureFin;
    private double puissanceDepart = 60;
    private double totalBesoin = 1500;
    public AssociationSecteurSource associationSecteurSource;
    Secteur secteur;
    double nombreEleveCoupure;
    double nombreEleveCoupureMidi;

    public void setHeureFin(String heureFin) {
        this.heureFin = Time.valueOf(heureFin);
    }

    public Coupure(){}

    public Coupure(String dateCoupure, String idSecteur){
        this.setDateCoupure(dateCoupure);
        this.setIdSecteur(idSecteur);
    }

    public Affichage getPredictionStatus(Connection c) throws Exception {
        Affichage affichage = new Affichage();
        String conditionCoupure = " date_coupure < '"+this.getDateCoupure()+"' and id_secteur = '"+this.getIdSecteur()+"' order by date_coupure";
        List<Coupure> coupureList = this.findWhere(c, conditionCoupure); 
        String condition = " id_secteur = '"+this.idSecteur+"'";
        this.associationSecteurSource = (AssociationSecteurSource) new AssociationSecteurSource().findWhere(c, condition).get(0);
        if(coupureList.size() == 0) throw new Exception("Pas de donnée");
        
        for (Object coupure: coupureList) {
            ((Coupure) coupure).setAssociationSecteurSource(this.associationSecteurSource);
            ((Coupure) coupure).generateStatesOfLight(c);
        }
      //  System.out.println("Isa");
      //  System.out.println(count);
        double moyenneCoupure = Utils.somme(coupureList, "getTotalBesoin") / coupureList.size();
        this.totalBesoin = moyenneCoupure;
        List<Reception> receptions = new Reception().getAllLuminosiy(Date.valueOf(LocalDate.of(this.dateCoupure.toLocalDateTime().getYear(),
        this.dateCoupure.toLocalDateTime().getMonth(), this.getDateCoupure().toLocalDateTime().getDayOfMonth())) , c);
        System.out.println(receptions.size());
        this.secteur = new Secteur();
        this.secteur = secteur.findById(c, this.idSecteur);
        this.initialPower = this.associationSecteurSource.getTotalPower(c);
        affichage.setCoupure(this);

     //   this.totalBesoin = obtenirMoyenneParJour(coupureList);
        double[] nombreEleve = obtenirMoyenneNombreEtudiantParJour(coupureList);
        this.nombreEleveCoupure = nombreEleve[0];
        this.nombreEleveCoupureMidi = nombreEleve[1];
       // this.totalBesoin = 73.16;
        System.out.println("Tanjaka ampiasaina "+this.totalBesoin+" Date "+this.dateCoupure);
        List<Status> stats = this.genererCoupure(receptions, c);
        Collections.sort(stats, new MonComparator());
        affichage.setStatus(stats);
        return affichage;
    }

    public double obtenirMoyenneParJour(List<Coupure> coupures){
        int count = 0;
        double somme = 0;
        int dayOfWeek = Utils.getDayOfWeek(this.dateCoupure.getTime());
        for (Coupure coupure : coupures) {
            int dayOfWeek2 = Utils.getDayOfWeek(coupure.getDateCoupure().getTime());
            if(dayOfWeek == dayOfWeek2){
                somme += coupure.puissanceDepart;
                count++;
            }
        }
        return somme / count;
    }

    public double[] obtenirMoyenneNombreEtudiantParJour(List<Coupure> coupures){
        int count = 0;
        double somme = 0;
        double sommeMidi = 0;
        int dayOfWeek = Utils.getDayOfWeek(this.dateCoupure.getTime());
        for (Coupure coupure : coupures) {
            int dayOfWeek2 = Utils.getDayOfWeek(coupure.getDateCoupure().getTime());
            if(dayOfWeek == dayOfWeek2){
                somme += coupure.nombreEleveCoupure;
                sommeMidi += coupure.nombreEleveCoupureMidi;
                count++;
            }
        }
        double[] retour = new double[2];
        retour[0] = somme/count;
        retour[1] = sommeMidi/count;
        return retour;
    }

    public void generateStatesOfLight(Connection c) throws Exception {
        Date localDate = Date.valueOf(this.dateCoupure.toLocalDateTime().toLocalDate());
        List<Reception> receptions = new Reception().getAllLuminosiy(localDate, c);
      //  System.out.println("Rece "+receptions.size());
        this.secteur = new Secteur();
        this.secteur = secteur.findById(c, this.idSecteur);
        this.initialPower = this.associationSecteurSource.getTotalPower(c); 
        Double[] nombreEtudiant = this.getNumberOfStudent(receptions, c);
        this.nombreEleveCoupure = nombreEtudiant[0];
        this.nombreEleveCoupureMidi = nombreEtudiant[1];
        this.dochotomie(receptions, c, nombreEtudiant);
    }

    public Double[] getNumberOfStudent(List<Reception> receptions, Connection c) throws Exception{
        Double[] retour = new Double[2];
        for(Reception r : receptions){
            if(retour[0] != null & retour[1] != null) return retour;
            if(r.getHeureReception().before(Time.valueOf(LocalTime.of(12, 0, 1)))){
                retour[0] = this.secteur.getTotalCharge(r.getDateReception(), r.getHeureReception(), c);
            }
            else{
                retour[1] = this.secteur.getTotalCharge(r.getDateReception(), r.getHeureReception(), c);
            }
        }
        return retour;
    }

    public void dochotomie(List<Reception> receptions, Connection c, Double[] nombreEtudiant) throws Exception {
        Time coupure = Time.valueOf(LocalTime.of(8, 0));
        this.heureFin = coupure;
        double millieu;
        while((this.totalBesoin - this.puissanceDepart) > 0.00000000001){
            millieu = (this.totalBesoin + this.puissanceDepart) / 2.0;
            coupure = varierCoupure(receptions, nombreEtudiant, millieu);
       //     System.out.println(" 1 Millieu "+millieu+" date prevu "+this.heureCoupure+" date prevision "+coupure);
            if(this.heureCoupure.after(coupure)){
                this.totalBesoin = millieu;
          //     System.out.println("A+");
            }
            else if(this.heureCoupure.before(coupure)){
                this.puissanceDepart = millieu;
        //        System.out.println("B-");
            }
            if(Math.abs(this.heureCoupure.getTime() - coupure.getTime()) / 1000 < 0.0001) break; 
      //      System.out.println(" 2 A="+this.puissanceDepart+" B="+this.totalBesoin);
        }
        this.totalBesoin = (this.totalBesoin + this.puissanceDepart) /2;
      System.out.println("F Tanjaka = "+this.totalBesoin+"|"+this.puissanceDepart+ " prevu = "+this.heureCoupure+" tsatoka = "+coupure+" date "+this.dateCoupure);
    }


    public Time varierCoupure(List<Reception> receptions, Double[] nombreEtudiant, double millieu) throws Exception {
        double puissanceBatterieADeboursee = initialPower[0] / 2;
        double puissanceRequis, nombreEleve, minuteDeCoupure, puissanceTotale, puissancePlaque, secondeDeCoupure;
        Time heureDeCoupure;
        for (Reception r: receptions) {
            puissancePlaque =( initialPower[1] * r.getLuminosite() )/10;
            puissanceTotale = puissancePlaque + puissanceBatterieADeboursee;
            if(r.getHeureReception().before(Time.valueOf(LocalTime.of(12, 01)))){
                nombreEleve = nombreEtudiant[0];
            }
            else{
                nombreEleve = nombreEtudiant[1];
            }
            puissanceRequis = millieu * nombreEleve;
            if(puissanceTotale < puissanceRequis){ // puissanceTotale < puissanceRequis
                minuteDeCoupure = (puissanceTotale * 60) / puissanceRequis; // eto ny minute de coupure
            //    System.out.println(minuteDeCoupure);
                secondeDeCoupure = (puissanceTotale * 3600) / puissanceRequis; // eto ny seconde de
                int min = (int) secondeDeCoupure / 60;
                int partieDecimale = (int) (secondeDeCoupure - (min * 60)); 
            //    partieDecimale = 60 * partieDecimale / 1;
             //   if(minuteDeCoupure < 0) minuteDeCoupure = 0;
              //  this.nombreEleveCoupure = nombreEleve;
                heureDeCoupure = Time.valueOf(LocalTime.of(r.getHeureReception().toLocalTime().getHour(), (int)minuteDeCoupure, 0));
                return heureDeCoupure;
            }
            
            else if(puissanceTotale > puissanceRequis){
               /* minuteDeCoupure = (puissanceTotale * 60) / puissanceRequis; // eto ny minute de coupure
                System.out.println("fopla"+minuteDeCoupure);
                secondeDeCoupure = (puissanceTotale * 3600) / puissanceRequis; // eto ny seconde de
                if(minuteDeCoupure < 120){
                    minuteDeCoupure = 120 - 60;
                    System.out.println("Zany hoe"+minuteDeCoupure);
                    if(minuteDeCoupure == 60) minuteDeCoupure = 0;
                   heureDeCoupure = Time.valueOf(LocalTime.of(r.getHeureReception().toLocalTime().getHour() + 1, (int) minuteDeCoupure));
                    return heureDeCoupure;
                }*/
                //if(puissanceRequis > puissancePlaque){
                    puissanceBatterieADeboursee = puissanceBatterieADeboursee - (puissanceRequis - puissancePlaque);
               // }
                if(puissanceBatterieADeboursee > initialPower[0] / 2){ // angalana le batterie
                    puissanceBatterieADeboursee = initialPower[0] / 2;
                }
                if(puissanceBatterieADeboursee < 0) puissanceBatterieADeboursee = 0.0;
            }
        }
        return Time.valueOf(LocalTime.of(18, 0));
    }

    public List<Status> genererCoupure(List<Reception> receptions, Connection c) throws Exception {
        List<Status> statusList = new ArrayList<>();
        double puissanceBatterieADeboursee = initialPower[0] / 2;
        double puissanceRequis, nombreEleve, minuteDeCoupure, puissanceTotale, puissancePlaque, minuteBonus;
        int etat = 1;
        Time heureDeCoupure;
        for (Reception r: receptions) {
            Status status = new Status();
            PlaqueSolaire plaqueSolaire = new PlaqueSolaire();
            plaqueSolaire.setReception(r);
            plaqueSolaire.setPuissanceMax((int) initialPower[1]);
            puissancePlaque = plaqueSolaire.getSolarPower();
            puissanceTotale = puissancePlaque + puissanceBatterieADeboursee;
            if(r.getHeureReception().before(Time.valueOf(LocalTime.of(12, 0, 1)))){
                nombreEleve = this.nombreEleveCoupure;
            }
            else{
                nombreEleve =  this.nombreEleveCoupureMidi;
            }
         //   nombreEleve = this.secteur.getTotalCharge(r.getDateReception(), r.getHeureReception(), c);
            puissanceRequis = this.totalBesoin * nombreEleve;
            
            if(puissanceTotale < puissanceRequis || etat == 0){
                if(etat == 1){
                    minuteDeCoupure = (puissanceTotale * 60) / puissanceRequis;
                    heureDeCoupure = Time.valueOf(LocalTime.of(r.getHeureReception().toLocalTime().getHour(), (int) minuteDeCoupure));
                    etat = 0;
                    status = new Status(puissanceRequis, puissancePlaque, etat, puissanceBatterieADeboursee, this.associationSecteurSource.getIdSecteur(), r.getIdReception(), heureDeCoupure);
                    status.setHeureDebut(r.getHeureReception());
                    status.setBesoin(puissanceRequis);
                    status.setInitialPower(initialPower);
                    statusList.add(status);
                }
                else{
                    puissanceBatterieADeboursee = puissanceBatterieADeboursee + ( puissancePlaque);
                    if(puissanceBatterieADeboursee > initialPower[0] / 2){ // angalana le batterie
                        puissanceBatterieADeboursee = initialPower[0] / 2;
                    }
                    heureDeCoupure = Time.valueOf(LocalTime.of(r.getHeureReception().toLocalTime().getHour(), (int) 59));
                    status = new Status(puissanceRequis, puissancePlaque, etat, puissanceBatterieADeboursee, this.associationSecteurSource.getIdSecteur(), r.getIdReception(), heureDeCoupure);
                    status.setHeureDebut(r.getHeureReception());
                    status.setBesoin(puissanceRequis);
                    status.setInitialPower(initialPower);
                    statusList.add(status);   
                }
            }
            else if(puissanceTotale >= puissanceRequis & etat == 1){
                puissanceBatterieADeboursee = puissanceBatterieADeboursee - (puissanceRequis - puissancePlaque);
                if(puissanceBatterieADeboursee > initialPower[0] / 2){ // angalana le batterie
                    puissanceBatterieADeboursee = initialPower[0] / 2;
                }
               // if(puissanceRequis > puissancePlaque){
                 //   puissanceBatterieADeboursee = puissanceBatterieADeboursee - (puissanceRequis - puissancePlaque);
               // }
               /*  if(puissanceRequis > puissancePlaque){
                    puissanceBatterieADeboursee = puissanceBatterieADeboursee - (puissanceRequis - puissancePlaque);
                }*/
                if(puissanceBatterieADeboursee < 0) puissanceBatterieADeboursee = 0.0;
                
                heureDeCoupure = Time.valueOf(LocalTime.of(r.getHeureReception().toLocalTime().getHour(), 59));
                status = new Status(puissanceRequis, puissancePlaque, etat, puissanceBatterieADeboursee, this.associationSecteurSource.getIdSecteur(), r.getIdReception(), heureDeCoupure);
                status.setHeureDebut(r.getHeureReception());
                status.setBesoin(puissanceRequis);
                status.setInitialPower(initialPower);
                statusList.add(status);
            }
        } 
        return statusList;
    }

    public void setIdSecteur(String value){
        this.idSecteur = value;
    }
    public String getIdSecteur(){
        return this.idSecteur;
    }

    public void setDateCoupure(String value){
        value = value.replace("T", " ");
        value += ":00";
     //   System.out.println("Date "+value);
        this.dateCoupure = Timestamp.valueOf(value);
    }

    public void setDateCoupure(java.sql.Timestamp value){
        this.dateCoupure = value;
    }
    public java.sql.Timestamp getDateCoupure(){
        return this.dateCoupure;
    }

    public void setIdCoupure(String value){
        this.idCoupure = value;
    }
    public String getIdCoupure(){
        return this.idCoupure;
    }

    public void setAssociationSecteurSource(AssociationSecteurSource associationSecteurSource) {
        this.associationSecteurSource = associationSecteurSource;
    }

    public AssociationSecteurSource getAssociationSecteurSource() {
        return associationSecteurSource;
    }

    public static void main(String[] args) {
       Double[] d = new Double[2];
        System.out.println(d[0]);
    }

    public void setHeureFin(Time heureFin) {
        this.heureFin = heureFin;
    }
    
}
