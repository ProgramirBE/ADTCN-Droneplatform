package be.odisee.citymesh.service;

import be.odisee.citymesh.domain.Drone;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Implementatie van {@link BatteryConsumptionStrategy} waarbij een energiezuinige
 * batterijverbruikstrategie wordt toegepast.
 * <p>
 * In Eco-modus wordt per afstandseenheid slechts 1% batterij verbruikt,
 * wat de voorkeur heeft bij lange vluchten of lage prioriteit.
 * </p>
 */
@Service
@Qualifier("ecoModeStrategy")
public class EcoModeStrategy implements BatteryConsumptionStrategy {

    /**
     * Berekent het batterijverbruik in eco-modus.
     * <p>
     * Voor elke eenheid afstand wordt 1% batterij afgetrokken.
     * </p>
     *
     * @param drone    de drone waarvoor het verbruik berekend wordt
     * @param distance de afstand die gevlogen wordt
     * @return het batterijverbruik (1% per eenheid afstand)
     */
    @Override
    public int calculateConsumption(Drone drone, int distance) {
        return distance * 1; // 1% batterij per afstandseenheid
    }
}
