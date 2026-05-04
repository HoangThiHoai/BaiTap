package demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DemoInventoryPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By cartIcon = By.cssSelector("[data-test='shopping-cart-link']");
    private By sortDropdown = By.className("product_sort_container");
    private By itemName = By.className("inventory_item_name");
    private By itemPrice = By.className("inventory_item_price");
    
    // Locators for text verification
    private By pageTitle = By.className("title");
    private By appLogo = By.className("app_logo");
    private By cartBadge = By.className("shopping_cart_badge");

    public DemoInventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(sortDropdown));
    }

    private void safeClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        try {
            element.click();
        } catch (Exception e) {
            try {
                new Actions(driver).moveToElement(element).click().perform();
            } catch (Exception e2) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            }
        }
    }

    public void selectSortOption(String visibleText) {
        Select dropdown = new Select(wait.until(ExpectedConditions.elementToBeClickable(sortDropdown)));
        dropdown.selectByVisibleText(visibleText);
        
        String expectedFirstItem = "";
        if (visibleText.equals("Name (Z to A)")) expectedFirstItem = "Test.allTheThings() T-Shirt (Red)";
        else if (visibleText.equals("Name (A to Z)")) expectedFirstItem = "Sauce Labs Backpack";
        else if (visibleText.equals("Price (low to high)")) expectedFirstItem = "Sauce Labs Onesie";
        else if (visibleText.equals("Price (high to low)")) expectedFirstItem = "Sauce Labs Fleece Jacket";
        
        if (!expectedFirstItem.isEmpty()) {
            wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.cssSelector(".inventory_item:nth-child(1) .inventory_item_name"), expectedFirstItem));
        }
    }

    public List<String> getAllItemNames() {
        List<WebElement> nameElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(itemName));
        List<String> names = new ArrayList<>();
        for (WebElement element : nameElements) {
            names.add(element.getText());
        }
        return names;
    }

    public List<Double> getAllItemPrices() {
        List<WebElement> priceElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(itemPrice));
        List<Double> prices = new ArrayList<>();
        for (WebElement element : priceElements) {
            String priceText = element.getText().replace("$", "");
            prices.add(Double.parseDouble(priceText));
        }
        return prices;
    }

    public String getItemName(String productName) {
        By locator = By.xpath("//div[contains(@class, 'inventory_item_name') and normalize-space(.)='" + productName + "']");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    public String getItemDescription(String productName) {
        By locator = By.xpath("//div[normalize-space(.)='" + productName + "']/ancestor::div[contains(@class, 'inventory_item')]//div[contains(@class, 'inventory_item_desc')]");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    public String getItemPrice(String productName) {
        By locator = By.xpath("//div[normalize-space(.)='" + productName + "']/ancestor::div[contains(@class, 'inventory_item')]//div[contains(@class, 'inventory_item_price')]");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    public String getItemImage(String productName) {
        By locator = By.xpath("//div[normalize-space(.)='" + productName + "']/ancestor::div[contains(@class, 'inventory_item')]//img[contains(@class, 'inventory_item_img')]");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getAttribute("src");
    }

    public String getItemButtonText(String productName) {
        By locator = By.xpath("//div[normalize-space(.)='" + productName + "']/ancestor::div[contains(@class, 'inventory_item')]//button");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    public void clickProductByName(String productName) {
        By productLink = By.xpath("//div[normalize-space(.)='" + productName + "']/ancestor::a");
        safeClick(productLink);
        wait.until(ExpectedConditions.urlContains("inventory-item.html"));
    }

    public void clickAddToCart(String name) {
        // Sử dụng xpath như yêu cầu của user
        By addToCartLink = By.xpath("//div[text()='" + name + "']/ancestor::div[@class='inventory_item_description']/descendant::button");
        safeClick(addToCartLink);
    }

    public void clickCart() {
        safeClick(cartIcon);
        wait.until(ExpectedConditions.urlContains("cart.html"));
    }

    public void goToCart() {
        clickCart();
    }

    public String getPageTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle)).getText();
    }

    public String getAppLogoText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(appLogo)).getText();
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
