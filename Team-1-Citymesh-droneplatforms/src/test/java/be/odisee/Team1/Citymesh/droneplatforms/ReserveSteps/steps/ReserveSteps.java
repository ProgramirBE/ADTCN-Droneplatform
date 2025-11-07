package be.odisee.Team1.Citymesh.droneplatforms.ReserveSteps.steps;

import be.odisee.Team1.Citymesh.droneplatforms.ReserveSteps_PageObjects.pageobjects.HomePage;
import be.odisee.Team1.Citymesh.droneplatforms.ReserveSteps_PageObjects.pageobjects.StartPlacesPage;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ReserveSteps {

    private WebDriver driver;
    private final String baseUrl = "http://localhost:8080";


    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\andre\\OneDrive\\Documents\\_Odisee_vakken\\3de_jaar\\BA3\\chromedriver\\chromedriver-win64\\chromedriver.exe");
    }

    @Given("the system is in a known initial state")
    public void the_system_is_in_a_known_initial_state() {
        WebDriverManager.chromedriver().avoidResolutionCache().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl + "/start-places.html");
    }

    @Given("a start place {string} exists that is available and {int}m from the nearest no-fly zone")
    public void a_start_place_exists_that_is_available_and_from_the_nearest_no_fly_zone(String id, Integer distance) {
        WebElement row = driver.findElement(By.cssSelector("tr[data-id='" + id + "']"));
        Assert.assertNotNull("Start place should exist", row);
        Assert.assertEquals("available", row.findElement(By.cssSelector(".status")).getText().trim().toLowerCase());
        Assert.assertEquals(distance.intValue(), Integer.parseInt(row.getAttribute("data-distance")));
    }

    @Given("another start place {string} exists that is reserved")
    public void another_start_place_exists_that_is_reserved(String id) {
        WebElement row = driver.findElement(By.cssSelector("tr[data-id='" + id + "']"));
        Assert.assertNotNull(row);
        String status = row.findElement(By.cssSelector(".status")).getText().trim().toLowerCase();
        Assert.assertEquals("reserved", status);
    }

    @When("the pilot reserves start place {string}")
    public void the_pilot_reserves_start_place(String id) {
        WebElement row = driver.findElement(By.cssSelector("tr[data-id='" + id + "']"));
        WebElement button = row.findElement(By.cssSelector("button.reserve"));
        if (button.isEnabled()) {
            button.click();
        }
    }

    @Then("the system marks {string} as reserved")
    public void the_system_marks_as_reserved(String id) {
        WebElement row = driver.findElement(By.cssSelector("tr[data-id='" + id + "']"));
        String status = row.findElement(By.cssSelector(".status")).getText().trim().toLowerCase();
        Assert.assertEquals("reserved", status);
        driver.quit();
    }

    @Then("the reservation for {string} is rejected due to insufficient safety distance")
    public void the_reservation_for_is_rejected_due_to_insufficient_safety_distance(String id) {
        WebElement row = driver.findElement(By.cssSelector("tr[data-id='" + id + "']"));
        String status = row.findElement(By.cssSelector(".status")).getText().trim().toLowerCase();
        Assert.assertEquals("available", status);
        driver.quit();
    }
}
