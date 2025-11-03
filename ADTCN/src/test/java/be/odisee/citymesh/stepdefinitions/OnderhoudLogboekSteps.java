package be.odisee.citymesh.stepdefinitions;

import io.cucumber.java.en.*;
import static org.junit.Assert.*;
import java.util.*;

public class OnderhoudLogboekSteps {

    static class LogEntry {
        Date datum; String type; String omschrijving;
        LogEntry(Date d, String t, String o){ this.datum=d; this.type=t; this.omschrijving=o; }
    }

    private final Map<Integer, List<LogEntry>> logboekPerDrone = new HashMap<>();
    private int droneId;

    @Given("een drone met id {int} heeft een onderhoudslogboek")
    public void drone_heeft_logboek(Integer id) {
        droneId = id;
        logboekPerDrone.putIfAbsent(droneId, new ArrayList<>());
    }

    @When("ik voeg een onderhoudsentry toe met datum, type {string} en omschrijving {string}")
    public void voeg_entry_toe(String type, String omschrijving) {
        List<LogEntry> lijst = logboekPerDrone.get(droneId);
        lijst.add(new LogEntry(new Date(), type, omschrijving));
    }

    @Then("verschijnt de entry in het log met datum, type en omschrijving")
    public void entry_verschijnt_in_log() {
        List<LogEntry> lijst = logboekPerDrone.get(droneId);
        assertFalse(lijst.isEmpty());
        LogEntry laatste = lijst.get(lijst.size()-1);
        assertNotNull(laatste.datum);
        assertNotNull(laatste.type);
        assertNotNull(laatste.omschrijving);
    }
}

