package be.odisee.citymesh.stepdefinitions;

import be.odisee.citymesh.domain.Drone;
import be.odisee.citymesh.domain.Melding;
import be.odisee.citymesh.service.DroneService;
import be.odisee.citymesh.service.impl.DroneServiceImpl;
import io.cucumber.java.nl.Gegeven;
import io.cucumber.java.nl.Als;
import io.cucumber.java.nl.Dan;
import org.jetbrains.annotations.NotNull;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DroneStatusSteps {
    private Drone drone;
    private DroneService droneService = new DroneServiceImpl();

    @Gegeven("een drone met status {string}")
    public void een_drone_met_status(@NotNull String status) {
        Drone.DroneStatus droneStatus = Drone.DroneStatus.valueOf(status.toUpperCase());
        drone = new Drone(droneStatus, "Locatie 1", Drone.DroneType.QUADCOPTER, 100);
    }

    @Als("de piloot een startcommand geeft")
    public void de_piloot_een_startcommand_geeft() {
        droneService.reserveerDrone(drone);
        droneService.startDroneVlucht(drone);
    }

    @Als("de drone landt op een geautoriseerde locatie")
    public void de_drone_landt_op_een_geautoriseerde_locatie() {
        droneService.resetDroneStatus(drone);
    }

    @Als("de batterij bijna leeg is")
    public void de_batterij_bijna_leeg_is() {
        droneService.startCharging(drone.getDroneID());
    }

    @Dan("moet de drone-status veranderen naar {string}")
    public void verandert_de_status_van_de_drone_naar(@NotNull String verwachteStatus) {
        assertEquals(Drone.DroneStatus.valueOf(verwachteStatus.toUpperCase()), drone.getStatus());
    }

    @Als("de batterij {int}% bereikt")
    public void deBatterijBereikt(int charge) {
        droneService.chargeBatterij(drone.getDroneID());
        if (drone.getBatterij() != charge){
            drone.setStatus(Drone.DroneStatus.DEFECT);
        }
        //System.out.println(drone.getBatterij());
    }
}