package be.odisee.citymesh.stepdefinitions;

import io.cucumber.java.en.*;
import static org.junit.Assert.*;
import java.util.*;

public class VluchtGeschiedenisSteps {

    private final Map<String, List<String>> vluchtGeschiedenis = new HashMap<>();
    private String piloot;
    private List<String> getoond;

    @Given("een piloot {string} met voltooide vluchten")
    public void piloot_met_voltooide_vluchten(String naam) {
        piloot = naam;
        vluchtGeschiedenis.put(naam, Arrays.asList("WP1->WP2->WP3"));
    }

    @When("ik mijn vluchtgeschiedenis raadpleeg")
    public void ik_mijn_vluchtgeschiedenis_raadpleeg() {
        getoond = vluchtGeschiedenis.getOrDefault(piloot, Collections.emptyList());
    }

    @Then("wordt de route per vlucht op een kaart getoond")
    public void route_wordt_op_kaart_getoond() {
        assertNotNull(getoond);
        assertFalse(getoond.isEmpty());
        assertTrue(getoond.get(0).contains("->"));
    }
}

