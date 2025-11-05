package be.odisee.citymesh.dao;

import be.odisee.citymesh.domain.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository-interface voor het uitvoeren van CRUD-operaties op {@link Drone}-entiteiten.
 * <p>
 * Deze interface maakt gebruik van Spring Data JPA en biedt standaardmethoden
 * voor opslag, opvraging, verwijdering en batchverwerking.
 * </p>
 */
public interface DroneRepository extends JpaRepository<Drone, Integer> {

    /**
     * Slaat een drone op of werkt een bestaande drone bij.
     *
     * @param drone de te bewaren of te updaten drone
     * @return de opgeslagen drone
     */
    Drone save(Drone drone);

    /**
     * Zoekt een drone op basis van zijn ID.
     *
     * @param id de unieke ID van de drone
     * @return de gevonden drone of {@code null} als geen match
     */
    Drone findById(int id);

    /**
     * Haalt een lijst op van alle drones in de database.
     *
     * @return lijst van alle drones
     */
    List<Drone> findAll();

    /**
     * Verwijdert een drone op basis van ID.
     *
     * @param id de ID van de te verwijderen drone
     */
    void deleteById(int id);

    /**
     * Verwijdert alle drones in één batchoperatie.
     */
    void deleteAllInBatch();
}
