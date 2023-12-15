package com.plaquesolaire.entity.information;

import annotation.*;
import dao.BddObject;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

// ok
@Table(name = "reception")
public class Reception extends BddObject
{


    @PrimaryKey(prefix = "REC", sequence = "seq_lumiere", length = 10)
    @Column(name = "id_reception")
    public String idReception;
  
    
    @Column(name = "date_reception")
    public java.sql.Date dateReception;    
    
    @Column(name = "luminosite")
    public Integer luminosite;    

    @Column(name = "heure_reception")
    public java.sql.Time heureReception;  


    public List<Reception> getAllLuminosiy(Date date, Connection c) throws Exception {
        Reception lumiere = new Reception();
        lumiere.setDateReception(date);
        String sql = "select * from reception where date_reception='"+date+"' order by heure_reception";
       // System.out.println(sql);
        return this.executeQuery(c, sql, new Reception());
    }

    public void setHeureReception(java.sql.Time value){
        this.heureReception = value;
    }
    public java.sql.Time getHeureReception(){
        return this.heureReception;
    }

    public void setLuminosite(Integer value) throws Exception {
        //if(value < 0 | value > 10) throw new Exception("Hors limite ="+value);
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
