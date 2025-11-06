package be.odisee.citymesh.controller;

import be.odisee.citymesh.entity.Drone;
import be.odisee.citymesh.entity.User;
import be.odisee.citymesh.repository.DroneRepository;
import be.odisee.citymesh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/mechanic")
public class MechanicController {

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Modifier le statut d'un drone (uniquement pour les mécaniciens)
     */
    @PutMapping("/drone/{droneId}/status")
    public ResponseEntity<?> updateDroneStatus(
            @PathVariable Integer droneId,
            @RequestBody Map<String, Object> payload) {

        try {
            // Vérifier l'utilisateur
            Integer userId = (Integer) payload.get("userId");
            String newStatus = (String) payload.get("status");

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Vérifier que l'utilisateur a le rôle Mechanieker
            if (!user.hasRole("Mechanieker")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Seuls les mécaniciens peuvent modifier le statut des drones"));
            }

            // Récupérer le drone
            Drone drone = droneRepository.findById(droneId)
                    .orElseThrow(() -> new RuntimeException("Drone not found"));

            // Modifier le statut
            drone.setStatus(newStatus);
            Drone updated = droneRepository.save(drone);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Statut du drone modifié avec succès",
                    "drone", updated
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Modifier le niveau de batterie d'un drone (uniquement pour les mécaniciens)
     */
    @PutMapping("/drone/{droneId}/battery")
    public ResponseEntity<?> updateDroneBattery(
            @PathVariable Integer droneId,
            @RequestBody Map<String, Object> payload) {

        try {
            // Vérifier l'utilisateur
            Integer userId = (Integer) payload.get("userId");
            Object batteryObj = payload.get("batteryLevel");
            Short batteryLevel = batteryObj instanceof Integer ?
                ((Integer) batteryObj).shortValue() : (Short) batteryObj;

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Vérifier que l'utilisateur a le rôle Mechanieker
            if (!user.hasRole("Mechanieker")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Seuls les mécaniciens peuvent modifier le niveau de batterie"));
            }

            // Validation du niveau de batterie
            if (batteryLevel < 0 || batteryLevel > 100) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Le niveau de batterie doit être entre 0 et 100"));
            }

            // Récupérer le drone
            Drone drone = droneRepository.findById(droneId)
                    .orElseThrow(() -> new RuntimeException("Drone not found"));

            // Modifier le niveau de batterie
            drone.setBatteryLevel(batteryLevel);
            Drone updated = droneRepository.save(drone);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Niveau de batterie modifié avec succès",
                    "drone", updated
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Réinitialiser un drone (statut + batterie 100%)
     */
    @PutMapping("/drone/{droneId}/reset")
    public ResponseEntity<?> resetDrone(
            @PathVariable Integer droneId,
            @RequestBody Map<String, Object> payload) {

        try {
            Integer userId = (Integer) payload.get("userId");

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!user.hasRole("Mechanieker")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Seuls les mécaniciens peuvent réinitialiser un drone"));
            }

            Drone drone = droneRepository.findById(droneId)
                    .orElseThrow(() -> new RuntimeException("Drone not found"));

            // Réinitialiser : status = Vliegklaar, battery = 100%
            drone.setStatus("Vliegklaar");
            drone.setBatteryLevel((short) 100);
            Drone updated = droneRepository.save(drone);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Drone réinitialisé avec succès",
                    "drone", updated
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}

