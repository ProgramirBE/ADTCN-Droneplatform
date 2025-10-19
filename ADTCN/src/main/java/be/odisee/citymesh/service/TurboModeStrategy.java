package be.odisee.citymesh.service;

import be.odisee.citymesh.domain.Drone;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Implementatie van {@link BatteryConsumptionStrategy} waarbij een intensief energieverbruik
 * wordt toegepast tijdens het vliegen van een drone.
 * <p>
 * In Turbo-modus wordt per afstandseenheid 3% batterij verbruikt,
 * geschikt voor dringende of snelle opdrachten.
 * </p>
 */
@Service
@Qualifier("turboModeStrategy")
public class TurboModeStrategy implements BatteryConsumptionStrategy {

    /**
     * Berekent het batterijverbruik in turbo-modus.
     * <p>
     * Voor elke eenheid afstand wordt 3% batterij verbruikt.
     * </p>
     *
     * @param drone    de drone waarvoor het verbruik berekend wordt
     * @param distance de afstand die gevlogen wordt
     * @return het batterijverbruik (3% per eenheid afstand)
     */
    @Override
    public int calculateConsumption(Drone drone, int distance) {
        return distance * 3; // 3% batterij per afstandseenheid
    }
}
