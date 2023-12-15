package com.plaquesolaire.entity;

import com.plaquesolaire.entity.information.Status;

public class MonComparator implements java.util.Comparator<Status> {

    @Override
    public int compare(Status arg0, Status arg1) {
        // TODO Auto-generated method stub
        return arg0.getHeureDebut().compareTo(arg1.getHeureDebut());
        //throw new UnsupportedOperationException("Unimplemented method 'compare'");
    }
    
}
