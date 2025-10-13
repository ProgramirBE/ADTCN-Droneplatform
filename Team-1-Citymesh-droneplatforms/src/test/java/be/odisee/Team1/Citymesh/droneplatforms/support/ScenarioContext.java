package be.odisee.Team1.Citymesh.droneplatforms.support;

import org.openqa.selenium.WebDriver;

public class ScenarioContext {
    private WebDriver driver;
    private String baseUrl;

    public WebDriver getDriver() { return driver; }
    public void setDriver(WebDriver driver) { this.driver = driver; }

    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
}
