package be.odisee.citymesh.stepdefinitions;

import io.cucumber.java.en.*;
import static org.junit.Assert.*;

public class BatterijWaarschuwingSteps {

    private int batterij;
    private boolean waarschuwing;

    @Given("een drone in vlucht met batterijniveau {int}")
    public void drone_in_vlucht(int niveau) {
        batterij = niveau;
        waarschuwing = false;
    }

    @When("het batterijniveau daalt naar {int}")
    public void batterij_daalt(int nieuw) {
        batterij = nieuw;
        if (batterij < 20) waarschuwing = true;
    }

    @Then("ontvangt de piloot een kritieke batterijwaarschuwing")
    public void piloot_krijgt_waarschuwing() {
        assertTrue(waarschuwing);
    }
}

