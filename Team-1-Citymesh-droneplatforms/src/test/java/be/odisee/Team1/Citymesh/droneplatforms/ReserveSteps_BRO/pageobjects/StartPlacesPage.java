package be.odisee.Team1.Citymesh.droneplatforms.ReserveSteps_BRO.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class StartPlacesPage extends AbstractPage {

    private final By rows = By.cssSelector("#startplaces tbody tr");

    public StartPlacesPage(WebDriver driver) {
        super(driver);
    }

    public void openDirect(String baseUrl) {
        driver.get(baseUrl + "/start-places.html");
        wait.until(ExpectedConditions.presenceOfElementLocated(rows));
    }

    public void clearAll() {
        ((JavascriptExecutor) driver).executeScript(
                "var tb = document.querySelector('#startplaces tbody'); if (tb) tb.innerHTML = '';");
    }

    /**
     * Add or update a start place row. This allows tests to create any row ID dynamically.
     * @param id e.g. "SP-201"
     * @param distance integer distance in meters
     * @param status "available" or "reserved"
     */
    public void addOrUpdateStartPlace(String id, int distance, String status) {
        // build the HTML row
        String disabledAttr = "reserved".equalsIgnoreCase(status) ? "disabled" : "";
        String rowHtml = "<tr data-id='" + id + "' data-distance='" + distance + "'>"
                + "<td class='id'>" + id + "</td>"
                + "<td class='distance'>" + distance + "</td>"
                + "<td class='status'>" + status + "</td>"
                + "<td><button class='reserve' " + disabledAttr + ">Reserve</button></td>"
                + "</tr>";

        // insert or replace using JS
        ((JavascriptExecutor) driver).executeScript(
                "var tbody = document.querySelector('#startplaces tbody');"
                        + "var id = arguments[1];"
                        + "var html = arguments[0];"
                        + "var existing = tbody.querySelector(\"tr[data-id='\"+id+\"']\");"
                        + "if (existing) { existing.outerHTML = html; } else { tbody.insertAdjacentHTML('beforeend', html); }",
                rowHtml, id);
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
        // If reservation succeeds, status will become 'reserved'.
        // But if it's too close or not available, the page's JS shows an alert.
    }
}
