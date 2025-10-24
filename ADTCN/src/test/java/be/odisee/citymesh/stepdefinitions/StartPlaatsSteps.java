package be.odisee.citymesh.stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.*;

public class StartPlaatsSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    @Given("ik ben op de loginpagina")
    public void loginPagina() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("http://localhost:8080/login");
    }

    @When("ik mij inlog met gebruikersnaam {string} en wachtwoord {string}")
    public void logIn(String gebruikersnaam, String wachtwoord) {
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("loginButton"));

        usernameInput.sendKeys(gebruikersnaam);
        passwordInput.sendKeys(wachtwoord);
        loginButton.click();
    }

    @When("ik selecteer de eerste drone")
    public void selectDrone() {
        // pas aan indien nodig ;)
        String droneId = "35";
        String url = "http://localhost:8080/launchpads?droneId=" + droneId;
        driver.get(url);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#startplaatsen li")));
    }

    @When("ik klik op de knop Kies Startplaats")
    public void kiesStartPlaats() {
        WebElement kiesStartplaatsKnop = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chooseLaunchpadButton")));
        wait.until(ExpectedConditions.elementToBeClickable(kiesStartplaatsKnop));
        kiesStartplaatsKnop.click();

        System.out.println("Na klik: " + driver.getCurrentUrl());
        List<WebElement> h2s = driver.findElements(By.tagName("h2"));
        for (WebElement h2 : h2s) {
            System.out.println("H2 tekst: " + h2.getText());
        }
    }

    @And("ik ga naar de pagina met startplaatsen en kies een startplaats")
    public void startPlaatsen() {
        // pas aan indien nodig ;)
        String droneId = "35";
        String url = "http://localhost:8080/launchpads?droneId=" + droneId;
        driver.get(url);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("startplaatsen")));

        List<WebElement> pads = driver.findElements(By.cssSelector("ul li"));
        System.out.println("Aantal launchpads gevonden: " + pads.size());
        assertFalse("Geen launchpads gevonden!", pads.isEmpty());
    }

    @Then("zie ik een lijst van beschikbare startplaatsen")
    public void beschikbareStartPlaatsen() {
        List<WebElement> startplaatsen = driver.findElements(By.xpath("//h2[text()='Beschikbare Startplaatsen']/following-sibling::ul[1]/li"));
        assertFalse("Er zijn geen beschikbare startplaatsen!", startplaatsen.isEmpty());
    }

    @When("ik klik op de reserveer-knop van de eerste beschikbare startplaats")
    public void reserveerStartplaats() {
        List<WebElement> reserveerKnoppen = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//form[@class='reserve-form']/button")));
        reserveerKnoppen.getFirst().click();

        wait.until(ExpectedConditions.urlContains("/reservaties"));
    }

    @Then("kom ik op de Mijn Reservatie pagina")
    public void mijnReservaties() {
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Mijn Reservaties')]")));
        assertTrue(header.getText().contains("Mijn Reservaties"));
    }
}
