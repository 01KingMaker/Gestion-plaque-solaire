package com.plaquesolaire.entity.infrastructure;

import annotation.*;
import dao.BddObject;

import java.sql.Connection;
import java.util.List;

// ok
@Table(name = "battiment")
public class Batiment extends BddObject
{

    @PrimaryKey(prefix = "BAT", sequence = "seq_batiment", length = 10)
    @Column(name = "id_batiment")
    public String idBatiment;

    @Column(name = "nom")
    public String nom;

    @ForeignKey
    @Column(name = "id_secteur")
    public String idSecteur;
    public double capacite;

    double puissance;

    public List<Batiment> getListBattimentByIdSecteur(Connection c, String idSecteur) throws Exception {
        String sql = "select * from battiment where id_battiment in (select id_battiment from " +
                " association_secteur_battiment where id_secteur = '" + idSecteur+"')";
        Batiment battiment = new Batiment();
        battiment.setIdSecteur(idSecteur);
        return battiment.findWhere(c);
    }
    public void setIdBatiment(String value){
        this.idBatiment = value;
    }
    public String getIdBatiment(){
        return this.idBatiment;
    }

    public void setNom(String value){
        this.nom = value;
    }
    public String getNom(){
        return this.nom;
    }

    public void setCapacite(double capacite) {
        this.capacite = capacite;
    }

    public void setPuissance(double puissance) {
        this.puissance = puissance;
    }

    public double getCapacite() {
        return capacite;
    }

    public double getPuissance() {
        return puissance;
    }

    public void setIdSecteur(String idSecteur) {
        this.idSecteur = idSecteur;
    }

    public String getIdSecteur() {
        return idSecteur;
    }
}
