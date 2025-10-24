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

    /**
     * Haalt alle reservaties op voor een bepaalde drone.
     *
     * @param droneId de ID van de drone
     * @return een lijst met alle bijhorende reservaties
     */
    List<Reservatie> findByDroneId(int droneId);

    /**
     * Haalt alle reservaties met een specifieke status op.
     *
     * @param status de gewenste reservatiestatus (bv. GERESERVEERD, ACTIEF, VOLTOOID)
     * @return een lijst met reservaties in die status
     */
    List<Reservatie> findByStatus(Reservatie.ReservatieStatus status);
}
