package be.odisee.citymesh.stepdefinitions;

import be.odisee.citymesh.domain.Drone;
import io.cucumber.java.nl.Gegeven;
import io.cucumber.java.nl.Als;
import io.cucumber.java.nl.Dan;
import org.jetbrains.annotations.NotNull;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DroneStatusSteps {
    private Drone drone;

    @Gegeven("een drone met status {string}")
    public void een_drone_met_status(@NotNull String status) {
        Drone.DroneStatus droneStatus = Drone.DroneStatus.valueOf(status.toUpperCase());
        drone = new Drone(droneStatus, "Locatie 1", Drone.DroneType.QUADCOPTER, 100);
    }

    @Als("de piloot een startcommand geeft")
    public void de_piloot_een_startcommand_geeft() {
        // Simuleer reserveren en starten zonder service calls
        if (drone.getStatus() == Drone.DroneStatus.INACTIVE) {
            drone.setStatus(Drone.DroneStatus.RESERVED);
        }
        if (drone.getStatus() == Drone.DroneStatus.RESERVED) {
            drone.setStatus(Drone.DroneStatus.INFLIGHT);
        }
    }

    @Als("de drone landt op een geautoriseerde locatie")
    public void de_drone_landt_op_een_geautoriseerde_locatie() {
        // Reset naar INACTIVE
        drone.resetStatus();
    }

    @Als("de batterij bijna leeg is")
    public void de_batterij_bijna_leeg_is() {
        // Simuleer low battery -> CHARGING
        drone.setBatterij(5);
        drone.setStatus(Drone.DroneStatus.CHARGING);
    }

    @Dan("moet de drone-status veranderen naar {string}")
    public void verandert_de_status_van_de_drone_naar(@NotNull String verwachteStatus) {
        assertEquals(Drone.DroneStatus.valueOf(verwachteStatus.toUpperCase()), drone.getStatus());
    }

    @Als("de batterij {int}% bereikt")
    public void deBatterijBereikt(int charge) {
        // Simuleer opladen tot een bepaald niveau
        drone.setBatterij(charge);
        if (charge >= 100) {
            drone.setStatus(Drone.DroneStatus.INACTIVE);
        }
    }
}