package be.odisee.citymesh.stepdefinitions;

import io.cucumber.java.en.*;
import static org.junit.Assert.*;
import java.util.*;

public class StartPlaatsCriteriaSteps {

    private static class StartPlaats {
        String naam; int afstandNoFlyM;
        StartPlaats(String n, int a){ naam=n; afstandNoFlyM=a; }
    }

    private final List<StartPlaats> alle = new ArrayList<>();
    private List<StartPlaats> veilig;

    @Given("startplaatsen met afstanden tot no-fly zones")
    public void startplaatsen_met_afstanden() {
        alle.add(new StartPlaats("A", 30));
        alle.add(new StartPlaats("B", 50));
        alle.add(new StartPlaats("C", 120));
    }

    @When("ik filter op veilige startplaatsen")
    public void ik_filter_op_veilige_startplaatsen() {
        veilig = new ArrayList<>();
        for (StartPlaats s : alle) if (s.afstandNoFlyM >= 50) veilig.add(s);
    }

    @Then("krijg ik enkel startplaatsen terug met afstand >= {int} meter")
    public void enkel_startplaatsen_met_drempel(Integer drempel) {
        assertNotNull(veilig);
        assertFalse(veilig.isEmpty());
        assertTrue(veilig.stream().allMatch(s -> s.afstandNoFlyM >= drempel));
    }
}

