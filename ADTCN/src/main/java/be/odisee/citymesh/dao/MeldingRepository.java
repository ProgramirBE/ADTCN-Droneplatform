package be.odisee.citymesh.dao;

import be.odisee.citymesh.domain.Melding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository-interface voor het uitvoeren van database-operaties op {@link Melding}-entiteiten.
 * <p>
 * Deze interface maakt gebruik van Spring Data JPA en biedt standaard CRUD-functionaliteit.
 */
public interface MeldingRepository extends JpaRepository<Melding, Integer> {
    List<Melding> findAll();
    Melding save(Melding melding);
}
