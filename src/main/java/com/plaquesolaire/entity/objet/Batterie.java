package com.plaquesolaire.entity.objet;

import annotation.*;
import dao.BddObject;

import java.sql.Connection;
import java.util.List;

// ok
@Table(name = "batterie")
public class Batterie extends BddObject
{

    @PrimaryKey(prefix = "BAT", sequence = "seq_batterie", length = 10)
    @Column(name = "id_batterie")
    public String idBatterie;    

    @Column(name = "puissance_max")
    public Integer puissanceMax;

    @Column(name = "id_source")
    public String idSource;

    public List<Object> getBatterieByIdSource(String idSource, Connection c) throws Exception {
        Batterie batterie = new Batterie();
        batterie.setIdSource(idSource);
        return batterie.findWhere(c);
    }

    public void setIdSource(String idSource) {
        this.idSource = idSource;
    }

    public String getIdSource() {
        return idSource;
    }

    public void setIdBatterie(String value){
        this.idBatterie = value;
    }
    public String getIdBatterie(){
        return this.idBatterie;
    }

    public void setPuissanceMax(Integer value){
        this.puissanceMax = value;
    }
    public Integer getPuissanceMax(){
        return this.puissanceMax;
    }

   
}
