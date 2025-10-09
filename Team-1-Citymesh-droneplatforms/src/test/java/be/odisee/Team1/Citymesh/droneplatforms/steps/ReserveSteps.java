package be.odisee.Team1.Citymesh.droneplatforms.steps;


import be.odisee.Team1.Citymesh.droneplatforms.pageobjects.StartPlacesPage;
import io.cucumber.java.*;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import be.odisee.Team1.Citymesh.droneplatforms.StartPlaceStatus;
import be.odisee.Team1.Citymesh.droneplatforms.pageobjects.HomePage;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class ReserveSteps {
    WebDriver driver = new ChromeDriver();

    HomePage homePage = new HomePage(driver);
    StartPlacesPage startPlacesPage = new StartPlacesPage(driver);
    @Given("a start place {string} exists that is available and {int}m from the nearest no-fly zone")
    public void setupAvailablePlace(String id, int distance) {
        // Seed via API or test DB instead of UI
        System.out.printf("Assume start place %s is available (%dm from NFZ)%n", id, distance);
    }

    @Given("another start place {string} exists that is reserved")
    public void setupReservedPlace(String id) {
        System.out.printf("Assume start place %s is reserved%n", id);
    }

    @When("the pilot navigates to the start-places page")
    public void navigate() {
        startPlacesPage.open();
    }

    @When("the pilot reserves start place {string}")
    public void reserve(String id) {
        startPlacesPage.reserveById(id);
    }

    @Then("the system marks {string} as reserved")
    public void verifyReserved(String id) {
        assertEquals("reserved", startPlacesPage.getStatus(id));
    }

    @Then("{string} cannot be reserved again (no double booking)")
    public void verifyCannotReserveAgain(String id) {
        assertFalse(startPlacesPage.isReserveButtonVisible(id));
    }

    @Then("the reservation has an expiry of {int} minutes")
    public void verifyExpiry(int minutes) {
        String expiryText = startPlacesPage.getExpiryText("SP-101");
        assertTrue(expiryText.contains(String.valueOf(minutes)));
    }

    @After
    public void cleanup() {
        if (driver != null) driver.quit();
    }
}
