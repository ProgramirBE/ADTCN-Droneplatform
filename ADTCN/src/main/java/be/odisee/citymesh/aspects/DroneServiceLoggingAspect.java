package be.odisee.citymesh.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

/**
 * Aspect voor het loggen van methodestarts en -eindes binnen de {@code DroneService}-klasse.
 * <p>
 * Deze klasse maakt gebruik van Spring AOP (Aspect-Oriented Programming) om
 * logging toe te voegen voor elke methode in de {@code DroneService}-klasse.
 * De logging gebeurt vóór en na de uitvoering van de methode.
 *
 * <p>
 * Opmerking: de logging zelf is momenteel gecommentarieerd, maar de hooks zijn aanwezig.
 * </p>
 *
 */
@Aspect
@Component
public class DroneServiceLoggingAspect {

    /**
     * Wordt uitgevoerd vóór elke methode in {@code DroneService}.
     *
     * @param joinPoint bevat informatie over het methodepunt van uitvoering, zoals de methodenaam.
     */
    @Before("execution(* be.odisee.citymesh.service.DroneService.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        // System.out.println("[AOP] Start methode: " + joinPoint.getSignature().getName());
    }

    /**
     * Wordt uitgevoerd na elke methode in {@code DroneService}.
     *
     * @param joinPoint bevat informatie over het methodepunt van uitvoering, zoals de methodenaam.
     */
    @After("execution(* be.odisee.citymesh.service.DroneService.*(..))")
    public void logAfterMethod(JoinPoint joinPoint) {
        // System.out.println("[AOP] Einde methode: " + joinPoint.getSignature().getName());
    }
}
