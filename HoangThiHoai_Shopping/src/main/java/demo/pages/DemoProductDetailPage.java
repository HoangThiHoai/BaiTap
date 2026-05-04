package demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

import java.time.Duration;

public class DemoProductDetailPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By productName = By.className("inventory_details_name");
    private By productDesc = By.className("inventory_details_desc");
    private By productPrice = By.className("inventory_details_price");
    private By addToCartButton = By.cssSelector(".btn_inventory");
    private By backToProductsButton = By.id("back-to-products");
    private By productImage = By.className("inventory_details_img");
    private By cartBadge = By.className("shopping_cart_badge");

    public DemoProductDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Verify we are on the product detail page
        wait.until(ExpectedConditions.visibilityOfElementLocated(backToProductsButton));
    }

    private void safeClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public String getProductName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
    }

    public String getProductDescription() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productDesc)).getText();
    }

    public String getProductPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productPrice)).getText();
    }

    public String getProductImage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productImage)).getAttribute("src");
    }

    public String getAddToCartButtonText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartButton)).getText();
    }

    public void clickAddToCart() {
        safeClick(addToCartButton);
    }

    public void clickBackToProducts() {
        safeClick(backToProductsButton);
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
