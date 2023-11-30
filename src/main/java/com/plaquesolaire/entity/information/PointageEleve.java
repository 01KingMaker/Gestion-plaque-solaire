package com.plaquesolaire.entity.information;

import annotation.*;
import dao.BddObject;

import java.sql.Connection;
import java.sql.Date;

// ok
@Table(name = "pointage_eleve")
public class PointageEleve extends BddObject
{

    @ForeignKey
    @Column(name = "id_secteur")
    public String idSecteur;    

    @Column(name = "date_pointage")
    public java.sql.Date datePointage;    

    @Column(name = "puissance_requis")
    public double puissanceRequis;    

    @PrimaryKey(prefix = "PTG", sequence = "seq_pointage", length = 10)
    @Column(name = "id_pointage")
    public Integer idPointage;    

    @Column(name = "division_journee")
    public Integer divisionJournee;    

    @Column(name = "nombre")
    public Integer nombre;    

    public PointageEleve getPointageByIdSecteur(Date date, String idSecteur, Integer moment,Connection c) throws Exception {
        PointageEleve pointageEleve = new PointageEleve();
        pointageEleve.setDatePointage(date);
        pointageEleve.setIdSecteur(idSecteur);
        pointageEleve.setDivisionJournee(moment);
        return (PointageEleve) pointageEleve.findWhere(c);
    }
    public void setIdSecteur(String value){
        this.idSecteur = value;
    }
    public String getIdSecteur(){
        return this.idSecteur;
    }

    public void setDatePointage(java.sql.Date value){
        this.datePointage = value;
    }
    public java.sql.Date getDatePointage(){
        return this.datePointage;
    }

    public void setPuissanceRequis(double value){
        this.puissanceRequis = value;
    }
    public double getPuissanceRequis(){
        return this.puissanceRequis;
    }

    public void setIdPointage(Integer value){
        this.idPointage = value;
    }
    public Integer getIdPointage(){
        return this.idPointage;
    }

    public void setDivisionJournee(Integer value){
        this.divisionJournee = value;
    }
    public Integer getDivisionJournee(){
        return this.divisionJournee;
    }

    public void setNombre(Integer value){
        this.nombre = value;
    }
    public Integer getNombre(){
        return this.nombre;
    }

   
}
