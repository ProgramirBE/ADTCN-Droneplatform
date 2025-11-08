package be.odisee.citymesh.dao;

import be.odisee.citymesh.domain.Reservatie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository-interface voor het beheren van {@link Reservatie}-entiteiten via Spring Data JPA.
 * <p>
 * Biedt standaard CRUD-operaties via {@code JpaRepository}, en extra zoekmethoden
 * gebaseerd op drone-ID en reservatiestatus.
 * </p>
 */
@Repository
public interface ReservatieRepository extends JpaRepository<Reservatie, Integer> {
    // keep only custom query methods; standard CRUD methods (findById, save, deleteById, findAll) come from JpaRepository
    List<Reservatie> findByDroneId(int droneId);
    List<Reservatie> findByStatus(Reservatie.ReservatieStatus status);
}
