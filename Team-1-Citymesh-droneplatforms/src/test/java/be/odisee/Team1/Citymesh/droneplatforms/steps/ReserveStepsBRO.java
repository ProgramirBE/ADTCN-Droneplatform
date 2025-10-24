package be.odisee.Team1.Citymesh.droneplatforms.steps;

import be.odisee.Team1.Citymesh.droneplatforms.StartPlace;
import be.odisee.Team1.Citymesh.droneplatforms.pageobjects.StartPlacesPage;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import static org.junit.jupiter.api.Assertions.*;
import io.cucumber.java.en.*;
import be.odisee.Team1.Citymesh.Data.TestDataStore;
import static org.junit.jupiter.api.Assertions.*;

public class ReserveStepsBRO {
    private StartPlacesPage startPlace;
    private final TestDataStore store = TestDataStore.get();

    @Given("a start place {string} exists that is available and {int}m from the nearest no-fly zone")
    public void startPlaceExists(String id, int distance) {
        store.clearAll();
        StartPlace sp = new StartPlace(id, distance);
        store.addStartPlace(sp);
        assertEquals(StartPlace.Status.AVAILABLE, sp.getStatus());
    }

    @Given("another start place {string} exists that is reserved")
    public void anotherReserved(String id) {
        StartPlace sp = new StartPlace(id, 40);
        sp.setStatus(StartPlace.Status.RESERVED);
        store.addStartPlace(sp);
    }

    @When("the pilot reserves start place {string}")
    public void pilotReserves(String id) {
        boolean success = store.reserve(id);
        assertTrue(success, "Start place must be at least 50m away to reserve");
    }

    @Then("the system marks {string} as reserved")
    public void systemMarksReserved(String id) {
        StartPlace sp = store.getStartPlace(id);
        assertEquals(StartPlace.Status.RESERVED, sp.getStatus());
    }
}
