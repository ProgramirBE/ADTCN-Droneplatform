package be.odisee.Team1.Citymesh.droneplatforms.pageobjects;

import org.openqa.selenium.*;
import java.time.Duration;


public class StartPlacesPage {
    private WebDriver driver;
    private final By tableRows = By.cssSelector("#startplaces tbody tr");

    public StartPlacesPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/templates/start-places.html");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    public WebElement findRow(String id) {
        for (WebElement row : driver.findElements(tableRows)) {
            if (id.equals(row.getAttribute("data-id"))) return row;
        }
        return null;
    }

    public String getStatus(String id) {
        WebElement row = findRow(id);
        if (row == null) return null;
        return row.findElement(By.cssSelector(".status")).getText().trim();
    }

    public int getDistance(String id) {
        WebElement row = findRow(id);
        if (row == null) return -1;
        String d = row.getAttribute("data-distance");
        try { return Integer.parseInt(d); } catch (Exception e) { return -1; }
    }

    public void clickReserve(String id) {
        WebElement row = findRow(id);
        if (row == null) throw new IllegalStateException("Row not found: " + id);
        WebElement btn = row.findElement(By.cssSelector("button.reserve"));
        btn.click();
    }

    public boolean isReserveButtonEnabled(String id) {
        WebElement row = findRow(id);
        if (row == null) return false;
        WebElement btn = row.findElement(By.cssSelector("button.reserve"));
        return btn.isEnabled();
    }

    public String getExpiryMinutes(String id) {
        WebElement row = findRow(id);
        if (row == null) return null;
        return row.getAttribute("data-expires-minutes");
    }
}
