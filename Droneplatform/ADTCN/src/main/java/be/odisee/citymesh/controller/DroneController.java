package be.odisee.citymesh.controller;

import be.odisee.citymesh.entity.Drone;
import be.odisee.citymesh.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drones")
public class DroneController {

    @Autowired
    private DroneRepository droneRepository;

    @GetMapping
    public List<Drone> getAllDrones() {
        return droneRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Drone> getDroneById(@PathVariable Integer id) {
        return droneRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Drone createDrone(@RequestBody Drone drone) {
        return droneRepository.save(drone);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Drone> updateDrone(@PathVariable Integer id, @RequestBody Drone droneDetails) {
        return droneRepository.findById(id)
                .map(drone -> {
                    drone.setName(droneDetails.getName());
                    drone.setModel(droneDetails.getModel());
                    drone.setStatus(droneDetails.getStatus());
                    drone.setBatteryLevel(droneDetails.getBatteryLevel());
                    return ResponseEntity.ok(droneRepository.save(drone));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrone(@PathVariable Integer id) {
        if (droneRepository.existsById(id)) {
            droneRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

