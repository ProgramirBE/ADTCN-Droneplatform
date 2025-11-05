package be.odisee.citymesh.controller;

import be.odisee.citymesh.entity.Launchpad;
import be.odisee.citymesh.entity.Reservation;
import be.odisee.citymesh.entity.User;
import be.odisee.citymesh.repository.LaunchpadRepository;
import be.odisee.citymesh.repository.ReservationRepository;
import be.odisee.citymesh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private LaunchpadRepository launchpadRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Integer id) {
        return reservationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<Reservation> getReservationsByUser(@PathVariable Integer userId) {
        return reservationRepository.findByUserId(userId);
    }

    @GetMapping("/launchpad/{launchpadId}")
    public List<Reservation> getReservationsByLaunchpad(@PathVariable Integer launchpadId) {
        return reservationRepository.findByLaunchpadId(launchpadId);
    }

    @GetMapping("/available")
    public List<Launchpad> getAvailableLaunchpads() {
        // Retourne les launchpads qui sont "safe" et disponibles
        return launchpadRepository.findByIsSafe(true);
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Map<String, Object> payload) {
        try {
            Integer userId = (Integer) payload.get("userId");
            Integer launchpadId = (Integer) payload.get("launchpadId");
            String startTimeStr = (String) payload.get("startTime");
            String endTimeStr = (String) payload.get("endTime");

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Launchpad launchpad = launchpadRepository.findById(launchpadId)
                    .orElseThrow(() -> new RuntimeException("Launchpad not found"));

            // Vérifier si le launchpad est sûr
            if (!launchpad.getIsSafe()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Ce launchpad n'est pas sûr pour le décollage"));
            }

            // Vérifier les conflits de réservation
            LocalDateTime startTime = LocalDateTime.parse(startTimeStr);
            LocalDateTime endTime = LocalDateTime.parse(endTimeStr);

            List<Reservation> conflicts = reservationRepository
                    .findConflictingReservations(launchpadId, startTime, endTime);

            if (!conflicts.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Ce launchpad est déjà réservé pour cette période"));
            }

            Reservation reservation = new Reservation();
            reservation.setUser(user);
            reservation.setLaunchpad(launchpad);
            reservation.setStartTime(startTime);
            reservation.setEndTime(endTime);
            reservation.setStatus("CONFIRMED");
            reservation.setCreatedAt(LocalDateTime.now());

            Reservation saved = reservationRepository.save(reservation);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Erreur lors de la création: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelReservation(@PathVariable Integer id) {
        return reservationRepository.findById(id)
                .map(reservation -> {
                    reservation.setStatus("CANCELLED");
                    Reservation updated = reservationRepository.save(reservation);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

