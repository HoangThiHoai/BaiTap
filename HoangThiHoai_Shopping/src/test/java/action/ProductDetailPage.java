package action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductDetailPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By productName = By.cssSelector("[data-test='inventory-item-name']");
    private By productDesc = By.cssSelector("[data-test='inventory-item-desc']");
    private By productPrice = By.cssSelector("[data-test='inventory-item-price']");
    private By addToCartButton = By.cssSelector("[data-test='add-to-cart']");
    private By backToProductsButton = By.id("back-to-products");
    private By productImage = By.className("inventory_details_img");
    private By cartBadge = By.className("shopping_cart_badge");


    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //wait.until(ExpectedConditions.visibilityOfElementLocated(backToProductsButton));
    }
    public WebElement getItemName() {
        return driver.findElement(productName);
    }

    public WebElement getItemPrice() {
        return driver.findElement(productPrice);
    }

    public WebElement getItemDescription() {
        return driver.findElement(productDesc);
    }

    public WebElement getItemImage() {
        return driver.findElement(productImage);
    }

    public WebElement getButtonText() {
        return driver.findElement(addToCartButton);
    }

    public void clickProductByName(String name) {
        By nameLink = By.xpath("//div[text()='" + name + "']/ancestor::div[@class='inventory_item']");
        driver.findElement(nameLink).click();

    }

    public void clickAddToCart() {
        driver.findElement(addToCartButton).click();
    }

    public void clickBackToProducts() {
        driver.findElement(backToProductsButton).click();
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

