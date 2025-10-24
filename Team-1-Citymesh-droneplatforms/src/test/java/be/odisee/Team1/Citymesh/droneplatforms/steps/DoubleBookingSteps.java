package be.odisee.Team1.Citymesh.droneplatforms.steps;

import be.odisee.Team1.Citymesh.droneplatforms.pageobjects.HomePage;
import be.odisee.Team1.Citymesh.droneplatforms.pageobjects.StartPlacesPage;
import be.odisee.Team1.Citymesh.droneplatforms.support.ScenarioContext;
import io.cucumber.java.en.*;
import org.assertj.core.api.Assertions;

public class DoubleBookingSteps {

    private final ScenarioContext ctx;
    private HomePage home;
    private StartPlacesPage startPlaces;
    private String spot;

    public DoubleBookingSteps(ScenarioContext ctx) { this.ctx = ctx; }

    @Given("het systeem staat in een gekende beginsituatie")
    public void initState() {
        // Hooks hebben al ge-seed; deze step is puur documentair (WHAT, niet HOW)
    }

    @Given("ik bevind me op de homepagina")
    public void openHome() {
        home = new HomePage(ctx.getDriver(), ctx.getBaseUrl());
        home.open();
    }

    @Given("er bestaat een beschikbare startplaats {string}")
    public void availableSpot(String name) {
        this.spot = name;
        startPlaces = home.goToStartPlaces();
        Assertions.assertThat(startPlaces.isStartPlaceVisible(spot))
                .as("Startplaats %s zichtbaar", spot).isTrue();
    }

    @When("ik die startplaats reserveer")
    public void reserveOnce() {
        startPlaces.reserveStartPlace(spot);
    }

    @Then("de reservatie is bevestigd")
    public void reservationConfirmed() {
        Assertions.assertThat(startPlaces.isReservationConfirmed(spot))
                .as("Bevestiging reservatie %s", spot).isTrue();
    }

    @When("ik dezelfde startplaats opnieuw reserveer")
    public void reserveTwice() {
        startPlaces.reserveStartPlace(spot);
    }

    @Then("zie ik een fout dat dubbele boekingen niet toegestaan zijn")
    public void doubleBookingBlocked() {
        Assertions.assertThat(startPlaces.isDoubleBookingErrorShown(spot))
                .as("Foutmelding dubbele boeking %s", spot).isTrue();
    }
}
