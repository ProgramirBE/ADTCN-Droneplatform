# language: nl
Feature: Startplaats reserveren
  Om conflicten te vermijden
  Als piloot
  Wil ik niet twee keer dezelfde startplaats kunnen reserveren

  # Geen UI-termen (knoppen, ids, css) in Gherkin -> WHAT, not HOW
  Background:
    Given het systeem staat in een gekende beginsituatie
    And ik bevind me op de homepagina

  Rule: Lijst van startplaatsen is zichtbaar en begrijpelijk
    @ui @pageobjects
    Scenario: Startplaatsenoverzicht tonen voor een drone
      Given ik kies een bestaande drone
      When ik de startplaatsen voor die drone bekijk
      Then zie ik minstens één startplaats in de lijst
      And elke getoonde startplaats vermeldt de beschikbaarheid en afstand tot no-fly zones

    @ui @pageobjects
    Scenario Outline: Beschikbare startplaats reserveren
      Given er bestaat een beschikbare startplaats "<spotId>"
      When ik die startplaats reserveer
      Then de reservatie is bevestigd

      Examples:
        | spotId        |
        | TEST-SPOT-01  |
        | TEST-SPOT-02  |
        | SP-101        |

  Rule: Dubbele boeking wordt voorkomen
    @ui @pageobjects @business-rule
    Scenario: Dezelfde startplaats twee keer proberen te reserveren
      Given er bestaat een beschikbare startplaats "TEST-SPOT-01"
      When ik die startplaats reserveer
      Then de reservatie is bevestigd
      When ik dezelfde startplaats opnieuw reserveer
      Then zie ik een fout dat dubbele boekingen niet toegestaan zijn

    @ui @pageobjects
    Scenario: Reservatie op reeds bezette startplaats wordt geweigerd
      Given er bestaat een gereserveerde startplaats "SP-202"
      When ik probeer die startplaats te reserveren
      Then zie ik een fout dat dubbele boekingen niet toegestaan zijn

  Rule: Veiligheidsregels rond no-fly zones
    # Business Rules Only (geen UI-termen) — check van de afstandsregel
    @bro @business-rule
    Scenario Outline: Enkel startplaatsen op veilige afstand zijn reserveerbaar
      Given er bestaat een startplaats "<veiligId>" die beschikbaar is en <afstandVeilig> meter van de dichtste no-fly zone ligt
      And er bestaat een startplaats "<onveiligId>" die beschikbaar is en <afstandOnveilig> meter van de dichtste no-fly zone ligt
      When de piloot de startplaats "<veiligId>" reserveert
      Then markeert het systeem "<veiligId>" als gereserveerd
      When de piloot probeert de startplaats "<onveiligId>" te reserveren
      Then wordt die reservatie geweigerd wegens onvoldoende afstand tot no-fly zones

      Examples:
        | veiligId | afstandVeilig | onveiligId | afstandOnveilig |
        | SP-301   | 80            | SP-399     | 30              |
        | SP-401   | 120           | SP-498     | 10              |

  Rule: Tijdelijkheid en verval van reservaties
    # Optioneel – als je een vervaltijd modelleert in je BRO/DOM
    @bro @business-rule
    Scenario: Een voorlopige reservatie vervalt na de ingestelde tijd
      Given er bestaat een beschikbare startplaats "SP-777" met een vervaltijd van 5 minuten
      When ik die startplaats reserveer
      And er verstrijken 5 minuten
      Then is de startplaats opnieuw beschikbaar voor andere piloten
