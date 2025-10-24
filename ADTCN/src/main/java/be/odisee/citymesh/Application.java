package be.odisee.citymesh;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import be.odisee.citymesh.boundary.DroneBoundary;

/**
 * Entry point voor de Citymesh Spring-toepassing.
 * <p>
 * Deze klasse:
 * <ul>
 *   <li>start een {@link AnnotationConfigApplicationContext} gebaseerd op de annotatie-configuratie</li>
 *   <li>scant het pakket {@code be.odisee.citymesh} op Spring-componenten</li>
 *   <li>haalt een {@link DroneBoundary} bean op en voert een testmethode uit</li>
 * </ul>
 */
@Configuration
@ComponentScan("be.odisee.citymesh")
public class Application {

    /**
     * De hoofdmethoden waarmee de Spring-toepassing gestart wordt.
     *
     * @param args eventuele argumenten bij het opstarten
     */
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        DroneBoundary droneBoundary = context.getBean(DroneBoundary.class);
        droneBoundary.testService(); // Output: bevestiging dat DroneService geïnjecteerd is
    }
}
