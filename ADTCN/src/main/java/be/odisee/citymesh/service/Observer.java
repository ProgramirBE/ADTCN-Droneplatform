package be.odisee.citymesh.service;

/**
 * Interface voor het implementeren van het Observer-patroon binnen het dronesysteem.
 * <p>
 * Klassen die deze interface implementeren, kunnen notificaties ontvangen van een {@link Subject}
 * wanneer er belangrijke gebeurtenissen plaatsvinden (zoals statuswijzigingen of meldingen).
 */
public interface Observer {

    /**
     * Methode die wordt aangeroepen wanneer het subject een update wil doorgeven aan zijn observers.
     *
     * @param message de boodschap of update die wordt verzonden
     */
    void update(String message);
}
