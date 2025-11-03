@US007
Feature: Onderhoudslogboek per drone

  Scenario: Log-entry bevat datum, type en omschrijving
    Given een drone met id 101 heeft een onderhoudslogboek
    When ik voeg een onderhoudsentry toe met datum, type "Inspectie" en omschrijving "Propeller vervangen"
    Then verschijnt de entry in het log met datum, type en omschrijving
