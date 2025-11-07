package be.odisee.Team1.Citymesh.droneplatforms.ReserveSteps_PageObjects.steps;

import be.odisee.Team1.Citymesh.droneplatforms.ReserveSteps_PageObjects.pageobjects.*;
import io.cucumber.java.*;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ReserveStepsPageObjects {

    private WebDriver driver;
    private HomePage homePage;
    private StartPlacesPage startPlacesPage;

    // Change to 8080 if your test server runs there
    private final String baseUrl = System.getProperty("baseUrl", "http://localhost:8080");

    // ---- Setup and teardown ----
    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\andre\\OneDrive\\Documents\\_Odisee_vakken\\3de_jaar\\BA3\\chromedriver\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        homePage = new HomePage(driver);
        startPlacesPage = new StartPlacesPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ---- Step definitions ----

    @Given("the system is in a known initial state")
    public void the_system_is_in_a_known_initial_state() {
        // simply open homepage and go to start places to reset state
        homePage.open(baseUrl);
        homePage.goToStartPlaces();
    }

    @Given("a start place {string} exists that is available and {int}m from the nearest no-fly zone")
    public void a_start_place_exists_that_is_available_and_m_from_the_nearest_no_fly_zone(String id, int distance) {
        startPlacesPage.openDirect(baseUrl);
        Assertions.assertTrue(startPlacesPage.isRowPresent(id), "Start place row not found: " + id);
        Assertions.assertEquals(distance, startPlacesPage.getDistance(id), "Distance mismatch for: " + id);
        Assertions.assertEquals("available", startPlacesPage.getStatus(id), "Expected 'available' status");
    }

    @Given("another start place {string} exists that is reserved")
    public void another_start_place_exists_that_is_reserved(String id) {
        startPlacesPage.openDirect(baseUrl);
        Assertions.assertTrue(startPlacesPage.isRowPresent(id), "Reserved start place not found: " + id);
        Assertions.assertEquals("reserved", startPlacesPage.getStatus(id), "Expected 'reserved' status");
    }

    @When("the pilot reserves start place {string}")
    public void the_pilot_reserves_start_place(String id) {
        startPlacesPage.openDirect(baseUrl);
        startPlacesPage.clickReserveFor(id);
    }

    @Then("the system marks {string} as reserved")
    public void the_system_marks_as_reserved(String id) {
        startPlacesPage.openDirect(baseUrl);
        Assertions.assertEquals("reserved", startPlacesPage.getStatus(id), id + " should be reserved");
    }

    @Then("the reservation for {string} is rejected due to insufficient safety distance")
    public void the_reservation_for_is_rejected_due_to_insufficient_safety_distance(String id) {
        startPlacesPage.openDirect(baseUrl);
        Assertions.assertEquals("available", startPlacesPage.getStatus(id), id + " should remain available");
    }
}
