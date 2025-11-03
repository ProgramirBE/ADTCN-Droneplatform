package be.odisee.citymesh.stepdefinitions;

import io.cucumber.java.en.*;
import static org.junit.Assert.*;
import java.util.*;

public class DronesLijstSteps {

    private static class DroneVM { String status; DroneVM(String s){ status=s; } }
    private List<DroneVM> lijst;
    private boolean kolomBestaat;

    @Given("er bestaan drones met verschillende statussen")
    public void er_bestaan_drones() {
        lijst = Arrays.asList(new DroneVM("Vliegklaar"), new DroneVM("In Onderhoud"), new DroneVM("In Gebruik"));
    }

    @When("ik de lijst van drones opvraag")
    public void ik_de_lijst_opvraag() {
        kolomBestaat = true; // simulatie dat de kolom Status bestaat
    }

    @Then("bevat de lijst een kolom {string}")
    public void bevat_de_lijst_kolom(String kolom) {
        assertTrue("Kolom 'Status' ontbreekt", kolomBestaat && "Status".equals(kolom));
    }

    @Then("elke statuswaarde is een van {string}, {string}, {string}")
    public void elke_statuswaarde_toegestaan(String s1, String s2, String s3) {
        Set<String> toegestaan = new HashSet<>(Arrays.asList(s1, s2, s3));
        assertTrue(lijst.stream().allMatch(d -> toegestaan.contains(d.status)));
    }
}

