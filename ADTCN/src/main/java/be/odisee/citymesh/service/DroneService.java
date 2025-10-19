package be.odisee.citymesh.service;

import be.odisee.citymesh.domain.Drone;
import be.odisee.citymesh.domain.Melding;

import java.util.List;

/**
 * Interface voor het beheren van drones binnen de Citymesh-toepassing.
 * <p>
 * Bevat methoden voor het uitvoeren van CRUD-operaties, batterijbeheer, vluchtlogica,
 * locatieverwerking, matrixgeneratie en reparaties.
 */
public interface DroneService {

    /**
     * Slaat een nieuwe drone op in de database.
     *
     * @param drone de drone die opgeslagen moet worden
     * @return de opgeslagen drone
     */
    Drone saveDrone(Drone drone);

    /**
     * Verwijdert een drone uit de database op basis van zijn ID.
     *
     * @param id het ID van de drone die verwijderd moet worden
     */
    void deleteDrone(int id);

    /**
     * Haalt een drone op aan de hand van zijn ID.
     *
     * @param id het ID van de drone
     * @return de gevonden drone of {@code null} als deze niet bestaat
     */
    Drone getDroneById(int id);

    /**
     * Haalt alle drones op uit de database.
     *
     * @return een lijst met alle drones
     */
    List<Drone> getAllDrones();

    /**
     * Update een bestaande drone in de database.
     *
     * @param drone de drone met aangepaste waarden
     */
    void updateDrone(Drone drone);

    /**
     * Zet de status van een drone naar {@code CHARGING} en start het oplaadproces.
     *
     * @param droneId het ID van de drone die moet opladen
     */
    void startCharging(int droneId);

    /**
     * Verhoogt de batterij van een drone geleidelijk tot 100%.
     *
     * @param droneId het ID van de drone die opgeladen moet worden
     */
    void chargeBatterij(int droneId);

    /**
     * Start een vlucht voor een drone indien deze in {@code RESERVED} status is.
     *
     * @param drone de drone waarvoor de vlucht gestart wordt
     */
    void startDroneVlucht(Drone drone);

    /**
     * Reserveert een drone indien deze beschikbaar of defect is.
     *
     * @param drone de drone die gereserveerd wordt
     */
    void reserveerDrone(Drone drone);

    /**
     * Zet de status van de drone terug op {@code INACTIVE}.
     *
     * @param drone de drone die gereset wordt
     */
    void resetDroneStatus(Drone drone);

    /**
     * Geeft de huidige locatie van de drone terug als deze in vlucht is.
     *
     * @param drone de drone waarvan de locatie verwerkt wordt
     * @return de locatie als string of {@code null} als de drone niet in vlucht is
     */
    String verwerkDroneLocatie(Drone drone);

    /**
     * Start een reparatieprocedure op basis van een ontvangen melding.
     *
     * @param drone   de drone die onderhoud nodig heeft
     * @param melding de bijhorende melding die het probleem beschrijft
     */
    void startReparatie(Drone drone, Melding melding);

    /**
     * Simuleert een vlucht en past batterijverbruik toe volgens een gegeven strategie.
     *
     * @param drone    de drone die vliegt
     * @param distance de afstand die gevlogen wordt
     * @param strategy de gebruikte batterijstrategie (bv. eco, turbo)
     */
    void flyDrone(Drone drone, int distance, BatteryConsumptionStrategy strategy);

    /**
     * Genereert een matrix van bevolkingsdichtheid (of andere waarden) voor simulatie.
     *
     * @param size de grootte van de matrix (aantal rijen en kolommen)
     * @return een 2D-array met gegenereerde waarden
     */
    int[][] genereerMatrix(int size);
}
