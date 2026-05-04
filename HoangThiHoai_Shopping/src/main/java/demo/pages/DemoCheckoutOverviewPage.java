package demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DemoCheckoutOverviewPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By finishButton = By.id("finish");
    private By itemPrices = By.className("inventory_item_price");
    private By subtotalLabel = By.className("summary_subtotal_label");
    private By taxLabel = By.className("summary_tax_label");
    private By totalLabel = By.className("summary_total_label");
    private By cartBadge = By.className("shopping_cart_badge");

    public DemoCheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(finishButton));
    }

    private void safeClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public String getItemName(String productName) {
        By locator = By.xpath("//div[contains(@class, 'inventory_item_name') and normalize-space(.)='" + productName + "']");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    public String getItemDescription(String productName) {
        By locator = By.xpath("//div[normalize-space(.)='" + productName + "']/ancestor::div[contains(@class, 'cart_item')]//div[contains(@class, 'inventory_item_desc')]");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    public String getItemPrice(String productName) {
        By locator = By.xpath("//div[normalize-space(.)='" + productName + "']/ancestor::div[contains(@class, 'cart_item')]//div[contains(@class, 'inventory_item_price')]");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    public List<Double> getIndividualItemPrices() {
        List<WebElement> elements = driver.findElements(itemPrices);
        List<Double> prices = new ArrayList<>();
        for (WebElement element : elements) {
            String text = element.getText().replace("$", "");
            prices.add(Double.parseDouble(text));
        }
        return prices;
    }

    public double getSubtotal() {
        String text = driver.findElement(subtotalLabel).getText().replace("Item total: $", "");
        return Double.parseDouble(text);
    }

    public double getTax() {
        String text = driver.findElement(taxLabel).getText().replace("Tax: $", "");
        return Double.parseDouble(text);
    }

    public double getTotal() {
        String text = driver.findElement(totalLabel).getText().replace("Total: $", "");
        return Double.parseDouble(text);
    }

    public void clickFinish() {
        safeClick(finishButton);
    }

    public String getCartBadgeCount() {
        List<WebElement> badges = driver.findElements(cartBadge);
        if (badges.isEmpty()) {
            return "";
        }
        return badges.get(0).getText();
    }

    public boolean isCartBadgeDisplayed() {
        return !driver.findElements(cartBadge).isEmpty();
    }
}
