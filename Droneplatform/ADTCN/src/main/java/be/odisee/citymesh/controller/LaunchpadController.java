package be.odisee.citymesh.controller;

import be.odisee.citymesh.entity.Launchpad;
import be.odisee.citymesh.repository.LaunchpadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/launchpads")
public class LaunchpadController {
à)
    @Autowired
    private LaunchpadRepository launchpadRepository;

    @GetMapping
    public List<Launchpad> getAllLaunchpads() {
        return launchpadRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Launchpad> getLaunchpadById(@PathVariable Integer id) {
        return launchpadRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/safe")
    public List<Launchpad> getSafeLaunchpads() {
        return launchpadRepository.findByIsSafe(true);
    }

    @PostMapping
    public Launchpad createLaunchpad(@RequestBody Launchpad launchpad) {
        return launchpadRepository.save(launchpad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Launchpad> updateLaunchpad(@PathVariable Integer id, @RequestBody Launchpad launchpadDetails) {
        return launchpadRepository.findById(id)
                .map(launchpad -> {
                    launchpad.setName(launchpadDetails.getName());
                    launchpad.setLatitude(launchpadDetails.getLatitude());
                    launchpad.setLongitude(launchpadDetails.getLongitude());
                    launchpad.setIsSafe(launchpadDetails.getIsSafe());
                    return ResponseEntity.ok(launchpadRepository.save(launchpad));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLaunchpad(@PathVariable Integer id) {
        if (launchpadRepository.existsById(id)) {
            launchpadRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

