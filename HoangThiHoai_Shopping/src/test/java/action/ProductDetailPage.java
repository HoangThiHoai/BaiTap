package action;

import feature.ui.ProductDetailPageUI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductDetailPage {
    private WebDriver driver;
    private WebDriverWait wait;


    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(ProductDetailPageUI.BACK_TO_PRODUCT_BUTTON));
    }
    public WebElement getItemName() {
        return driver.findElement(ProductDetailPageUI.PRODUCT_NAME);
    }

    public WebElement getItemPrice() {
        return driver.findElement(ProductDetailPageUI.PRODUCT_PRICE);
    }

    public WebElement getItemDescription() {
        return driver.findElement(ProductDetailPageUI.PRODUCT_DESC);
    }

    public WebElement getItemImage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ProductDetailPageUI.PRODUCT_IMAGE));
        return driver.findElement(ProductDetailPageUI.PRODUCT_IMAGE);
    }

    public WebElement getButtonAddToCartText() {
        return driver.findElement(ProductDetailPageUI.ADD_TO_CART_BUTTON);
    }

    public WebElement getButtonRemoveText() {
        return driver.findElement(ProductDetailPageUI.REMOVE_BUTTON);
    }

    public void clickAddToCart() {
        driver.findElement(ProductDetailPageUI.ADD_TO_CART_BUTTON).click();
    }
    public void clickRemove() {
        driver.findElement(ProductDetailPageUI.REMOVE_BUTTON).click();
    }

    public void clickBackToProducts() {
        driver.findElement(ProductDetailPageUI.BACK_TO_PRODUCT_BUTTON).click();
    }

    public String getCartBadgeCount() {
        List<WebElement> badges = driver.findElements(ProductDetailPageUI.CART_BADGE);
        if (badges.isEmpty()) {
            return "";
        }
        return badges.get(0).getText();
    }

    public boolean isCartBadgeDisplayed() {
        return !driver.findElements(ProductDetailPageUI.CART_BADGE).isEmpty();
    }
}

