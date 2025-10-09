package be.odisee.Team1.Citymesh.droneplatforms.pageobjects;

import org.openqa.selenium.*;
import java.time.Duration;

public class StartPlacesPage {
    private WebDriver driver;

    public StartPlacesPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("http://localhost:8080/start-places"); // adapt to your test app URL
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    public void reserveById(String id) {
        WebElement row = driver.findElement(By.cssSelector("tr[data-id='" + id + "']"));
        WebElement reserveBtn = row.findElement(By.cssSelector("button.reserve"));
        reserveBtn.click();
    }

    public String getStatus(String id) {
        WebElement row = driver.findElement(By.cssSelector("tr[data-id='" + id + "']"));
        return row.findElement(By.cssSelector(".status")).getText();
    }

    public boolean isReserveButtonVisible(String id) {
        WebElement row = driver.findElement(By.cssSelector("tr[data-id='" + id + "']"));
        return !row.findElements(By.cssSelector("button.reserve")).isEmpty();
    }

    public String getExpiryText(String id) {
        WebElement row = driver.findElement(By.cssSelector("tr[data-id='" + id + "']"));
        return row.findElement(By.cssSelector(".expiry")).getText();
    }
}
