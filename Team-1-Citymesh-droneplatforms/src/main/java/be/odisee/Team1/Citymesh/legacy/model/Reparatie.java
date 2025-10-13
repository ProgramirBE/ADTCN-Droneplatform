package be.odisee.Team1.Citymesh.legacy.model;

import java.util.Date;

public class Reparatie {

    private int reparatieID;
    private Date datum;
    private String status;

    public int getReparatieID() {
        return reparatieID;
    }

    public void setReparatieID(int reparatieID) {
        this.reparatieID = reparatieID;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
