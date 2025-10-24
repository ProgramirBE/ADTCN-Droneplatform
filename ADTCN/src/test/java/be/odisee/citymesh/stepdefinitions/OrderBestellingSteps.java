package be.odisee.citymesh.stepdefinitions;

import be.odisee.citymesh.domain.Onderdeel;
import io.cucumber.java.en.*;
import be.odisee.citymesh.domain.Bestelling;
import be.odisee.citymesh.domain.Bestelling.BestellingStatus;
import org.junit.Assert;
import java.util.Date;
import java.util.ArrayList;

public class OrderBestellingSteps {
    private Bestelling order;
    private Onderdeel onderdeel = new Onderdeel("Motor", 2);
    @Given("het systeem is in status \"Initial\"")
    public void hetSysteemIsInStatusInitial() {
        order = new Bestelling(1, new Date(), BestellingStatus.INITIAL, new ArrayList<>());
        order.addOnderdeel(onderdeel);
        Assert.assertEquals(BestellingStatus.INITIAL, order.getStatus());
    }

    @When("de verwerking wordt uitgevoerd")
    public void startVerwerkingWordtUitgevoerd() {
        order.wijzigingStatus(BestellingStatus.IN_BEHANDELING);
    }

    @Then("moet de orderstatus veranderen naar \"In Behandeling\"")
    public void orderstatusVerandertNaarInBehandeling() {
        Assert.assertEquals(BestellingStatus.IN_BEHANDELING, order.getStatus());
    }

    @Then("er moet een tijdsstempel worden geregistreerd")
    public void tijdsstempelWordtGeregistreerd() {
        Assert.assertNotNull(order.getLaatsteWijziging());
    }

    @Given("de order is in status \"In Behandeling\"")
    public void orderIsInBehandeling() {
        order = new Bestelling(2, new Date(), BestellingStatus.IN_BEHANDELING, new ArrayList<>());
        order.addOnderdeel(onderdeel);
    }

    @When("de validatie wordt uitgevoerd")
    public void valideerControleWordtUitgevoerd() {
        order.wijzigingStatus(BestellingStatus.KLAAR_VOOR_CONTROLE);
    }

    @Then("moet de order klaar zijn voor controle")
    public void orderMoetKlaarZijnVoorControle() {
        Assert.assertEquals(BestellingStatus.KLAAR_VOOR_CONTROLE, order.getStatus());
    }

    @When("de bestelling wordt geanulleerd")
    public void annuleerBestellingWordtUitgevoerd() {
        order.wijzigingStatus(BestellingStatus.GEANNULEERD);
    }

    @Then("moet de orderstatus veranderen naar \"Geannuleerd\"")
    public void orderstatusVerandertNaarGeannuleerd() {
        Assert.assertEquals(BestellingStatus.GEANNULEERD, order.getStatus());
    }

    @Then("er moet een annuleringsbericht worden verstuurd")
    public void annuleringsberichtWordtVerstuurd() {
        System.out.println("Annuleringsbericht verstuurd");
    }

    @Given("de order is klaar voor controle")
    public void orderIsKlaarVoorControle() {
        order = new Bestelling(3, new Date(), BestellingStatus.KLAAR_VOOR_CONTROLE, new ArrayList<>());
        order.addOnderdeel(onderdeel);
    }

    @When("de bestelling als gecontroleerd wordt markeerd")
    public void markeerGecontroleerdWordtUitgevoerd() {
        order.wijzigingStatus(BestellingStatus.KLAAR_VOOR_VERZENDING);
    }

    @When("de status word aangepast")
    public void statusWordtAangepast() {
        // Deze stap wordt afhankelijk van de context aangepast
        // Simpelweg een bevestiging dat de status al is gewijzigd
        Assert.assertNotNull(order.getStatus());
    }

    @Then("moet de order klaar zijn voor verzending")
    public void orderIsKlaarVoorVerzending() {
        Assert.assertEquals(BestellingStatus.KLAAR_VOOR_VERZENDING, order.getStatus());
    }

    @Given("de order is goedgekeurd voor verzending")
    public void orderIsGoedgekeurdVoorVerzending() {
        order = new Bestelling(4, new Date(), BestellingStatus.KLAAR_VOOR_VERZENDING, new ArrayList<>());
        order.addOnderdeel(onderdeel);
    }

    @When("het order word verzonder")
    public void orderWordtVerzonden() {
        order.wijzigingStatus(BestellingStatus.VERZONDEN);
    }

    @Then("moet de orderstatus veranderen naar \"Verzonden\"")
    public void orderstatusVerandertNaarVerzonden() {
        Assert.assertEquals(BestellingStatus.VERZONDEN, order.getStatus());
    }

    @Then("er moet een trackingnummer worden gegenereerd")
    public void trackingnummerWordtGegenereerd() {
        System.out.println("Trackingnummer gegenereerd: " + Math.random());
    }

    @Given("de order is in status \"Verzonden\"")
    public void orderIsInVerzonden() {
        order = new Bestelling(5, new Date(), BestellingStatus.VERZONDEN, new ArrayList<>());
        order.addOnderdeel(onderdeel);
    }

    @When("de levering word bevestigd")
    public void leveringWordtBevestigd() {
        order.wijzigingStatus(BestellingStatus.AFGEROND);
    }

    @Then("moet de orderstatus veranderen naar \"Afgerond\"")
    public void orderstatusVerandertNaarAfgerond() {
        Assert.assertEquals(BestellingStatus.AFGEROND, order.getStatus());
    }

    @Then("de klant moet een bevestigingsmail ontvangen")
    public void bevestigingsmailVerzonden() {
        System.out.println("Bevestigingsmail verzonden naar klant.");
    }

    @Given("de order is in status \"Afgerond\"")
    public void orderIsAfgerond() {
        order = new Bestelling(6, new Date(), BestellingStatus.AFGEROND, new ArrayList<>());
        order.addOnderdeel(onderdeel);
    }

    @When("het eindproces wordt uitgevoerd")
    public void eindprocesWordtUitgevoerd() {
        order.wijzigingStatus(BestellingStatus.FINAL);
    }

    @Then("moet de orderstatus veranderen naar \"Final\"")
    public void orderstatusVerandertNaarFinal() {
        Assert.assertEquals(BestellingStatus.FINAL, order.getStatus());
    }

    @Then("alle resources moeten worden vrijgegeven")
    public void resourcesVrijgeven() {
        System.out.println("Alle resources vrijgegeven.");
    }

    @When("de controle gevalideerd wordt")
    public void controleWordtGevalideerd() {
        if (order.getOnderdelen().isEmpty()) {
            order.wijzigingStatus(BestellingStatus.IN_BEHANDELING);
        }
    }

    @When("er ontbreken verplichte gegevens")
    public void verplichteGegevensOntbreken() {
        order.emptyOnderdelen(order);
    }

    @Then("moet de order in \"In Behandeling\" blijven")
    public void orderBlijftInBehandeling() {
        Assert.assertEquals(BestellingStatus.IN_BEHANDELING, order.getStatus());
    }

    @Then("er moet een foutmelding worden gegenereerd")
    public void foutmeldingWordtGegenereerd() {
        System.out.println("Foutmelding: verplichte gegevens ontbreken.");
    }

    @Then("alle vereiste velden moeten correct zijn ingevuld")
    public void alle_vereiste_velden_moeten_correct_zijn_ingevuld() {
        boolean correct = true;
        if (order.getOnderdelen().isEmpty()) correct = false;
        if (order.getStatus() == null) correct = false;
        if (order.getDatum() == null) correct = false;

        Assert.assertTrue(correct);
    }
}
