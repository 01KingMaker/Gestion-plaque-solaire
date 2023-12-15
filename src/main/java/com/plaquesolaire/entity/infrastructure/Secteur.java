package com.plaquesolaire.entity.infrastructure;

import annotation.*;
import com.plaquesolaire.entity.information.PointageEleve;
import dao.BddObject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

// ok
@Table(name = "secteur")
public class Secteur extends BddObject<Secteur>
{

    @PrimaryKey(prefix = "SEC", sequence = "seq_secteur", length = 10)
    @Column(name = "id_secteur")
    public String idSecteur;    

    @Column(name = "nom_secteur")
    public String nomSecteur;    

    @Column(name = "capacite_max")
    public Integer capaciteMax; 

    List<Batiment> battiments;

    public double getTotalCharge(Date date, Time heure, Connection c) throws Exception {
        double power = 0;
        this.setBattimentsPersistence(c);
        for (Batiment ass: this.getBattiments()) {
            int moment = 1;
            Time midi = Time.valueOf(LocalTime.of(12, 0, 1));
            if(heure.after(midi)){
                moment = 2;
            }
            PointageEleve pointageEleve = new PointageEleve().getPointageByIdSecteur(date, ass.getIdBatiment(), moment, c);
            /*power += pointageEleve.getPuissanceRequis()  poinmvntageEleve.getNombre()*/;
            power += pointageEleve.getNombre();
        }
        return power;
    }
    public void setBattimentsPersistence(Connection c) throws Exception {
        this.battiments = new Batiment().getListBattimentByIdSecteur(c, this.idSecteur);
    }

    public void setBattiments(List<Batiment> battiments) {
        this.battiments = battiments;
    }

    public List<Batiment> getBattiments() {
        return battiments;
    }

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
