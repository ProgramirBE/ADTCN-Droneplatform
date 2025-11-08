package be.odisee.Team1.Citymesh.droneplatforms.ReserveSteps_PageObjects.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AbstractPage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    }

    public HomePage navigateToHomePage() {
        driver.navigate().to("http://localhost:8080/index.html");
        return new HomePage(driver);
    }

    public String getPageText() {
        return driver.findElement(By.tagName("body")).getText();
    }

    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}

