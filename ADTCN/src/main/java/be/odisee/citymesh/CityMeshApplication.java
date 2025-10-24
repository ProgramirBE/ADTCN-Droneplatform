package be.odisee.citymesh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Startpunt van de Spring Boot-toepassing voor Citymesh.
 * <p>
 * Deze klasse bootstrapt de volledige applicatie met behulp van {@code SpringApplication.run()}.
 * Door de {@link SpringBootApplication} annotatie:
 * <ul>
 *   <li>wordt component scanning automatisch ingeschakeld</li>
 *   <li>wordt auto-configuratie toegepast</li>
 *   <li>worden alle beans en services geïnitialiseerd</li>
 * </ul>
 */
@SpringBootApplication
public class CityMeshApplication {

    /**
     * De hoofdmethode die wordt uitgevoerd bij het opstarten van de Spring Boot-applicatie.
     *
     * @param args optionele command line argumenten
     */
    public static void main(String[] args) {
        SpringApplication.run(CityMeshApplication.class, args);
    }
}
