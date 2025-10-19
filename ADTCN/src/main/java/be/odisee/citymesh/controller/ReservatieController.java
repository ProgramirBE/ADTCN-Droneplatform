package be.odisee.citymesh.controller;

import be.odisee.citymesh.dao.ReservatieRepository;
import be.odisee.citymesh.domain.Drone;
import be.odisee.citymesh.domain.Reservatie;
import be.odisee.citymesh.service.DroneService;
import be.odisee.citymesh.service.ReservatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class ReservatieController {
    @Autowired
    private ReservatieService reservatieService;

    @Autowired
    private DroneService droneServiceImpl;

    // Toon reservaties
    @GetMapping("/reservaties")
    public String showReservaties(Model model, HttpSession session) {
        List<Reservatie> reservaties = reservatieService.getAllReservaties();
        for (Reservatie reservatie : reservaties) {
            Drone drone = droneServiceImpl.getDroneById(reservatie.getDroneId());
            if (drone != null) {
                reservatie.setDroneType(drone.getType());
            }
        }
        model.addAttribute("reservaties", reservaties);
        return "reservaties";
    }

    // status GERESERVEERD
    @PostMapping("/reserveer-startplaats")
    public String reserveerStartplaats(
            @RequestParam("droneId") int droneId,
            @RequestParam("locatie") String locatie,
            @RequestParam("consumptionStrategy") String consumptionStrategy,
            RedirectAttributes redirectAttributes) {

        Drone drone = droneServiceImpl.getDroneById(droneId);
        if (drone != null) {
            Reservatie reservatie = new Reservatie();
            reservatie.setDroneId(droneId);
            reservatie.setLocatie(locatie);
            reservatie.setConsumptionStrategy(consumptionStrategy);
            reservatie.setStatus(Reservatie.ReservatieStatus.GERESERVEERD);
            reservatie.setReservatieDatum(new Date());
            reservatieService.saveReservatie(reservatie);

            redirectAttributes.addFlashAttribute("successMessage",
                    "Reservatie succesvol aangemaakt voor drone " + droneId);
        }
        return "redirect:/reservaties";
    }

    // Annuleer reservatie
    @PostMapping("/annuleer-reservatie")
    public String annuleerReservatie(@RequestParam("reservatieId") int reservatieId) {
        Reservatie reservatie = reservatieService.getReservatieById(reservatieId);
        if (reservatie != null && reservatie.getStatus() == Reservatie.ReservatieStatus.GERESERVEERD) {
            reservatie.setStatus(Reservatie.ReservatieStatus.GEANNULEERD);
            reservatieService.updateReservatie(reservatie);
        }
        return "redirect:/reservaties";
    }
}
