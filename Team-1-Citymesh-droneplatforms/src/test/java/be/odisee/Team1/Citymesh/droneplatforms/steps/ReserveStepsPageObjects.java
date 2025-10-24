package be.odisee.Team1.Citymesh.droneplatforms.steps;

import io.cucumber.java.*;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import be.odisee.Team1.Citymesh.droneplatforms.StartPlaceStatus;
import be.odisee.Team1.Citymesh.droneplatforms.pageobjects.HomePage;
import be.odisee.Team1.Citymesh.droneplatforms.pageobjects.StartPlacesPage;
import static org.junit.jupiter.api.Assertions.*;

public class ReserveStepsPageObjects {
    private WebDriver driver;
    private StartPlacesPage startPlaces;
    private final String baseUrl = "http://localhost:8000";

    @Given("a start place {string} exists that is available and {int}m from the nearest no-fly zone")
    public void givenAvailablePlace(String id, int distance) {
        System.out.printf("Setup: %s available (%dm from NFZ)%n", id, distance);
    }

    @Given("another start place {string} exists that is reserved")
    public void givenReservedPlace(String id) {
        System.out.printf("Setup: %s already reserved%n", id);
    }

    @When("the pilot navigates to the start-places page")
    public void navigateToPage() {
        startPlaces.open(baseUrl);
    }

    @When("the pilot reserves start place {string}")
    public void reserve(String id) {
        startPlaces.clickReserve(id);
    }

    @Then("the system marks {string} as reserved")
    public void verifyReserved(String id) {
        assertEquals("reserved", startPlaces.getStatus(id).toLowerCase());
    }

    @After
    public void cleanup() {
        if (driver != null) driver.quit();
    }
}
