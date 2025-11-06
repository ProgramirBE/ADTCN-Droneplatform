package be.odisee.citymesh.repository;

import be.odisee.citymesh.entity.NoFlyZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoFlyZoneRepository extends JpaRepository<NoFlyZone, Integer> {
}

