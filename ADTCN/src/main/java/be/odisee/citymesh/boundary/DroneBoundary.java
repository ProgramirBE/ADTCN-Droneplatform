package be.odisee.citymesh.boundary;

import be.odisee.citymesh.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * De {@code DroneBoundary}-klasse fungeert als toegangspunt tot de {@code DroneService}-laag.
 * <p>
 * Deze component wordt beheerd door Spring en toont hoe services via dependency injection
 * kunnen worden gebruikt binnen een boundary-klasse.
 * </p>
 *
 * <p>
 * Momenteel bevat de klasse een voorbeeldmethode ter illustratie van de injectie.
 * </p>
 *
 */
@Component
public class DroneBoundary {

    private final DroneService droneService;

    /**
     * Constructor die {@code DroneService} injecteert via Spring.
     *
     * @param droneService de serviceklasse die drone-functionaliteit aanbiedt.
     */
    @Autowired
    public DroneBoundary(DroneService droneService) {
        this.droneService = droneService;
    }

    /**
     * Voorbeeldmethode die toont of {@code DroneService} succesvol geïnjecteerd is.
     * <p>
     * Deze methode print naar de console of de {@code droneService} null is of niet.
     * </p>
     */
    public void testService() {
        System.out.println("DroneService is injected: " + (droneService != null));
    }
}
