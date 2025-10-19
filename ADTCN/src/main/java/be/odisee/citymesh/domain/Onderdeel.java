package be.odisee.citymesh.domain;

/**
 * Klasse die een onderdeel representeert binnen het dronesysteem.
 * <p>
 * Elk onderdeel heeft een naam, een voorraad en een (gedeelde) ID.
 * </p>
 */
public class Onderdeel {

    /**
     * ⚠️ Opgelet: Deze variabele is static en wordt gedeeld door alle objecten.
     * Dit betekent dat alle onderdelen exact dezelfde ID zullen hebben (namelijk de laatste waarde +1).
     * Gebruik liever een instantieveld of een correcte ID-generator als je unieke IDs verwacht.
     */
    private static int OnderdeelId;

    private String Naam;
    private int vooraad;

    /**
     * Constructor voor een nieuw onderdeel.
     *
     * @param Naam    de naam van het onderdeel
     * @param vooraad het aantal eenheden op voorraad
     */
    public Onderdeel(String Naam, int vooraad) {
        this.Naam = Naam;
        this.vooraad = vooraad;
        this.OnderdeelId = OnderdeelId + 1;
    }

    /**
     * Wijzigt de voorraad met een positief of negatief aantal.
     *
     * @param wijziging het aantal eenheden om toe te voegen of af te trekken
     */
    public void wijzigVooraad(int wijziging) {
        vooraad = vooraad + wijziging;
    }
}
