Feature: Reserve a start place
  As a drone pilot
  I want to view and reserve an available start place
  So that I can launch my drone from a safe location

  Background:
    Given the system is in a known initial state

  Rule: A start place is reserveable only if it is available and safely distant

    @bro @business-rule
    Scenario: Pilot reserves an available start place that is >=50m from no-fly zones
      Given a start place "SP-101" exists that is available and 60m from the nearest no-fly zone
      And another start place "SP-102" exists that is reserved
      When the pilot reserves start place "SP-101"
      Then the system marks "SP-101" as reserved

    @bro @business-rule
    Scenario Outline: Multiple valid reservations at safe distance
      Given a start place "<availableId>" exists that is available and <distance>m from the nearest no-fly zone
      And another start place "<reservedId>" exists that is reserved
      When the pilot reserves start place "<availableId>"
      Then the system marks "<availableId>" as reserved

      Examples:
        | availableId | distance | reservedId |
        | SP-201      | 50       | SP-202     |
        | SP-301      | 80       | SP-399     |
        | SP-401      | 120      | SP-498     |

  Rule: Safety distance is enforced (no reservation below 50m)

    @bro @business-rule
    Scenario Outline: Reservation is rejected when the start place is too close to no-fly zones
      Given a start place "<tooCloseId>" exists that is available and <distance>m from the nearest no-fly zone
      When the pilot reserves start place "<tooCloseId>"
      Then the reservation for "<tooCloseId>" is rejected due to insufficient safety distance

      Examples:
        | tooCloseId | distance |
        | SP-910     | 30       |
        | SP-911     | 10       |
        | SP-912     | 49       |
