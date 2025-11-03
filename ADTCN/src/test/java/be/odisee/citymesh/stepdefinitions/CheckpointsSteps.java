package be.odisee.citymesh.stepdefinitions;

import io.cucumber.java.en.*;
import static org.junit.Assert.*;
import java.util.*;

public class CheckpointsSteps {

    private boolean kaartOpen;
    private final List<int[]> checkpoints = new ArrayList<>();

    @Given("een kaart is geopend voor de vlucht")
    public void kaart_is_geopend() {
        kaartOpen = true;
    }

    @When("ik een checkpoint pin plaats op positie {int},{int}")
    public void plaats_checkpoint(int x, int y) {
        assertTrue(kaartOpen);
        checkpoints.add(new int[]{x,y});
    }

    @Then("verschijnt er een checkpoint pin op positie {int},{int}")
    public void pin_verschijnt(int x, int y) {
        boolean bestaat = checkpoints.stream().anyMatch(p -> p[0]==x && p[1]==y);
        assertTrue(bestaat);
    }
}
