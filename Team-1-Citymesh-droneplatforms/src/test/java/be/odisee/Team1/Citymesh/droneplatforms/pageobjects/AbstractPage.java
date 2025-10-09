package be.odisee.Team1.Citymesh.droneplatforms.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AbstractPage {

    protected WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
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

