package be.odisee.citymesh.domain;

import java.util.Date;

/**
 * Klasse die een verslag voorstelt dat bijvoorbeeld na een dronevlucht of inspectie geschreven is.
 * <p>
 * Een verslag bevat een ID, inhoud, een datum en (optioneel) een koppeling met een {@code DronePiloot}.
 * </p>
 */
public class Verslag {

    /**
     * Uniek ID van het verslag.
     */
    private int VerslagId;

    /**
     * Inhoud van het verslag (tekstueel).
     */
    private String Inhoud;

    /**
     * Datum waarop het verslag geschreven is.
     */
    private Date Datum;

    // Koppeling met de piloot (optioneel)
    // private DronePiloot piloot;

    /**
     * Lege constructor voor frameworks of initialisatie zonder gegevens.
     */
    public Verslag() {}

    /**
     * Volledige constructor voor het maken van een verslag.
     *
     * @param verslagId het unieke ID van het verslag
     * @param inhoud    de inhoud van het verslag
     * @param datum     de datum waarop het verslag geschreven werd
     */
    public Verslag(int verslagId, String inhoud, Date datum /*, DronePiloot piloot */) {
        this.VerslagId = verslagId;
        this.Inhoud = inhoud;
        this.Datum = datum;
        // this.piloot = piloot;
    }

    /**
     * @return het ID van het verslag
     */
    public int getVerslagId() {
        return VerslagId;
    }

    /**
     * @param verslagId stel het ID van dit verslag in
     */
    public void setVerslagId(int verslagId) {
        this.VerslagId = verslagId;
    }

    /**
     * @return de inhoud van het verslag
     */
    public String getInhoud() {
        return Inhoud;
    }

    /**
     * @param inhoud stel de inhoud van het verslag in
     */
    public void setInhoud(String inhoud) {
        this.Inhoud = inhoud;
    }

    /**
     * @return de datum van het verslag
     */
    public Date getDatum() {
        return Datum;
    }

    /**
     * @param datum stel de datum van het verslag in
     */
    public void setDatum(Date datum) {
        this.Datum = datum;
    }

    /*
    // Getter voor gekoppelde piloot
    public DronePiloot getPiloot() {
        return piloot;
    }

    // Setter voor gekoppelde piloot
    public void setPiloot(DronePiloot piloot) {
        this.piloot = piloot;
    }
    */
}
