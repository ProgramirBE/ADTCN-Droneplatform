Feature: Startplaats reserveren
  Om conflicten te vermijden
  Als piloot
  Wil ik niet twee keer dezelfde startplaats kunnen reserveren

  # Geen UI-termen (knoppen, ids, css) in Gherkin -> WHAT, not HOW
  # Testdata/driver komt uit hooks (zie verder)

  Background:
    Given het systeem staat in een gekende beginsituatie
    And ik bevind me op de homepagina

  Rule: Dubbele boeking wordt voorkomen
    @ui @pageobjects @business-rule
    Scenario: Dezelfde startplaats twee keer proberen te reserveren
      Given er bestaat een beschikbare startplaats "TEST-SPOT-01"
      When ik die startplaats reserveer
      Then de reservatie is bevestigd
      When ik dezelfde startplaats opnieuw reserveer
      Then zie ik een fout dat dubbele boekingen niet toegestaan zijn
