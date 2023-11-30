package com.plaquesolaire.entity.infrastructure;

import annotation.*;
import dao.BddObject;

import java.sql.Connection;
import java.util.List;

// ok
@Table(name = "secteur")
public class Secteur extends BddObject
{

    @Column(name = "capacite_max")
    public Integer capaciteMax;    

    @PrimaryKey(prefix = "SEC", sequence = "seq_secteur", length = 10)
    @Column(name = "id_secteur")
    public String idSecteur;    

    @Column(name = "nom_secteur")
    public String nomSecteur;    

    public Secteur getSecteurById(Connection c, String idSecteur) throws Exception {
        return (Secteur) this.findById(c, idSecteur);
    }
    public List<Object> getSecteurByIdSource(String idSource, Connection c){
        Secteur secteur = new Secteur();
        return null;
    }
    public void setCapaciteMax(Integer value){
        this.capaciteMax = value;
    }
    public Integer getCapaciteMax(){
        return this.capaciteMax;
    }

    public void setIdSecteur(String value){
        this.idSecteur = value;
    }
    public String getIdSecteur(){
        return this.idSecteur;
    }

    public void setNomSecteur(String value){
        this.nomSecteur = value;
    }
    public String getNomSecteur(){
        return this.nomSecteur;
    }

   
}
