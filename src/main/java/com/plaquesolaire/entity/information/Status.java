package com.plaquesolaire.entity.information;

import annotation.*;
import dao.BddObject;

import java.sql.Connection;
import java.sql.Time;
import java.util.Comparator;
import java.util.List;

// ok
@Table(name = "status")
public class Status extends BddObject
{

    @PrimaryKey(prefix = "STT", sequence = "seq_status", length = 7)
    @Column(name = "id_status")
    public String idStatus;

    @Column(name = "besoin")
    public double besoin;

    @Column(name = "puissance_solaire")
    public double puissanceSolaire;

    @Column(name = "etat")
    public Integer etat;

    @Column(name = "puissance_batterie")
    public double puissanceBatterie;
    @Column(name = "id_secteur")
    public String idSecteur;
    @Column(name = "id_reception")
    public String idReception;
    @Column(name = "heure_coupure")
    public Time heure;

    public Time heureDebut;

    public double[] initialPower;

    public double puissanceTotaleBatterie(){
        return this.puissanceBatterie + this.initialPower[0] / 2;
    }

    public void setInitialPower(double[] initialPower) {
        this.initialPower = initialPower;
    }
    public double[] getInitialPower() {
        return initialPower;
    }

    
    public String getStatusEtat(){
        if(this.etat == 1){
            return "Mandeha";
        }
        return "Maty";
    }

    public String getColor(){
        if(this.etat == 1){
            return "transparent";
        }
        return "#2b3035";
    }

    public static void insererStatus(List<Status> statusList, Connection c) throws Exception {
        for (Status s:
             statusList) {
            s.printStatus();
            //s.save(c);
        }
    }

    public void printStatus(){
        String printer = String.format("Solaire %s Etat %s Batterie %s Coupure %s Depart %s Besoin %s"
                , this.puissanceSolaire, this.etat, this.puissanceBatterie, this.heure, this.heureDebut, this.besoin
        );
        System.out.println(printer);
    }

    public Status(double besoin, double puissanceSolaire, Integer etat, double puissanceBatterie, String idSecteur, String idReception, Time heureFin) throws Exception {
        this.setBesoin(besoin);
        this.setPuissanceBatterie(puissanceBatterie);
        this.setEtat(etat);
        this.setPuissanceSolaire(puissanceSolaire);
        this.setIdSecteur(idSecteur);
        this.setIdReception(idReception);
        this.setHeure(heureFin);
    }

    public Status(){
    }

    public void setIdSecteur(String idSecteur) {
        this.idSecteur = idSecteur;
    }

    public String getIdSecteur() {
        return idSecteur;
    }

    public void setIdStatus(String value){
        this.idStatus = value;
    }
    public String getIdStatus(){
        return this.idStatus;
    }

    public void setBesoin(double value) throws Exception {
        if(value < 0) throw new Exception(String.format("Problème, besoin négatif %s", value));
        this.besoin = value;
    }
    public double getBesoin(){
        return this.besoin;
    }

    public void setPuissanceSolaire(double value) throws Exception {
        if(value <0) throw new Exception(String.format("Prblème, puissance solaire négative %s", value));
        this.puissanceSolaire = value;
    }
    public double getPuissanceSolaire(){
        return this.puissanceSolaire;
    }

    public void setEtat(Integer value){
        this.etat = value;
    }
    public Integer getEtat(){
        return this.etat;
    }

    public void setPuissanceBatterie(double value) throws Exception {
        if(value < 0) throw new Exception(String.format("Problème, puissance batterie négative %s",value));
        this.puissanceBatterie = value;
    }
    public double getPuissanceBatterie(){
        return this.puissanceBatterie;
    }

    public void setIdReception(String idReception) {
        this.idReception = idReception;
    }

    public String getIdReception() {
        return idReception;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }

    public Time getHeure() {
        return heure;
    }

    public Time getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Time heureDebut) {
        this.heureDebut = heureDebut;
    }


}
