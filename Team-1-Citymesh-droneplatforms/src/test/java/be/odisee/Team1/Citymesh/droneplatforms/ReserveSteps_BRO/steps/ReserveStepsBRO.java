package be.odisee.Team1.Citymesh.droneplatforms.ReserveSteps_BRO.steps;

import be.odisee.Team1.Citymesh.droneplatforms.ReserveSteps_BRO.pageobjects.*;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ReserveStepsBRO {

    private WebDriver driver;
    private HomePage homePage;
    private StartPlacesPage startPlacesPage;
    private final String baseUrl = "http://localhost:8080";


    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\andre\\OneDrive\\Documents\\_Odisee_vakken\\3de_jaar\\BA3\\chromedriver\\chromedriver-win64\\chromedriver.exe");
    }

    @Given("the system is in a known initial state")
    public void the_system_is_in_a_known_initial_state() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        homePage.open(baseUrl);
        homePage.goToStartPlaces();
        startPlacesPage = new StartPlacesPage(driver);
    }

    @Given("a start place {string} exists that is available and {int}m from the nearest no-fly zone")
    public void a_start_place_exists_that_is_available_and_from_the_nearest_no_fly_zone(String id, Integer distance) {
        Assert.assertTrue("Start place row should exist", startPlacesPage.isRowPresent(id));
        Assert.assertEquals(distance.intValue(), startPlacesPage.getDistance(id));
        Assert.assertEquals("available", startPlacesPage.getStatus(id));
    }

    @Given("another start place {string} exists that is reserved")
    public void another_start_place_exists_that_is_reserved(String id) {
        Assert.assertTrue("Reserved start place must exist", startPlacesPage.isRowPresent(id));
        Assert.assertEquals("reserved", startPlacesPage.getStatus(id));
    }

    @When("the pilot reserves start place {string}")
    public void the_pilot_reserves_start_place(String id) {
        startPlacesPage.clickReserveFor(id);
    }

    @Then("the system marks {string} as reserved")
    public void the_system_marks_as_reserved(String id) {
        Assert.assertEquals("reserved", startPlacesPage.getStatus(id));
        driver.quit();
    }

    @Then("the reservation for {string} is rejected due to insufficient safety distance")
    public void the_reservation_for_is_rejected_due_to_insufficient_safety_distance(String id) {
        Assert.assertEquals("available", startPlacesPage.getStatus(id));
        driver.quit();
    }
}
