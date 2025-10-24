package be.odisee.citymesh;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Algemene configuratieklasse voor de Spring-context van de Citymesh-toepassing.
 * <p>
 * Deze klasse:
 * <ul>
 *   <li>markeert de toepassing als een Spring-configuratiebron</li>
 *   <li>activeert component scanning in het pakket {@code be.odisee.citymesh}</li>
 *   <li>schakelt ondersteuning in voor AspectJ AOP-proxy's</li>
 * </ul>
 */
@Configuration
@ComponentScan("be.odisee.citymesh")
@EnableAspectJAutoProxy
public class AppConfig {
    // Geen extra configuratie nodig: Spring zoekt en beheert beans automatisch
}
