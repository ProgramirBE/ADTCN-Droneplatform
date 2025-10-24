package be.odisee.citymesh.service;

import be.odisee.citymesh.domain.Drone;

/**
 * Interface voor het implementeren van verschillende strategieën om het batterijverbruik
 * van een {@link Drone} te berekenen tijdens een vlucht.
 * <p>
 * Deze strategie maakt het mogelijk om afhankelijk van de context (zoals Eco- of Turbo-modus)
 * andere berekeningen toe te passen zonder de hoofdlogica van het systeem te wijzigen (Strategy Pattern).
 */
public interface BatteryConsumptionStrategy {

    /**
     * Berekent het batterijverbruik van een drone op basis van afstand en
     * specifieke strategielogica (bv. eco of turbo).
     *
     * @param drone   de {@link Drone} waarvoor het verbruik berekend wordt
     * @param distance de afgelegde afstand in meters of logische eenheden
     * @return het aantal batterijpunten dat verbruikt wordt
     */
    int calculateConsumption(Drone drone, int distance);
}
