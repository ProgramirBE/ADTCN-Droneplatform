@US004
Feature: Reset drone status na reparatie

  Scenario: Mechanieker reset status naar klaar voor gebruik en logt de actie
    Given een gerepareerde drone in status "Maintenance"
    When de mechanieker reset de status naar klaar voor gebruik
    Then is de drone klaar voor gebruik
    And wordt de actie gelogd in het onderhoudslogboek
