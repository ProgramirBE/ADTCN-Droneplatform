package be.odisee.citymesh.domain;

import java.util.Date;

/**
 * Klasse die een reparatie representeert die uitgevoerd wordt op een drone.
 * <p>
 * Elke reparatie heeft een uniek ID, een datum en een status.
 * De reparatie kan ook uitgevoerd worden op een {@link Drone}-object.
 * </p>
 */
public class Reparatie {

    private int reparatieID;
    private Date datum;
    private String status;

    /**
     * Constructor om een nieuwe reparatie aan te maken.
     *
     * @param reparatieID het unieke ID van de reparatie
     * @param datum       de datum waarop de reparatie werd gestart of geregistreerd
     * @param status      de huidige status van de reparatie (bv. "In behandeling", "Gerepareerd")
     */
    public Reparatie(int reparatieID, Date datum, String status) {
        this.reparatieID = reparatieID;
        this.datum = datum;
        this.status = status;
    }

    /**
     * Start een reparatie op een drone.
     * <p>
     * Deze methode reset de status van de drone naar {@code INACTIVE}
     * en zet de reparatiestatus op "Gerepareerd".
     * </p>
     *
     * @param drone de drone waarop de reparatie wordt uitgevoerd
     */
    public void startReparatie(Drone drone) {
        drone.resetStatus();
        status = "Gerepareerd";
    }

    /**
     * @return het unieke ID van de reparatie
     */
    public int getReparatieID() {
        return reparatieID;
    }

    /**
     * @param reparatieID het nieuwe ID voor deze reparatie
     */
    public void setReparatieID(int reparatieID) {
        this.reparatieID = reparatieID;
    }

    /**
     * @return de datum van de reparatie
     */
    public Date getDatum() {
        return datum;
    }

    /**
     * @param datum de datum waarop de reparatie plaatsvond of werd aangemaakt
     */
    public void setDatum(Date datum) {
        this.datum = datum;
    }

    /**
     * @return de huidige status van de reparatie
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status de nieuwe status van de reparatie
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
