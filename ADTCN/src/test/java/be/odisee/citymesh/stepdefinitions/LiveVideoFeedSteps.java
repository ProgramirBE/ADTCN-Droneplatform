package be.odisee.citymesh.stepdefinitions;

import io.cucumber.java.en.*;
import static org.junit.Assert.*;

public class LiveVideofeedSteps {

    private boolean droneActief;
    private boolean feedWeergegeven;
    private long gemetenLatencyMs;

    @Given("een actieve drone met live videofeed")
    public void een_actieve_drone_met_live_videofeed() {
        droneActief = true;
    }

    @When("ik de live videofeed open")
    public void ik_de_live_videofeed_open() {
        assertTrue(droneActief);
        // Simulatie: feed opent en meet latentie
        feedWeergegeven = true;
        gemetenLatencyMs = 800; // stub: 0.8s
    }

    @Then("zie ik de videofeed in real-time")
    public void zie_ik_de_videofeed_in_real_time() {
        assertTrue(feedWeergegeven);
    }

    @Then("is de latentie maximaal {int} seconden")
    public void is_de_latentie_maximaal_seconden(Integer maxSec) {
        long maxMs = maxSec * 1000L;
        assertTrue("Latentie te hoog: " + gemetenLatencyMs + "ms", gemetenLatencyMs <= maxMs);
    }
}

