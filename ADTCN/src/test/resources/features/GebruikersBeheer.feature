@US009
Feature: Gebruikersbeheer - rollen toewijzen

  Scenario: Administrator maakt gebruiker en wijst rol toe
    Given de administrator is ingelogd
    When de administrator maakt een nieuwe gebruiker "jan" en kent de rol "Piloot" toe
    Then bestaat de gebruiker "jan" met de rol "Piloot"
    And de rollen omvatten "Piloot", "Mechanieker", "Administrator"
