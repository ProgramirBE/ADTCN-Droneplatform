package be.odisee.Team1.Citymesh.legacy.model;

import java.util.Date;

public class Melding {

    public enum MeldingStatus {
        BATTERY_LOW,
        MAINTENANCE_REQUIRED,
        OVERCROWDING
    }

    private int meldingID;
    private String beschrijving;
    private Date tijdstip;
    private MeldingStatus status;

    public int getMeldingID() {
        return meldingID;
    }

    public void setMeldingID(int meldingID) {
        this.meldingID = meldingID;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public Date getTijdstip() {
        return tijdstip;
    }

    public void setTijdstip(Date tijdstip) {
        this.tijdstip = tijdstip;
    }

    public MeldingStatus getStatus() {
        return status;
    }

    public void setStatus(MeldingStatus status) {
        this.status = status;
    }
}
