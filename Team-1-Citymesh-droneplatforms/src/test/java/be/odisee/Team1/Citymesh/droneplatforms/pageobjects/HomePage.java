package be.odisee.Team1.Citymesh.droneplatforms.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends AbstractPage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getPageText() {
        // wacht tot de juiste pagina geladen is
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions
                .textToBePresentInElementLocated(By.tagName("body"), "Lijst van personen"));
        return(super.getPageText());
    }

    public boolean isStartPlacesLinkVisible() {
        return driver.findElements(By.id("start-places-link")).size() > 0;
    }
}

