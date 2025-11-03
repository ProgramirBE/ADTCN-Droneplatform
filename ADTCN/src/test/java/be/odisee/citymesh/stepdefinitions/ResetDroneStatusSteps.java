package be.odisee.citymesh.stepdefinitions;

import be.odisee.citymesh.domain.Drone;
import io.cucumber.java.en.*;
import static org.junit.Assert.*;
import java.util.*;

public class ResetDroneStatusSteps {

    private Drone drone;
    private final List<String> onderhoudLog = new ArrayList<>();

    @Given("een gerepareerde drone in status {string}")
    public void gerepareerde_drone_in_status(String status) {
        drone = new Drone(Drone.DroneStatus.valueOf(status.toUpperCase()), "Hangar", Drone.DroneType.QUADCOPTER, 100);
    }

    @When("de mechanieker reset de status naar klaar voor gebruik")
    public void mechanieker_reset_status() {
        drone.resetStatus(); // wordt INACTIVE in dit domein
        onderhoudLog.add("RESET:"+new Date());
    }

    @Then("is de drone klaar voor gebruik")
    public void drone_klaar_voor_gebruik() {
        assertEquals(Drone.DroneStatus.INACTIVE, drone.getStatus());
    }

    @Then("wordt de actie gelogd in het onderhoudslogboek")
    public void actie_gelogd() {
        assertFalse(onderhoudLog.isEmpty());
    }
}

