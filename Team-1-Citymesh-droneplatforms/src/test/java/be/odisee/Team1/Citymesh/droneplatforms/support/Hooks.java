package be.odisee.Team1.Citymesh.droneplatforms.support;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

public class Hooks {

    private final ScenarioContext ctx;

    public Hooks(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    @Before
    public void setUp() {
        // Driver
        WebDriver driver = WebDriverFactory.create();
        ctx.setDriver(driver);
        ctx.setBaseUrl(System.getProperty("baseUrl", "http://localhost:8080"));
    }

    @After
    public void tearDown() {
        if (ctx.getDriver() != null) {
            ctx.getDriver().quit();
        }
    }
}
