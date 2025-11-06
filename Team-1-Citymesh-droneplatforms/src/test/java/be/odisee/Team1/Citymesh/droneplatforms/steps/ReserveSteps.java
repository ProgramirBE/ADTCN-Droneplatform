package be.odisee.Team1.Citymesh.droneplatforms.steps;


import be.odisee.Team1.Citymesh.droneplatforms.pageobjects.StartPlacesPage;
import io.cucumber.java.*;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import be.odisee.Team1.Citymesh.droneplatforms.StartPlaceStatus;
import be.odisee.Team1.Citymesh.droneplatforms.pageobjects.HomePage;
import org.openqa.selenium.chrome.ChromeDriver;
import be.odisee.Team1.Citymesh.droneplatforms.support.Hooks;


import static org.junit.jupiter.api.Assertions.*;

public class ReserveSteps {
    private final WebDriver driver = Hooks.setup;
    private final String baseUrl = System.getenv().getOrDefault("TEST_BASE_URL","http://localhost:8000");

    @Given("a start place {string} exists that is available and {int}m from the nearest no-fly zone")
    public void startPlaceExists(String id, int distance) {
        driver.get(baseUrl + "/start-places.html");
        WebElement row = driver.findElement(By.cssSelector("tr[data-id='" + id + "']"));
        assertNotNull(row);
        assertEquals(String.valueOf(distance), row.getAttribute("data-distance"));
        assertEquals("available", row.findElement(By.cssSelector(".status")).getText().trim().toLowerCase());
    }

    @Given("another start place {string} exists that is reserved")
    public void anotherReserved(String id) {
        driver.get(baseUrl + "/start-places.html");
        WebElement status = driver.findElement(By.cssSelector("tr[data-id='" + id + "'] .status"));
        assertEquals("reserved", status.getText().trim().toLowerCase());
    }

    @When("the pilot reserves start place {string}")
    public void pilotReserves(String id) {
        driver.get(baseUrl + "/start-places.html");
        WebElement button = driver.findElement(By.cssSelector("tr[data-id='" + id + "'] button.reserve"));
        button.click();
    }

    @Then("the system marks {string} as reserved")
    public void systemMarksReserved(String id) {
        driver.get(baseUrl + "/start-places.html");
        WebElement status = driver.findElement(By.cssSelector("tr[data-id='" + id + "'] .status"));
        assertEquals("reserved", status.getText().trim().toLowerCase());
    }

    @After
    public void cleanup() {
        if (driver != null) driver.quit();
    }
}
