@US010
Feature: Kritieke batterijwaarschuwing

  Scenario: Waarschuwing wanneer batterij onder 20% zakt
    Given een drone in vlucht met batterijniveau 25
    When het batterijniveau daalt naar 19
    Then ontvangt de piloot een kritieke batterijwaarschuwing
