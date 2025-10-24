Feature: Drone onderdelen bestelling testen
  Scenario: Nieuwe order start verwerking
    Given het systeem is in status "Initial"
    When de verwerking wordt uitgevoerd
    Then moet de orderstatus veranderen naar "In Behandeling"
    And er moet een tijdsstempel worden geregistreerd

  Scenario: Order succesvol gevalideerd
    Given de order is in status "In Behandeling"
    When de validatie wordt uitgevoerd
    Then moet de order klaar zijn voor controle
    And alle vereiste velden moeten correct zijn ingevuld

  Scenario: Klant annuleert order
    Given de order is in status "In Behandeling"
    When de bestelling wordt geanulleerd
    Then moet de orderstatus veranderen naar "Geannuleerd"
    And er moet een annuleringsbericht worden verstuurd

  Scenario: Order gecontroleerd en goedgekeurd
    Given de order is klaar voor controle
    When de bestelling als gecontroleerd wordt markeerd
    And de status word aangepast
    Then moet de order klaar zijn voor verzending

  Scenario: Order wordt verzonden
    Given de order is goedgekeurd voor verzending
    When het order word verzonder
    And de status word aangepast
    Then moet de orderstatus veranderen naar "Verzonden"
    And er moet een trackingnummer worden gegenereerd

  Scenario: Levering succesvol bevestigd
    Given de order is in status "Verzonden"
    When de levering word bevestigd
    And de status word aangepast
    Then moet de orderstatus veranderen naar "Afgerond"
    And de klant moet een bevestigingsmail ontvangen

  Scenario: Orderproces voltooid
    Given de order is in status "Afgerond"
    When het eindproces wordt uitgevoerd
    Then moet de orderstatus veranderen naar "Final"
    And alle resources moeten worden vrijgegeven

  Scenario: Ordervalidatie mislukt
    Given de order is in status "In Behandeling"
    When de controle gevalideerd wordt
    And er ontbreken verplichte gegevens
    Then moet de order in "In Behandeling" blijven
    And er moet een foutmelding worden gegenereerd