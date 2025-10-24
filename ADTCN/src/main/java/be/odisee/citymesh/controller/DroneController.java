package be.odisee.citymesh.controller;

import be.odisee.citymesh.domain.Drone;
import be.odisee.citymesh.domain.Drone.DroneStatus;
import be.odisee.citymesh.domain.Melding;
import be.odisee.citymesh.domain.Melding.MeldingStatus;
import be.odisee.citymesh.domain.Reservatie;
import be.odisee.citymesh.service.BatteryConsumptionStrategy;
import be.odisee.citymesh.service.DroneService;
import be.odisee.citymesh.service.ReservatieService;
import be.odisee.citymesh.service.impl.MeldingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class DroneController {
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
    public String defaultMapping(){
        return "login";
    }

    @GetMapping("/drones")
    public String showAvailableDrones(Model model) {
        List<Drone> drones = droneServiceImpl.getAllDrones();
        model.addAttribute("drones", drones);
        return "drones";
    }

    @GetMapping("/drones/nieuw")
    public String showNieuwDronesForm(Model model) {
        model.addAttribute("drone", new Drone(
                DroneStatus.INACTIVE, "", Drone.DroneType.QUADCOPTER, 100)
        );
        return "droneFormulier";
    }

    @PostMapping("/drones/edit")
    public String updateDrone(@ModelAttribute Drone drone) {
        droneServiceImpl.updateDrone(drone);
        return "redirect:/drones";
    }

    @PostMapping("/drones")
    public String createNewDrone(@ModelAttribute Drone drone) {
        droneServiceImpl.saveDrone(drone);
        return "redirect:/drones";
    }

    @PostMapping("drones/delete/{id}")
    public String deleteDrone(@PathVariable int id) {
        droneServiceImpl.deleteDrone(id);
        return "redirect:/drones";
    }

    // drone resetten
    @GetMapping("/drones/reset")
    public String showResetPage(Model model) {
        List<Drone> alleDrones = droneServiceImpl.getAllDrones();
        List<Drone> defectDrones = alleDrones.stream()
                .filter(drone -> drone.getStatus() == DroneStatus.DEFECT)
                .toList();
        model.addAttribute("defectDrones", defectDrones);
        return "reset-drone";
    }

    @PostMapping("/drones/reset")
    public String resetDrone(@RequestParam("droneId") int droneId) {
        Drone drone = droneServiceImpl.getDroneById(droneId);
        if (drone != null) {
            droneServiceImpl.resetDroneStatus(drone);
            droneServiceImpl.updateDrone(drone);
        }
        return "redirect:/drones";
    }

    // drone markeren voor depot
    @GetMapping("/drones/depot")
    public String showDepotPage(Model model) {
        List<Drone> alleDrones = droneServiceImpl.getAllDrones();
        List<Drone> defecteDrones = alleDrones.stream()
                .filter(drone -> drone.getStatus() == DroneStatus.DEFECT)
                .toList();
        model.addAttribute("drones", defecteDrones);
        return "send-to-depot";
    }

    @PostMapping("/drones/depot")
    public String sendToDepot(@RequestParam("droneId") int droneId) {
        Drone drone = droneServiceImpl.getDroneById(droneId);
        if (drone != null) {
            Melding melding = new Melding(
                    1,
                    "Te ernstige schade, depot vereist",
                    new Date(),
                    MeldingStatus.MAINTENANCE_REQUIRED
            );
            droneServiceImpl.startReparatie(drone, melding);
        }
        return "redirect:/drones";
    }

    // Drone update
    @GetMapping("/drones/edit/{id}")
    public String showEditDroneForm(@PathVariable int id, Model model) {
        Drone drone = droneServiceImpl.getDroneById(id);
        if (drone == null) {
            return "redirect:/drones";
        }
        model.addAttribute("drone", drone);
        return "droneEditFormulier";
    }

    @PostMapping("/api/drone/fly/{id}")
    @ResponseBody
    public Map<String, Integer> flyDroneAndGetBattery(@PathVariable int id,
                                                      @RequestBody Map<String, Object> payload) {
        Drone drone = droneServiceImpl.getDroneById(id);
        if (drone == null) {
            return Map.of("batterij", -1);
        }

        // Haal de afstand op uit de payload
        // Je kunt dit aanpassen naar hoe je afstand wilt bepalen per cel
        int distance = (Integer) payload.getOrDefault("distance", 10);
        String strategyName = (String) payload.get("strategy");

        BatteryConsumptionStrategy strategyToUse;
        if ("turboModeStrategy".equals(strategyName)) {
            strategyToUse = turboModeStrategy;
        } else {
            strategyToUse = ecoModeStrategy; // Default eco mode
        }

        droneServiceImpl.flyDrone(drone, distance, strategyToUse); // Roep flyDrone met de gekozen strategie aan

        return Map.of("batterij", drone.getBatterij());
    }

    @PostMapping("/api/drone/status/{id}")
    @ResponseBody // Dit vertelt spring dat het data rechtstreeks stuurt, niet een view
    public String updateDroneStatus(@PathVariable int id, @RequestBody Map<String, String> payload, HttpSession session) {
        Drone drone = droneServiceImpl.getDroneById(id);
        if (drone == null) {
            return "Drone not found";
        }

        String statusString = payload.get("status");
        try {
            DroneStatus newStatus = DroneStatus.valueOf(statusString.toUpperCase());
            drone.setStatus(newStatus);
            droneServiceImpl.updateDrone(drone);

            // Als de vlucht voltooid is, update ook de reservatie status
            if (newStatus == DroneStatus.INACTIVE) {
                Integer reservatieId = (Integer) session.getAttribute("currentReservatieId");
                if (reservatieId != null) {
                    Reservatie reservatie = reservatieService.getReservatieById(reservatieId);
                    if (reservatie != null) {
                        reservatie.setStatus(Reservatie.ReservatieStatus.VOLTOOID);
                        reservatieService.updateReservatie(reservatie);
                    }
                    session.removeAttribute("currentReservatieId");
                }
            }
            return "Drone " + id + " status updated to " + newStatus.name();
        } catch (IllegalArgumentException e) {
            return "Invalid status: " + statusString;
        }
	}


    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard(Model model) {
        model.addAttribute("welcomeMessage", "Welkom, Admin!");
        return "adminDashboard";
    }
}
