package be.odisee.Team1.Citymesh.droneplatforms.ReserveSteps_PageObjects.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends AbstractPage {
    private final By startPlacesLink = By.id("start-places-link");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/home.html");
        wait.until(ExpectedConditions.elementToBeClickable(startPlacesLink));
    }

    public void goToStartPlaces() {
        driver.findElement(startPlacesLink).click();
        // letting StartPlacesPage wait for its own content
    }
}


