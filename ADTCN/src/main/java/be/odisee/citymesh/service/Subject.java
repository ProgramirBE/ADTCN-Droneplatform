package be.odisee.citymesh.service;

/**
 * Interface voor het Subject in het Observer-patroon.
 * <p>
 * Een klasse die deze interface implementeert kan één of meerdere {@link Observer}-objecten beheren
 * en deze op de hoogte brengen van veranderingen, zoals statuswijzigingen van drones.
 */
public interface Subject {

    /**
     * Voegt een {@link Observer} toe aan de lijst van geabonneerde observers.
     *
     * @param o het observer-object dat toegevoegd moet worden
     */
    void addObserver(Observer o);

    /**
     * Verwittigt alle geregistreerde {@link Observer}-objecten met een specifiek bericht.
     *
     * @param bericht de boodschap die aan alle observers wordt doorgestuurd
     */
    void notifyObservers(String bericht);

    /**
     * Verwijdert een {@link Observer} uit de lijst van geabonneerde observers.
     *
     * @param o het observer-object dat verwijderd moet worden
     */
    void removeObserver(Observer o);
}
