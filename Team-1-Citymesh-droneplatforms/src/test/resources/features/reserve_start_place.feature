Feature: Reserve a start place
  As a drone pilot
  I want to view and reserve an available start place
  So that I can launch my drone from a safe location

  Scenario: Pilot reserves an available start place that is >=50m from no-fly zones
    Given a start place "SP-101" exists that is available and 60m from the nearest no-fly zone
    And another start place "SP-102" exists that is reserved
    When the pilot reserves start place "SP-101"
    Then the system marks "SP-101" as reserved
