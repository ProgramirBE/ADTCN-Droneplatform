@US003
Feature: Checkpoints op de kaart

  Scenario: Piloot plaatst handmatig een checkpoint pin
    Given een kaart is geopend voor de vlucht
    When ik een checkpoint pin plaats op positie 10,20
    Then verschijnt er een checkpoint pin op positie 10,20
