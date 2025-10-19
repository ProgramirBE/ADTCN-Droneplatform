package be.odisee.citymesh.domain;

import java.util.Date;
import java.util.List;

/**
 * Representatie van een bestelling met status, datum, onderdelen en wijzigingsdatum.
 * <p>
 * Elke bestelling bevat een lijst van {@link Onderdeel}-objecten en een status
 * die de levenscyclus van de bestelling beschrijft.
 * </p>
 */
public class Bestelling {

    private final int bestellingID;
    private final Date datum;
    private BestellingStatus status;
    private final List<Onderdeel> onderdelen;
    private Date laatsteWijziging;

    /**
     * Mogelijke statussen voor een bestelling.
     */
    public enum BestellingStatus {
        INITIAL("Initial"),
        IN_BEHANDELING("In Behandeling"),
        KLAAR_VOOR_CONTROLE("Klaar voor controle"),
        GEANNULEERD("Geannuleerd"),
        KLAAR_VOOR_VERZENDING("Klaar voor verzending"),
        FINAL("Final"),
        VERZONDEN("Verzonden"),
        AFGEROND("Afgerond");

        private final String status;

        BestellingStatus(String status) {
            this.status = status;
        }

        /**
         * Retourneert de leesbare statusnaam.
         *
         * @return status als string
         */
        public String getStatus() {
            return status;
        }
    }

    /**
     * Constructor voor een nieuwe bestelling.
     *
     * @param bestellingID     het unieke ID van de bestelling
     * @param datum            de aanmaakdatum van de bestelling
     * @param status           de initiële status
     * @param onderdelen       lijst van onderdelen in de bestelling
     */
    public Bestelling(int bestellingID, Date datum, BestellingStatus status, List<Onderdeel> onderdelen) {
        this.bestellingID = bestellingID;
        this.datum = datum;
        this.status = status;
        this.onderdelen = onderdelen;
        this.laatsteWijziging = new Date();
    }

    /**
     * Wijzigt de status van de bestelling en past de laatste wijzigingsdatum aan.
     *
     * @param nieuweStatus de nieuwe status
     */
    public void wijzigingStatus(BestellingStatus nieuweStatus) {
        this.status = nieuweStatus;
        this.laatsteWijziging = new Date();
    }

    /**
     * @return het unieke ID van de bestelling
     */
    public int getBestellingID() {
        return bestellingID;
    }

    /**
     * @return de aanmaakdatum van de bestelling
     */
    public Date getDatum() {
        return datum;
    }

    /**
     * @return de huidige status van de bestelling
     */
    public BestellingStatus getStatus() {
        return status;
    }

    /**
     * @return de lijst van onderdelen in de bestelling
     */
    public List<Onderdeel> getOnderdelen() {
        return onderdelen;
    }

    /**
     * @return datum waarop de bestelling het laatst werd gewijzigd
     */
    public Date getLaatsteWijziging() {
        return laatsteWijziging;
    }

    /**
     * Voeg een onderdeel toe aan de bestelling.
     *
     * @param onderdeel het toe te voegen onderdeel
     */
    public void addOnderdeel(Onderdeel onderdeel) {
        this.onderdelen.add(onderdeel);
    }

    /**
     * Verwijdert alle onderdelen uit een andere bestelling.
     *
     * @param bestelling de bestelling waarvan de onderdelen geleegd moeten worden
     */
    public void emptyOnderdelen(Bestelling bestelling) {
        bestelling.onderdelen.clear();
    }
}
