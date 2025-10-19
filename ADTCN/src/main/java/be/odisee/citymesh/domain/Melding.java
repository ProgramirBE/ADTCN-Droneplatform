package be.odisee.citymesh.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Entity-klasse die een melding voorstelt die door het dronesysteem gegenereerd wordt.
 * <p>
 * Meldingen geven belangrijke informatie over de toestand van drones of het systeem,
 * zoals batterijstatus, onderhoudsbehoeften of overbevolking in het dronegebied.
 * </p>
 */
@Entity
public class Melding {

    /**
     * Mogelijke statussen voor een melding.
     */
    public enum MeldingStatus {
        /** De batterij van een drone is te laag. */
        BATTERY_LOW,
        /** De drone vereist onderhoud of reparatie. */
        MAINTENANCE_REQUIRED,
        /** Er is overbevolking vastgesteld in een gebied. */
        OVERCROWDING,
        /** Informatieve melding zonder urgentie. */
        INFO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int meldingID;

    private String beschrijving;
    private Date tijdstip;
    private MeldingStatus status;

    /**
     * Constructor met alle velden.
     *
     * @param meldingID    uniek ID van de melding
     * @param beschrijving tekstuele uitleg van het probleem of status
     * @param tijdstip     moment waarop de melding werd gegenereerd
     * @param status       het type van de melding
     */
    public Melding(int meldingID, String beschrijving, Date tijdstip, MeldingStatus status) {
        this.meldingID = meldingID;
        this.beschrijving = beschrijving;
        this.tijdstip = tijdstip;
        this.status = status;
    }

    /**
     * Lege constructor vereist door JPA.
     */
    public Melding() {}

    /**
     * @return het unieke ID van de melding
     */
    public int getMeldingID() {
        return meldingID;
    }

    /**
     * @param meldingID de ID van de melding
     */
    public void setMeldingID(int meldingID) {
        this.meldingID = meldingID;
    }

    /**
     * @return de beschrijving van de melding
     */
    public String getBeschrijving() {
        return beschrijving;
    }

    /**
     * @param beschrijving de tekst die uitlegt wat de melding betekent
     */
    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    /**
     * @return het tijdstip waarop de melding werd geregistreerd
     */
    public Date getTijdstip() {
        return tijdstip;
    }

    /**
     * @param tijdstip het moment waarop de melding ontstond
     */
    public void setTijdstip(Date tijdstip) {
        this.tijdstip = tijdstip;
    }

    /**
     * @return het status-type van de melding
     */
    public MeldingStatus getStatus() {
        return status;
    }

    /**
     * @param status de status of categorie van de melding
     */
    public void setStatus(MeldingStatus status) {
        this.status = status;
    }
}
