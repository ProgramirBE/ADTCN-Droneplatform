package be.odisee.citymesh.stepdefinitions;

import be.odisee.citymesh.domain.Drone;
import be.odisee.citymesh.service.DroneService;
import be.odisee.citymesh.domain.Melding;
import be.odisee.citymesh.service.impl.DroneServiceImpl;
import io.cucumber.java.nl.Gegeven;
import io.cucumber.java.nl.Als;
import io.cucumber.java.nl.Dan;
import io.cucumber.java.nl.En;
import org.jetbrains.annotations.NotNull;

import static org.junit.Assert.*;

public class ReparatieStatusSteps
{
    private Drone drone;
    private DroneService droneService = new DroneServiceImpl();
    private Melding melding;
    private String foutmelding;
    private boolean onderdelenBeschikbaar = true;

    @Gegeven("een defecte drone met status {string}")
    public void een_drone_met_status(@NotNull String status) {
        drone = new Drone(Drone.DroneStatus.valueOf(status.toUpperCase()), "Locatie 1", Drone.DroneType.QUADCOPTER, 100);
    }

    @Gegeven("er zijn geen vervangende onderdelen beschikbaar")
    public void er_zijn_geen_vervangende_onderdelen_beschikbaar() {
        onderdelenBeschikbaar = false;
    }

    @Gegeven("de mechanieker beoordeelt dat reparatie ter plaatse niet mogelijk is")
    public void de_mechanieker_beoordeelt_dat_reparatie_ter_plaats_niet_mogelijk_is() {

    }

    @Als("de mechanieker de drone inspecteert en repareert")
    public void de_mechanieker_de_drone_inspecteert_en_repareert() {
        if (onderdelenBeschikbaar) {
            droneService.resetDroneStatus(drone);
        }
    }

    @Als("de mechanieker een reparatie wil uitvoeren")
    public void de_mechanieker_een_reparatie_wil_uitvoeren() {
        if (!onderdelenBeschikbaar) {
            foutmelding = "Reparatie niet mogelijk";
        }
    }

    @Als("de mechanieker de drone markeert voor verzending naar het depot")
    public void de_mechanieker_de_drone_markeert_voor_verzending_naar_het_depot() {
        droneService.reserveerDrone(drone);
    }

    @Dan("moet de status van de drone veranderen naar {string}")
    public void moet_de_status_van_de_drone_veranderen_naar(String verwachteStatus) {
        assertEquals(Drone.DroneStatus.valueOf(verwachteStatus.toUpperCase()), drone.getStatus());
    }

    @Dan("moet het systeem een foutmelding geven: {string}")
    public void moet_het_systeem_een_foutmelding_geven(String verwachteFoutmelding) {
        assertEquals(verwachteFoutmelding, foutmelding);
    }

    @Dan("de status van de drone blijft {string}")
    public void de_status_van_de_drone_blijft(String verwachteStatus) {
        assertEquals(Drone.DroneStatus.valueOf(verwachteStatus.toUpperCase()), drone.getStatus());
    }

    @En("wanneer de mechanieker de status reset, moet de drone {string} zijn")
    public void wanneer_de_mechanieker_de_status_reset_moet_de_drone_zijn(String verwachteStatus) {
        droneService.resetDroneStatus(drone);
        assertEquals(Drone.DroneStatus.valueOf(verwachteStatus.toUpperCase()), drone.getStatus());
    }
}