@US006
Feature: Vluchtgeschiedenis raadplegen

  Scenario: Piloot ziet routes op kaart in de geschiedenis
    Given een piloot "piet" met voltooide vluchten
    When ik mijn vluchtgeschiedenis raadpleeg
    Then wordt de route per vlucht op een kaart getoond
