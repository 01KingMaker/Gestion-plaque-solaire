package com.plaquesolaire;

import com.plaquesolaire.entity.Utils;
import com.plaquesolaire.entity.information.Affichage;
import com.plaquesolaire.entity.information.Coupure;
import com.plaquesolaire.entity.information.Status;
import dao.DbConnection;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
public class Main {
    public static void main(String[] args) throws Exception {

        DbConnection.setDatasource("jdbc:postgresql://localhost:5432/plaque2");
        DbConnection.setUsername("your_username");
        DbConnection.setPassword("your_password");
        DbConnection.setDriver("org.postgresql.Driver");
        
        try(Connection c = DbConnection.connect()){
            c.setAutoCommit(false);
            Coupure coupure = new Coupure("2023-12-13T00:00", "SCT001");
            Affichage statusList = coupure.getPredictionStatus(c);
            System.out.println("Vita");
            System.out.println("Moyenne "+statusList.getCoupure().getTotalBesoin());
            for (Status s: statusList.getStatus()) {
                s.printStatus();
            }
            c.commit();
        }
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.of(2023, 12, 01, 0, 0, 0));
        System.out.println(Utils.getDayOfWeek(timestamp.getTime()));       
    }
}