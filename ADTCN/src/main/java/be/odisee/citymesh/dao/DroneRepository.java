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
    // Methoden JPA standaard beschikbaar via JpaRepository
    List<Drone> findAll();
    Drone save(Drone drone);
    Drone findById(int id);
    void deleteById(int id);
    void deleteAllInBatch();
}
