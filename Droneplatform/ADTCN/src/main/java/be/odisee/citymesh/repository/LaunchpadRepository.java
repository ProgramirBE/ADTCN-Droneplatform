package be.odisee.citymesh.repository;

import be.odisee.citymesh.entity.Launchpad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LaunchpadRepository extends JpaRepository<Launchpad, Integer> {
    List<Launchpad> findByIsSafe(Boolean isSafe);
}

