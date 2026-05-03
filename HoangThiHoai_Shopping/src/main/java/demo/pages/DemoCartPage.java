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

public class DemoCartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By checkoutButton = By.id("checkout");
    private By continueShoppingButton = By.id("continue-shopping");
    private By cartBadge = By.className("shopping_cart_badge");

    public DemoCartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutButton));
    }

    private void safeClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public List<String> getCartItemNames() {
        List<WebElement> nameElements = driver.findElements(By.cssSelector(".cart_item .inventory_item_name"));
        List<String> names = new ArrayList<>();
        for (WebElement element : nameElements) {
            names.add(element.getText());
        }
        return names;
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

    public int getQuantityByName(String productName) {
        By containerLocator = By.xpath("//div[normalize-space(.)='" + productName + "']/ancestor::div[contains(@class, 'cart_item')]//div[contains(@class, 'cart_quantity')]");
        String qtyText = wait.until(ExpectedConditions.visibilityOfElementLocated(containerLocator)).getText();
        return Integer.parseInt(qtyText);
    }

    public void clickRemoveByName(String productName) {
        By containerLocator = By.xpath("//div[normalize-space(.)='" + productName + "']/ancestor::div[contains(@class, 'cart_item')]//button[text()='Remove']");
        safeClick(containerLocator);
    }

    public boolean isProductInCart(String productName) {
        By containerLocator = By.xpath("//div[normalize-space(.)='" + productName + "']/ancestor::div[contains(@class, 'cart_item')]");
        return !driver.findElements(containerLocator).isEmpty();
    }

    public boolean isCartEmpty() {
        return driver.findElements(By.className("cart_item")).isEmpty();
    }

    public void clickCheckout() {
        safeClick(checkoutButton);
        wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
    }
    
    public void clickContinueShopping() {
        safeClick(continueShoppingButton);
        wait.until(ExpectedConditions.urlContains("inventory.html"));
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
