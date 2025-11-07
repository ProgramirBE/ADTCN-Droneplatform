package be.odisee.Team1.Citymesh.droneplatforms.ReserveSteps_PageObjects.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class StartPlacesPage extends AbstractPage {

    private final By rows = By.cssSelector("#startplaces tbody tr");
    private final By rowById = By.cssSelector("tr[data-id='%s']"); // format with id

    public StartPlacesPage(WebDriver driver) {
        super(driver);
    }

    public void openDirect(String baseUrl) {
        driver.get(baseUrl + "/start-places.html");
        wait.until(ExpectedConditions.presenceOfElementLocated(rows));
    }

    public boolean isRowPresent(String id) {
        return findRow(id) != null;
    }

    private WebElement findRow(String id) {
        List<WebElement> list = driver.findElements(rows);
        for (WebElement r : list) {
            if (id.equals(r.getAttribute("data-id"))) return r;
        }
        return null;
    }

    public int getDistance(String id) {
        WebElement r = findRow(id);
        if (r == null) throw new NoSuchElementException("No row " + id);
        String d = r.getAttribute("data-distance");
        return Integer.parseInt(d);
    }

    public String getStatus(String id) {
        WebElement r = findRow(id);
        if (r == null) throw new NoSuchElementException("No row " + id);
        WebElement s = r.findElement(By.cssSelector(".status"));
        wait.until(ExpectedConditions.visibilityOf(s));
        return s.getText().trim().toLowerCase();
    }

    public void clickReserveFor(String id) {
        WebElement r = findRow(id);
        if (r == null) throw new NoSuchElementException("No row " + id);
        WebElement btn = r.findElement(By.cssSelector("button.reserve"));
        wait.until(ExpectedConditions.elementToBeClickable(btn));
        btn.click();
        // wait for status change
        WebElement statusEl = r.findElement(By.cssSelector(".status"));
        wait.until(ExpectedConditions.textToBePresentInElement(statusEl, "reserved"));
    }
}
