Feature: De piloot voert acties uit op het systeem

Scenario: Piloot logt succesvol in
  Given een piloot heeft een geldige gebruikersnaam en wachtwoord
  When de piloot probeert in te loggen
  Then wordt de login gevalideerd
  And de piloot krijgt een bevestiging van succesvolle login

Scenario: Piloot vult een ongeldig verslag in
  Given de piloot is ingelogd
  And de piloot vult het verslag in met ongeldige gegevens
  When de piloot probeert het verslag te verzenden
  Then toont het systeem een foutmelding
  And de piloot krijgt de kans om de invoer te corrigeren

Scenario: Piloot dient een correct verslag in
  Given de piloot is ingelogd
  And de piloot heeft alle vereiste velden correct ingevuld
  When de piloot het verslag verzendt
  Then wordt het verslag opgeslagen
  And ontvangt de piloot een bevestiging van succesvolle verzending
  And wordt er een melding aan het verslag toegevoegd

Scenario: Verbindingsprobleem tijdens verzenden
  Given de piloot is ingelogd
  And de piloot heeft alle velden correct ingevuld
  When de piloot probeert het verslag te verzenden
  And er is een verbindingsprobleem
  Then toont het systeem een foutmelding
  And de piloot kan het later opnieuw proberen

  #hier nog geen steps van