@US002
Feature: Startplaatsen veiligheid criterium

  Scenario: Filteren op veilige startplaatsen (>= 50 meter)
    Given startplaatsen met afstanden tot no-fly zones
    When ik filter op veilige startplaatsen
    Then krijg ik enkel startplaatsen terug met afstand >= 50 meter
