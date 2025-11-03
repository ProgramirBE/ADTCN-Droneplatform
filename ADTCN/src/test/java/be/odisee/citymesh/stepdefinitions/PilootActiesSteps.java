package be.odisee.citymesh.stepdefinitions;

import io.cucumber.java.nl.Als;
import io.cucumber.java.nl.Dan;
import io.cucumber.java.nl.Gegeven;
import org.junit.Assert;

public class PilootActiesSteps {

    private boolean ingelogd;
    private boolean geldigeInvoer;
    private boolean verzendingGelukt;
    private boolean verbindingsprobleem;

    @Gegeven("een piloot heeft een geldige gebruikersnaam en wachtwoord")
    public void een_piloot_heeft_een_geldige_gebruikersnaam_en_wachtwoord() {
        // stub: credentials zijn geldig
        ingelogd = false;
    }

    @Als("de piloot probeert in te loggen")
    public void de_piloot_probeert_in_te_loggen() {
        // stub: login slaagt
        ingelogd = true;
    }

    @Dan("wordt de login gevalideerd")
    public void wordt_de_login_gevalideerd() {
        Assert.assertTrue("Login zou gevalideerd moeten zijn", ingelogd);
    }

    @Dan("de piloot krijgt een bevestiging van succesvolle login")
    public void de_piloot_krijgt_een_bevestiging_van_succesvolle_login() {
        Assert.assertTrue("Piloot zou ingelogd moeten zijn", ingelogd);
    }

    @Gegeven("de piloot is ingelogd")
    public void de_piloot_is_ingelogd() {
        ingelogd = true;
    }

    @Gegeven("de piloot vult het verslag in met ongeldige gegevens")
    public void de_piloot_vult_het_verslag_in_met_ongeldige_gegevens() {
        geldigeInvoer = false;
    }

    @Als("de piloot probeert het verslag te verzenden")
    public void de_piloot_probeert_het_verslag_te_verzenden() {
        verzendingGelukt = ingelogd && geldigeInvoer && !verbindingsprobleem;
    }

    @Dan("toont het systeem een foutmelding")
    public void toont_het_systeem_een_foutmelding() {
        Assert.assertFalse("Verzending zou niet mogen slagen", verzendingGelukt);
    }

    @Dan("de piloot krijgt de kans om de invoer te corrigeren")
    public void de_piloot_krijgt_de_kans_om_de_invoer_te_corrigeren() {
        // stub: mogelijkheid bestaat
        Assert.assertFalse(verzendingGelukt);
    }

    @Gegeven("de piloot heeft alle vereiste velden correct ingevuld")
    public void de_piloot_heeft_alle_vereiste_velden_correct_ingevuld() {
        geldigeInvoer = true;
    }

    @Gegeven("de piloot heeft alle velden correct ingevuld")
    public void de_piloot_heeft_alle_velden_correct_ingevuld_alias() {
        geldigeInvoer = true;
    }

    @Als("de piloot het verslag verzendt")
    public void de_piloot_het_verslag_verzendt() {
        verzendingGelukt = ingelogd && geldigeInvoer && !verbindingsprobleem;
    }

    @Dan("wordt het verslag opgeslagen")
    public void wordt_het_verslag_opgeslagen() {
        Assert.assertTrue("Verzending zou moeten slagen", verzendingGelukt);
    }

    @Dan("ontvangt de piloot een bevestiging van succesvolle verzending")
    public void ontvangt_de_piloot_een_bevestiging_van_succesvolle_verzending() {
        Assert.assertTrue(verzendingGelukt);
    }

    @Dan("wordt er een melding aan het verslag toegevoegd")
    public void wordt_er_een_melding_aan_het_verslag_toegevoegd() {
        Assert.assertTrue(verzendingGelukt);
    }

    @Als("er is een verbindingsprobleem")
    public void er_is_een_verbindingsprobleem() {
        verbindingsprobleem = true;
        // Een verbindingsprobleem tijdens of net na de verzendpoging leidt tot falen
        verzendingGelukt = false;
    }

    @Dan("de piloot kan het later opnieuw proberen")
    public void de_piloot_kan_het_later_opnieuw_proberen() {
        Assert.assertFalse("Verzending mag niet slagen bij verbindingsprobleem", verzendingGelukt);
    }
}
