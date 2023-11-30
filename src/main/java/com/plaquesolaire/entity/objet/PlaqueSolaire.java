package com.plaquesolaire.entity.objet;

import annotation.*;
import dao.BddObject;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

// ok
@Table(name = "plaque_solaire")
public class PlaqueSolaire extends BddObject
{

    @PrimaryKey(prefix = "PS", sequence = "seq_plaque", length = 10)
    @Column(name = "id_plaque_solaire")
    public String idPlaqueSolaire;    

    @Column(name = "puissance_max")
    public Integer puissanceMax;

    @Column(name = "id_source")
    public String idSource;

    public double getCurrentPower(Integer heure, double puissance){
        return (heure * puissance) / 10;
    }
    public List<Object> getPlaqueByIdSource(String idSource, Connection c) throws Exception {
        PlaqueSolaire plaqueSolaire = new PlaqueSolaire();
        plaqueSolaire.setIdSource(idSource);
        return plaqueSolaire.findWhere(c);
    }

    public String getIdSource() {
        return idSource;
    }

    public void setIdSource(String idSource) {
        this.idSource = idSource;
    }

    public void setIdPlaqueSolaire(String value){
        this.idPlaqueSolaire = value;
    }
    public String getIdPlaqueSolaire(){
        return this.idPlaqueSolaire;
    }

    public void setPuissanceMax(Integer value){
        this.puissanceMax = value;
    }
    public Integer getPuissanceMax(){
        return this.puissanceMax;
    }

   
}
