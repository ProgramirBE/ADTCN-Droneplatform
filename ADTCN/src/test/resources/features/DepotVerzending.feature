@US005
Feature: Drone markeren voor verzending naar depot

  Scenario: Mechanieker markeert drone voor verzending
    Given een defecte drone met status "Defect"
    When de mechanieker de drone markeert voor verzending naar het depot
    Then moet de status van de drone veranderen naar "Reserved"
