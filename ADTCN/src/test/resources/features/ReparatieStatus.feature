Feature: Drone Reparatie

  Scenario: Drone wordt succesvol gerepareerd
    Given een defecte drone met status "Defect"
    When de mechanieker de drone inspecteert en repareert
    Then moet de status van de drone veranderen naar "Inactive"

  Scenario: Reparatie mislukt door ontbrekende onderdelen
    Given een defecte drone met status "Defect"
    And er zijn geen vervangende onderdelen beschikbaar
    When de mechanieker een reparatie wil uitvoeren
    Then moet het systeem een foutmelding geven: "Reparatie niet mogelijk"
    And de status van de drone blijft "Defect"

  Scenario: Drone moet naar depot worden gestuurd
    Given een defecte drone met status "Defect"
    And de mechanieker beoordeelt dat reparatie ter plaatse niet mogelijk is
    When de mechanieker de drone markeert voor verzending naar het depot
    Then moet de status van de drone veranderen naar "Reserved"