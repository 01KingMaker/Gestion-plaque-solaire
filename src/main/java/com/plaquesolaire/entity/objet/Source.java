package com.plaquesolaire.entity.objet;

import annotation.*;
import dao.BddObject;

import java.util.List;

// ok
@Table(name = "source")
public class Source extends BddObject
{

    @Column(name = "puissance_plaque__totale")
    public double puissancePlaqueTotale;

    @PrimaryKey(prefix = "SRC", sequence = "seq_source", length = 10)
    @Column(name = "id_source")
    public String idSource;    

    @ForeignKey
    @Column(name = "id_batterie")
    public String idBatterie;    

    @Column(name = "puissance_batterie_totale")
    public double puissanceBatterieTotale;    


    public List<Batterie> batterieList;
    public List<PlaqueSolaire> plaqueSolaireList;

    public void setPlaqueSolaireList(List<PlaqueSolaire> plaqueSolaireList) {
        this.plaqueSolaireList = plaqueSolaireList;
    }

    public List<PlaqueSolaire> getPlaqueSolaireList() {
        return plaqueSolaireList;
    }

    public void setBatterieList(List<Batterie> batterieList) {
        this.batterieList = batterieList;
    }

    public List<Batterie> getBatterieList() {
        return batterieList;
    }

    public void setPuissancePlaqueTotale(double value){
        this.puissancePlaqueTotale = value;
    }
    public double getPuissancePlaqueTotale(){
        return this.puissancePlaqueTotale;
    }

    public void setIdSource(String value){
        this.idSource = value;
    }
    public String getIdSource(){
        return this.idSource;
    }

    public void setIdBatterie(String value){
        this.idBatterie = value;
    }
    public String getIdBatterie(){
        return this.idBatterie;
    }

    public void setPuissanceBatterieTotale(double value){
        this.puissanceBatterieTotale = value;
    }
    public double getPuissanceBatterieTotale(){
        return this.puissanceBatterieTotale;
    }

   
}
