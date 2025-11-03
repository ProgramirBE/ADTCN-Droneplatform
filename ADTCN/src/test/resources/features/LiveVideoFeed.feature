@US008
Feature: Live videofeed bekijken

  Scenario: Crowdmanager bekijkt live videofeed met max 2s vertraging
    Given een actieve drone met live videofeed
    When ik de live videofeed open
    Then zie ik de videofeed in real-time
    And is de latentie maximaal 2 seconden
