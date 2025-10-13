package be.odisee.Team1.Citymesh.droneplatforms.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {
    public static WebDriver create() {
        ChromeOptions opts = new ChromeOptions();
        if (Boolean.parseBoolean(System.getProperty("headless", "true"))) {
            opts.addArguments("--headless=new");
        }
        opts.addArguments("--window-size=1600,1000");
        return new ChromeDriver(opts);
    }
}
