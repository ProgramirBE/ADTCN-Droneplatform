package be.odisee.citymesh.service.impl;

import be.odisee.citymesh.domain.Reservatie;
import be.odisee.citymesh.dao.ReservatieRepository;
import be.odisee.citymesh.service.ReservatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementatie van de {@link ReservatieService} interface.
 * <p>
 * Deze klasse beheert de logica voor het ophalen, opslaan, updaten en verwijderen
 * van {@link Reservatie}-objecten via de {@link ReservatieRepository}.
 * </p>
 */
@Service
public class ReservatieServiceImpl implements ReservatieService {

    @Autowired
    private ReservatieRepository reservatieRepository;

    /**
     * Haalt alle reservaties op uit de database.
     *
     * @return een lijst met alle {@link Reservatie}-objecten
     */
    @Override
    public List<Reservatie> getAllReservaties() {
        return reservatieRepository.findAll();
    }

    /**
     * Haalt een specifieke reservatie op aan de hand van zijn ID.
     *
     * @param id het ID van de reservatie
     * @return de gevonden {@link Reservatie}, of {@code null} als deze niet bestaat
     */
    @Override
    public Reservatie getReservatieById(int id) {
        return reservatieRepository.findById(id).orElse(null);
    }

    /**
     * Slaat een nieuwe reservatie op in de database.
     *
     * @param reservatie het {@link Reservatie}-object om op te slaan
     * @return de opgeslagen reservatie
     */
    @Override
    public Reservatie saveReservatie(Reservatie reservatie) {
        return reservatieRepository.save(reservatie);
    }

    /**
     * Update een bestaande reservatie in de database.
     *
     * @param reservatie de te updaten {@link Reservatie}
     */
    @Override
    public void updateReservatie(Reservatie reservatie) {
        reservatieRepository.save(reservatie);
    }

    /**
     * Verwijdert een reservatie uit de database op basis van ID.
     *
     * @param id het ID van de te verwijderen reservatie
     */
    @Override
    public void deleteReservatie(int id) {
        reservatieRepository.deleteById(id);
    }

    /**
     * Haalt alle reservaties op voor een specifieke drone.
     *
     * @param droneId het ID van de drone
     * @return een lijst van {@link Reservatie} gekoppeld aan de gegeven drone
     */
    @Override
    public List<Reservatie> getReservatiesByDroneId(int droneId) {
        return reservatieRepository.findByDroneId(droneId);
    }

    /**
     * Haalt alle reservaties op met een specifieke status.
     *
     * @param status de status van de reservaties (bv. GERESERVEERD, ACTIEF)
     * @return een lijst van {@link Reservatie}-objecten met de gegeven status
     */
    @Override
    public List<Reservatie> getReservatiesByStatus(Reservatie.ReservatieStatus status) {
        return reservatieRepository.findByStatus(status);
    }
}
