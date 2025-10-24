package be.odisee.citymesh.service;

import be.odisee.citymesh.domain.Reservatie;
import java.util.List;

/**
 * Interface voor het beheren van {@link Reservatie} objecten binnen het dronesysteem van Citymesh.
 * <p>
 * Bevat methoden voor CRUD-operaties en opvragingen op basis van drone-ID of reservatiestatus.
 */
public interface ReservatieService {

    /**
     * Haalt alle reservaties op uit de databank.
     *
     * @return een lijst met alle {@link Reservatie} objecten
     */
    List<Reservatie> getAllReservaties();

    /**
     * Haalt een specifieke reservatie op aan de hand van zijn ID.
     *
     * @param id de unieke ID van de reservatie
     * @return het bijbehorende {@link Reservatie} object, of {@code null} indien niet gevonden
     */
    Reservatie getReservatieById(int id);

    /**
     * Slaat een nieuwe reservatie op in de databank.
     *
     * @param reservatie het {@link Reservatie} object dat opgeslagen moet worden
     * @return het opgeslagen {@link Reservatie} object (met gegenereerde ID)
     */
    Reservatie saveReservatie(Reservatie reservatie);

    /**
     * Werkt een bestaande reservatie bij in de databank.
     *
     * @param reservatie het {@link Reservatie} object dat geüpdatet moet worden
     */
    void updateReservatie(Reservatie reservatie);

    /**
     * Verwijdert een reservatie op basis van zijn ID.
     *
     * @param id de ID van de te verwijderen reservatie
     */
    void deleteReservatie(int id);

    /**
     * Haalt alle reservaties op die gekoppeld zijn aan een bepaalde drone.
     *
     * @param droneId het ID van de drone
     * @return een lijst van {@link Reservatie} objecten voor de opgegeven drone
     */
    List<Reservatie> getReservatiesByDroneId(int droneId);

    /**
     * Haalt alle reservaties op met een bepaalde status.
     *
     * @param status de gewenste {@link Reservatie.ReservatieStatus}
     * @return een lijst van {@link Reservatie} objecten met de opgegeven status
     */
    List<Reservatie> getReservatiesByStatus(Reservatie.ReservatieStatus status);
}
