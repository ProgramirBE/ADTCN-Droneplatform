package be.odisee.citymesh.controller;

import be.odisee.citymesh.domain.Drone;
import be.odisee.citymesh.domain.Reservatie;
import be.odisee.citymesh.service.BatteryConsumptionStrategy;
import be.odisee.citymesh.service.DroneService;
import be.odisee.citymesh.service.ReservatieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/api/drone")
public class DroneRestController {

    @Autowired
    private DroneService droneServiceImpl;

    @Autowired
    @Qualifier("ecoModeStrategy")
    private BatteryConsumptionStrategy ecoModeStrategy;

    @Autowired
    @Qualifier("turboModeStrategy")
    private BatteryConsumptionStrategy turboModeStrategy;

    @Autowired
    private ReservatieService reservatieService;

    @GetMapping("")
    @Operation(
            summary = "Geef alle drones terug",
            description = "Geeft een lijst van alle beschikbare drones.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lijst met drones",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Drone.class))
                    )
            }
    )
    public ResponseEntity<List<Drone>> getAllDrones() {
        List<Drone> drones = droneServiceImpl.getAllDrones();
        return ResponseEntity.ok(drones == null ? List.of() : drones);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Geef drone op basis van ID",
            description = "Zoekt een drone op basis van ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Drone gevonden", content = @Content(schema = @Schema(implementation = Drone.class))),
                    @ApiResponse(responseCode = "404", description = "Drone niet gevonden")
            }
    )
    public ResponseEntity<Drone> getDroneById(@PathVariable int id) {
        Drone drone = droneServiceImpl.getDroneById(id);
        return drone != null ? ResponseEntity.ok(drone) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("")
    @Operation(
            summary = "Voeg een nieuwe drone toe",
            description = "Voegt een nieuwe drone toe op basis van JSON.",
            requestBody = @RequestBody(
                    required = true,
                    description = "Drone object",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Drone.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Drone succesvol toegevoegd", content = @Content(schema = @Schema(implementation = Drone.class))),
                    @ApiResponse(responseCode = "400", description = "Ongeldige invoer")
            }
    )
    public ResponseEntity<Drone> addDrone(@org.springframework.web.bind.annotation.RequestBody Drone drone) {
        Drone created = droneServiceImpl.saveDrone(drone);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update een drone",
            description = "Update de gegevens van een drone op basis van ID.",
            requestBody = @RequestBody(description = "Bijgewerkte dronegegevens", required = true, content = @Content(schema = @Schema(implementation = Drone.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Drone bijgewerkt", content = @Content(schema = @Schema(implementation = Drone.class))),
                    @ApiResponse(responseCode = "404", description = "Drone niet gevonden")
            }
    )
    public ResponseEntity<Drone> updateDrone(@PathVariable int id, @org.springframework.web.bind.annotation.RequestBody Drone drone) {
        drone.setDroneID(id);
        Drone existing = droneServiceImpl.getDroneById(id);
        if (existing != null) {
            Drone updated = droneServiceImpl.saveDrone(drone);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Verwijder een drone",
            description = "Verwijdert een drone op basis van ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Drone verwijderd"),
                    @ApiResponse(responseCode = "404", description = "Drone niet gevonden")
            }
    )
    public ResponseEntity<Void> deleteDrone(@PathVariable int id) {
        Drone existing = droneServiceImpl.getDroneById(id);
        if (existing != null) {
            droneServiceImpl.deleteDrone(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/fly/{id}")
    @Operation(
            summary = "Laat een drone vliegen",
            description = "Laat een drone vliegen met opgegeven afstand en strategie (eco of turbo).",
            requestBody = @RequestBody(description = "Bevat 'distance' en 'strategy'", required = true,
                    content = @Content(schema = @Schema(example = "{\"distance\": 20, \"strategy\": \"turboModeStrategy\"}"))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vlucht succesvol uitgevoerd"),
                    @ApiResponse(responseCode = "404", description = "Drone niet gevonden")
            }
    )
    public ResponseEntity<Map<String, Integer>> flyDroneAndGetBattery(@PathVariable int id, @org.springframework.web.bind.annotation.RequestBody Map<String, Object> payload) {
        Drone drone = droneServiceImpl.getDroneById(id);
        if (drone == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("batterij", -1));
        }

        int distance = (Integer) payload.getOrDefault("distance", 10);
        String strategyName = (String) payload.get("strategy");

        BatteryConsumptionStrategy strategyToUse = "turboModeStrategy".equals(strategyName)
                ? turboModeStrategy
                : ecoModeStrategy;

        droneServiceImpl.flyDrone(drone, distance, strategyToUse);

        return ResponseEntity.ok(Map.of("batterij", drone.getBatterij()));
    }

    @PostMapping("/status/{id}")
    @Operation(
            summary = "Update status van een drone",
            description = "Wijzigt de status van een drone (bijv. INACTIVE, ACTIVE, etc.).",
            requestBody = @RequestBody(description = "JSON met 'status': bv. {\"status\": \"INACTIVE\"}",
                    required = true,
                    content = @Content(schema = @Schema(example = "{\"status\": \"INACTIVE\"}"))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status geüpdatet"),
                    @ApiResponse(responseCode = "400", description = "Ongeldige status"),
                    @ApiResponse(responseCode = "404", description = "Drone niet gevonden")
            }
    )
    public ResponseEntity<String> updateDroneStatus(@PathVariable int id,
                                                    @org.springframework.web.bind.annotation.RequestBody Map<String, Object> payload,
                                                    HttpSession session) {
        System.out.println("Payload ontvangen: " + payload);

        Drone drone = droneServiceImpl.getDroneById(id);
        if (drone == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Drone niet gevonden");
        }

        Object rawStatus = payload.get("status");
        String statusString = rawStatus != null ? rawStatus.toString() : null;

        System.out.println("Status ontvangen: " + statusString);

        if (statusString == null || statusString.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status mag niet leeg zijn.");
        }

        try {
            Drone.DroneStatus newStatus = Drone.DroneStatus.valueOf(statusString.toUpperCase());
            drone.setStatus(newStatus);
            droneServiceImpl.updateDrone(drone);

            return ResponseEntity.ok("Drone " + id + " status updated to " + newStatus.name());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ongeldige status: " + statusString);
        }
    }
}