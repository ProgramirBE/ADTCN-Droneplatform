package be.odisee.citymesh.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.Assert.*;

import java.util.*;

public class GebruikersBeheerSteps {

    private final Set<String> toegestaneRollen = new HashSet<>(Arrays.asList("Piloot", "Mechanieker", "Administrator"));
    private final Map<String, Set<String>> users = new HashMap<>();
    private boolean adminIngelogd;

    @Given("de administrator is ingelogd")
    public void de_administrator_is_ingelogd() {
        adminIngelogd = true;
    }

    @When("de administrator maakt een nieuwe gebruiker {string} en kent de rol {string} toe")
    public void de_admin_maakt_gebruiker_en_rol(String username, String rol) {
        assertTrue("Admin moet ingelogd zijn", adminIngelogd);
        assertTrue("Onbekende rol: " + rol, toegestaneRollen.contains(rol));
        users.computeIfAbsent(username, u -> new HashSet<>()).add(rol);
    }

    @Then("bestaat de gebruiker {string} met de rol {string}")
    public void bestaat_de_gebruiker_met_de_rol(String username, String rol) {
        assertTrue(users.containsKey(username));
        assertTrue(users.get(username).contains(rol));
    }

    @Then("de rollen omvatten {string}, {string}, {string}")
    public void de_rollen_omvatten(String r1, String r2, String r3) {
        Set<String> verwacht = new HashSet<>(Arrays.asList(r1, r2, r3));
        assertEquals(verwacht, toegestaneRollen);
    }
}

