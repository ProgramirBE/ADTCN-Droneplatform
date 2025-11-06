package be.odisee.citymesh.repository;

import be.odisee.citymesh.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByUserId(Integer userId);

    List<Reservation> findByLaunchpadId(Integer launchpadId);

    @Query("SELECT r FROM Reservation r WHERE r.launchpad.id = :launchpadId " +
           "AND r.status != 'CANCELLED' " +
           "AND ((r.startTime <= :endTime AND r.endTime >= :startTime))")
    List<Reservation> findConflictingReservations(
            @Param("launchpadId") Integer launchpadId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}

