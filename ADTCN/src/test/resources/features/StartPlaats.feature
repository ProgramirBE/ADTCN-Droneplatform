Feature: Startplaatsen bekijken en reserveren

  # US002: Als piloot kan ik beschikbare startplaatsen zien en reserveren,
  # zodat ik mijn drone kan lanceren vanaf een vrije en veilige locatie.

  Scenario: Piloot ziet beschikbare startplaatsen na inloggen
    Given ik ben op de loginpagina
    When ik mij inlog met gebruikersnaam "citymeshuser" en wachtwoord "citymeshpwd"
    And ik ga naar de pagina met startplaatsen en kies een startplaats
    Then zie ik een lijst van beschikbare startplaatsen

  Scenario: Piloot reserveert een beschikbare startplaats
    Given ik ben op de loginpagina
    When ik mij inlog met gebruikersnaam "citymeshuser" en wachtwoord "citymeshpwd"
    And ik ga naar de pagina met startplaatsen en kies een startplaats
    And ik klik op de reserveer-knop van de eerste beschikbare startplaats
    Then kom ik op de Mijn Reservatie pagina