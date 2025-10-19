package be.odisee.citymesh.controller;

import be.odisee.citymesh.domain.Drone;
import be.odisee.citymesh.domain.Melding;
import be.odisee.citymesh.domain.Reservatie;
import be.odisee.citymesh.service.BatteryConsumptionStrategy;
import be.odisee.citymesh.service.DroneService;
import be.odisee.citymesh.service.MeldingService;
import be.odisee.citymesh.service.ReservatieService;
import be.odisee.citymesh.service.impl.MeldingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class VluchtController {
    @Autowired
    private DroneService droneServiceImpl;

    @Autowired
    private ReservatieService reservatieService;

    @Autowired
    private MeldingService meldingServiceImpl;

    // vanuit de reservaties naar de vlucht simulatie
    @PostMapping("/start-vlucht-simulatie")
    public String startVluchtSimulatie(
            @RequestParam("reservatieId") int reservatieId,
            @RequestParam("droneId") int droneId,
            @RequestParam("locatie") String locatie,
            @RequestParam("consumptionStrategy") String consumptionStrategy,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        Reservatie reservatie = reservatieService.getReservatieById(reservatieId);
        Drone drone = droneServiceImpl.getDroneById(droneId);

        if (reservatie != null && drone != null) {
            reservatie.setStatus(Reservatie.ReservatieStatus.ACTIEF);
            reservatieService.updateReservatie(reservatie);

            droneServiceImpl.reserveerDrone(drone);
            droneServiceImpl.updateDrone(drone);

            int gridSize = 5;
            int[][] matrix = droneServiceImpl.genereerMatrix(gridSize);

            session.setAttribute("lastSimulatedMatrix", matrix);
            session.setAttribute("lastSimulatedGridSize", gridSize);
            session.setAttribute("currentReservatieId", reservatieId);

            redirectAttributes.addFlashAttribute("selectedDroneId", droneId);
            redirectAttributes.addFlashAttribute("matrix", matrix);
            redirectAttributes.addFlashAttribute("locatie", locatie);
            redirectAttributes.addFlashAttribute("chosenStrategyName", consumptionStrategy);

            return "redirect:/vlucht-simulatie";
        }
        return "redirect:/reservaties";
    }

    @GetMapping("/vlucht-simulatie")
    public String toonVluchtSimulatie(Model model, @ModelAttribute("selectedDroneId") Integer droneId,
                                      @ModelAttribute("matrix") int[][] matrix,
                                      @ModelAttribute("locatie") String locatie,
                                      @ModelAttribute("chosenStrategyName") String chosenStrategyName) { // NIEUW

        if (droneId == null || matrix == null || chosenStrategyName == null) { // Controleer ook de strategie
            return "redirect:/drones";
        }

        Drone selectedDrone = droneServiceImpl.getDroneById(droneId);
        if (selectedDrone != null) {
            droneServiceImpl.startDroneVlucht(selectedDrone);
            droneServiceImpl.updateDrone(selectedDrone);

            model.addAttribute("selectedDrone", selectedDrone);
            model.addAttribute("matrix", matrix);
            model.addAttribute("gridSize", matrix.length);
            model.addAttribute("locatie", locatie);
            model.addAttribute("chosenStrategyName", chosenStrategyName); // Geef door aan de view
            return "vluchtSimulatie";
        }

        return "redirect:/drones";
    }

    @GetMapping("/matrix-meldingen")
    public String showMatrixMeldingen(Model model, HttpSession session,
                                      @RequestParam(value = "overcrowdLimit", defaultValue = "45") int overcrowdLimit) {

        // Probeer de matrix en gridSize uit de sessie te halen
        int[][] matrix = (int[][]) session.getAttribute("lastSimulatedMatrix");
        Integer gridSizeObj = (Integer) session.getAttribute("lastSimulatedGridSize");

        if (matrix == null || gridSizeObj == null) {
            model.addAttribute("errorMessage", "Geen recente gesimuleerde matrix gevonden. Start eerst een vlucht.");
            return "drones";
        }

        int gridSize = gridSizeObj;

        // Genereer meldingen op basis van de opgehaalde matrix en de limiet
        List<Melding> overcrowdingMeldingen = meldingServiceImpl.generateOvercrowdingReports(matrix, overcrowdLimit);

        // Voeg de meldingen en de matrix toe aan het model
        model.addAttribute("meldingen", overcrowdingMeldingen);
        model.addAttribute("matrix", matrix);
        model.addAttribute("gridSize", gridSize);
        model.addAttribute("overcrowdLimit", overcrowdLimit);

        return "matrixMeldingen";
    }

    // beschikbare startplaatsen tonen
    @GetMapping("/launchpads")
    public String showLaunchpads(@RequestParam(value = "droneId", required = false) Integer droneId, Model model) {
        List<String> pads = List.of("Park Zuid", "Rooftop Central", "Havengebied");
        model.addAttribute("launchpads", pads);
        if (droneId != null) {
            Drone selectedDrone = droneServiceImpl.getDroneById(droneId);
            if (selectedDrone != null) {
                model.addAttribute("selectedDrone", selectedDrone);
            }
        }
        return "launchpads";
    }
}
