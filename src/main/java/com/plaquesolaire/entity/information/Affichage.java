package com.plaquesolaire.entity.information;

import java.util.List;

public class Affichage {

    Coupure coupure;
    List<Status> status;
    
    public Affichage(){

    }
    public Affichage(Coupure coupure, List<Status> status) {
        this.setCoupure(coupure);
        this.setStatus(status);
    }

    public Coupure getCoupure() {
        return coupure;
    }
    public void setCoupure(Coupure coupure) {
        this.coupure = coupure;
    }
    public List<Status> getStatus() {
        return status;
    }
    public void setStatus(List<Status> status) {
        this.status = status;
    }
       
}
