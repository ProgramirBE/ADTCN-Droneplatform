package be.odisee.citymesh;

import be.odisee.citymesh.domain.Drone;
import be.odisee.citymesh.domain.Melding;
import be.odisee.citymesh.service.DroneService;
import be.odisee.citymesh.service.impl.DroneServiceImpl;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests voor de {@link DroneServiceImpl} klasse.
 * <p>
 * Deze testklasse controleert het gedrag van dronefuncties zoals opladen, reserveren,
 * statusovergangen en locatieverwerking.
 */
public class DroneServiceTest {

    private DroneService droneService;
    private Drone drone;
    private Melding melding;

    /**
     * Initialisatie van een nieuwe drone en een testmelding vóór elke test.
     */
    @BeforeEach
    void setUp() {
        droneService = new DroneServiceImpl(); // Let op: geen dependency injection hier
        drone = new Drone(Drone.DroneStatus.INACTIVE, "1,1", Drone.DroneType.QUADCOPTER, 100);
        melding = new Melding(1, "De drone is kapot", DateTime.now().toDate(), Melding.MeldingStatus.MAINTENANCE_REQUIRED);
    }

    /**
     * Test of een drone correct overschakelt naar CHARGING status.
     */
    @Test
    void testStartCharging() {
        drone.setStatus(Drone.DroneStatus.INACTIVE);
        droneService.startCharging(drone.getDroneID());
        assertEquals(Drone.DroneStatus.CHARGING, drone.getStatus());
    }

    /**
     * Test of een drone na het resetten weer INACTIVE wordt.
     */
    @Test
    void testChargingToInactive() {
        drone.setStatus(Drone.DroneStatus.CHARGING);
        droneService.resetDroneStatus(drone);
        assertEquals(Drone.DroneStatus.INACTIVE, drone.getStatus());
    }

    /**
     * Test of een gereserveerde drone succesvol in vluchtstatus gezet kan worden.
     */
    @Test
    void testStartDroneVlucht() {
        drone.setStatus(Drone.DroneStatus.RESERVED);
        droneService.startDroneVlucht(drone);
        assertEquals(Drone.DroneStatus.INFLIGHT, drone.getStatus());
    }

    /**
     * Test of een defecte drone na een onderhoudsmelding de MAINTENANCE status krijgt.
     */
    @Test
    void testInFlightToMaintenance() {
        drone.setStatus(Drone.DroneStatus.DEFECT);
        droneService.startReparatie(drone, melding);
        assertEquals(Drone.DroneStatus.MAINTENANCE, drone.getStatus());
    }

    /**
     * Test of een drone met MAINTENANCE status succesvol kan worden gereset.
     */
    @Test
    void testMaintenanceToInactive() {
        drone.setStatus(Drone.DroneStatus.MAINTENANCE);
        droneService.resetDroneStatus(drone);
        assertEquals(Drone.DroneStatus.INACTIVE, drone.getStatus());
    }

    /**
     * Test of een drone succesvol gereserveerd kan worden.
     */
    @Test
    void testReserveerDrone() {
        drone.setStatus(Drone.DroneStatus.INACTIVE);
        droneService.reserveerDrone(drone);
        assertEquals(Drone.DroneStatus.RESERVED, drone.getStatus());
    }

    /**
     * Test de algemene resetfunctionaliteit voor drones.
     */
    @Test
    void testResetDroneStatus() {
        drone.setStatus(Drone.DroneStatus.INFLIGHT);
        droneService.resetDroneStatus(drone);
        assertEquals(Drone.DroneStatus.INACTIVE, drone.getStatus());
    }

    /**
     * Test of de locatie van een drone correct wordt teruggegeven als deze in vlucht is.
     */
    @Test
    void testVerwerkDroneLocatie() {
        drone.setStatus(Drone.DroneStatus.INFLIGHT);
        drone.setLocatie("Brussel");
        assertEquals("Brussel", droneService.verwerkDroneLocatie(drone));
    }

    /**
     * Test of de locatie niet wordt teruggegeven als de drone niet in vlucht is.
     */
    @Test
    void testVerwerkDroneLocatie_NotInFlight() {
        drone.setStatus(Drone.DroneStatus.CHARGING);
        assertNull(droneService.verwerkDroneLocatie(drone));
    }
}
