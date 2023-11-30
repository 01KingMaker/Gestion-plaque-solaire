package com.plaquesolaire.entity.information;

import annotation.*;
import dao.BddObject;

// ok
@Table(name = "status")
public class Status extends BddObject
{

    @PrimaryKey(prefix = "STT", sequence = "seq_status", length = 10)
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

    public Status(double besoin, double puissanceSolaire, Integer etat, double puissanceBatterie) {
        this.besoin = besoin;
        this.puissanceSolaire = puissanceSolaire;
        this.etat = etat;
        this.puissanceBatterie = puissanceBatterie;
    }

    public Status(){
    }

    public void setIdStatus(String value){
        this.idStatus = value;
    }
    public String getIdStatus(){
        return this.idStatus;
    }

    public void setBesoin(double value){
        this.besoin = value;
    }
    public double getBesoin(){
        return this.besoin;
    }

    public void setPuissanceSolaire(double value){
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

    public void setPuissanceBatterie(double value){
        this.puissanceBatterie = value;
    }
    public double getPuissanceBatterie(){
        return this.puissanceBatterie;
    }

   
}
