Feature: Drone Status Overgangen

  Scenario: Drone gaat van Inactive naar InFlight
    Given een drone met status "Inactive"
    When de piloot een startcommand geeft
    Then moet de drone-status veranderen naar "InFlight"

  Scenario: Drone landt veilig en keert terug naar Inactive
    Given een drone met status "InFlight"
    When de drone landt op een geautoriseerde locatie
    Then moet de drone-status veranderen naar "Inactive"

  Scenario: Drone schakelt over naar Charging na low battery
    Given een drone met status "InFlight"
    When de batterij bijna leeg is
    Then moet de drone-status veranderen naar "Charging"

  Scenario: Drone volledig opgeladen
    Given een drone met status "Charging"
    When de batterij 100% bereikt
    Then moet de drone-status veranderen naar "Inactive"