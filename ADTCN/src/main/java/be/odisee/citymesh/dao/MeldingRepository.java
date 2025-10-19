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

    /**
     * Slaat een {@link Melding} op in de database.
     *
     * @param melding het melding-object dat moet worden opgeslagen
     * @return de opgeslagen melding met gegenereerd ID
     */
    Melding save(Melding melding);

    /**
     * Haalt alle meldingen op uit de database.
     *
     * @return een lijst van alle {@link Melding}-objecten
     */
    List<Melding> findAll();
}
