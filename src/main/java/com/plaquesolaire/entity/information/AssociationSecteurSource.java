package com.plaquesolaire.entity.information;

import annotation.*;
import com.plaquesolaire.entity.Utils;
import com.plaquesolaire.entity.infrastructure.Secteur;
import com.plaquesolaire.entity.objet.Batterie;
import com.plaquesolaire.entity.objet.PlaqueSolaire;
import dao.BddObject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

// ok
@Table(name = "association_secteur_source")
public class AssociationSecteurSource extends BddObject
{

    @ForeignKey
    @Column(name = "id_secteur")
    public String idSecteur;    

    @Column(name = "date_association")
    public java.sql.Date dateAssociation;    

    @ForeignKey
    @Column(name = "id_source")
    public String idSource;
    public double[] getTotalPower(Connection c) throws Exception {
        double[] retour = new double[2];
        List<Object> batterieList = new Batterie().getBatterieByIdSource(this.idSource, c);
        List<Object> plaqueSolaireList = new PlaqueSolaire().getPlaqueByIdSource(this.idSource, c);
        retour[0] = Utils.somme(batterieList, "getPuissanceMax");
        retour[1] = Utils.somme(plaqueSolaireList, "getPuissanceMax");
        return retour;
    }

    public double getTotalCharge(List<AssociationSecteurSource> associationSecteurSources, Date date, Time heure, Connection c) throws Exception {
        double power = 0;
        for (AssociationSecteurSource ass: associationSecteurSources) {
            Secteur secteur = new Secteur().getSecteurById(c, ass.getIdSecteur());
            int moment = 1;
            Time midi = Time.valueOf("12:00");
            if(heure.after(midi)){
                moment = 2;
            }
            PointageEleve pointageEleve = new PointageEleve().getPointageByIdSecteur(date, ass.getIdSecteur(), moment, c);
            power += pointageEleve.getPuissanceRequis() * pointageEleve.getNombre();
        }
        return power;
    }
    public List<AssociationSecteurSource> getCurrentSourceBySector(Connection c) throws Exception {
        String sql = "select * from association_secteur_source where date_association=" +
                "(select max(date_association) from association_secteur_source)";
        System.out.println(sql);
        return this.executeQuery(c, sql, this);
    }
    public void setIdSecteur(String value){
        this.idSecteur = value;
    }
    public String getIdSecteur(){
        return this.idSecteur;
    }

    public void setDateAssociation(java.sql.Date value){
        this.dateAssociation = value;
    }
    public java.sql.Date getDateAssociation(){
        return this.dateAssociation;
    }

    public void setIdSource(String value){
        this.idSource = value;
    }
    public String getIdSource(){
        return this.idSource;
    }

   
}
