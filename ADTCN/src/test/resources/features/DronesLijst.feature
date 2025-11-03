@US001
Feature: Drones lijst met statuskolom

  Scenario: Overzicht toont statuskolom met toegestane waarden
    Given er bestaan drones met verschillende statussen
    When ik de lijst van drones opvraag
    Then bevat de lijst een kolom "Status"
    And elke statuswaarde is een van "Vliegklaar", "In Onderhoud", "In Gebruik"
