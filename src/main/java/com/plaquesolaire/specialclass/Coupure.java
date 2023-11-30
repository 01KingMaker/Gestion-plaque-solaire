package com.plaquesolaire.specialclass;

import com.plaquesolaire.entity.information.AssociationSecteurSource;
import com.plaquesolaire.entity.information.Lumiere;
import com.plaquesolaire.entity.information.PointageEleve;
import com.plaquesolaire.entity.information.Status;
import com.plaquesolaire.entity.objet.PlaqueSolaire;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class Coupure {
    public Date date;

    public void generateCoupure(Connection c) throws Exception {
        List<Lumiere> receptions = new Lumiere().getAllLuminosiy(this.date, c);
        List<AssociationSecteurSource> associationSecteurSources = new AssociationSecteurSource().getCurrentSourceBySector(c);
        for (AssociationSecteurSource as: associationSecteurSources) {
            double[] initialPower = as.getTotalPower(c);
            double batterieInitialePower = initialPower[0];
            double plaqueInitialePower = initialPower[1];
            double reste = batterieInitialePower;
            for (Lumiere etat: receptions) {
                if(reste <= (batterieInitialePower/2)){
                    double currentPowerByHours = new PlaqueSolaire().getCurrentPower(etat.getLuminosite(), plaqueInitialePower);
                    double totalPuissance = currentPowerByHours + reste;
// public double getTotalCharge(List<AssociationSecteurSource> associationSecteurSources, Date date, Integer moment, Connection c)

                    double totalChargeRequis = new AssociationSecteurSource().getTotalCharge(associationSecteurSources, etat.getDateReception(), etat.getHeureReception(), c);
                    double chargeTakenByPlaque = currentPowerByHours - totalChargeRequis;
                    if(chargeTakenByPlaque < 0){
                        // tsy lany
                        // double besoin, double puissanceSolaire, Integer etat, double puissanceBatterie
                        Status status = new Status(totalChargeRequis, currentPowerByHours, 1, reste);
                    }
                    else{
                        reste =
                    }
                }
                else {

                }
            }
        }
    }

    public void setDate(String date){
        this.setDate(Date.valueOf(date));
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}
