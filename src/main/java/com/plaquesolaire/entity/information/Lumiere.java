package com.plaquesolaire.entity.information;

import annotation.*;
import dao.BddObject;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

// ok
@Table(name = "lumiere")
public class Lumiere extends BddObject
{

    @ForeignKey
    @Column(name = "id_status")
    public String idStatus;    

    @Column(name = "heure_reception")
    public java.sql.Time heureReception;    

    @Column(name = "luminosite")
    public Integer luminosite;    

    @Column(name = "date_reception")
    public java.sql.Date dateReception;    

    @PrimaryKey(prefix = "REC", sequence = "seq_lumiere", length = 10)
    @Column(name = "id_reception")
    public String idReception;    

    public List<Lumiere> getAllLuminosiy(Date date, Connection c) throws Exception {
        Lumiere lumiere = new Lumiere();
        lumiere.setDateReception(date);
        return lumiere.findWhere(c);
    }
    public void setIdStatus(String value){
        this.idStatus = value;
    }
    public String getIdStatus(){
        return this.idStatus;
    }

    public void setHeureReception(java.sql.Time value){
        this.heureReception = value;
    }
    public java.sql.Time getHeureReception(){
        return this.heureReception;
    }

    public void setLuminosite(Integer value) throws Exception {
        if(value < 0 | value > 10) throw new Exception("Hors limite ="+value);
        this.luminosite = value;
    }
    public Integer getLuminosite(){
        return this.luminosite;
    }

    public void setDateReception(java.sql.Date value){
        this.dateReception = value;
    }
    public java.sql.Date getDateReception(){
        return this.dateReception;
    }

    public void setIdReception(String value){
        this.idReception = value;
    }
    public String getIdReception(){
        return this.idReception;
    }

   
}
