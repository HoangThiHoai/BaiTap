package action;

import feature.ui.CartPageUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickProductByName(String name) {
        driver.findElement(CartPageUI.getItemName(name)).click();
    }

    public void clickRemoveButton(String name) {
        driver.findElement(CartPageUI.getRemoveButton(name)).click();
    }
    public void clickCheckout() {
        driver.findElement(CartPageUI.CHECKOUT_BUTTON).click();
        wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
    }

    public void clickContinueShopping() {
        driver.findElement(CartPageUI.CONTINUE_SHOPPING_BUTTON).click();
        wait.until(ExpectedConditions.urlContains("inventory.html"));
    }

    public String getCartBadgeCount() {
        List<WebElement> badges = driver.findElements(CartPageUI.CART_BADGE);
        if (badges.isEmpty()) {
            return "";
        }
        return badges.get(0).getText();
    }

    public boolean isCartBadgeDisplayed() {
        return !driver.findElements(CartPageUI.CART_BADGE).isEmpty();
    }
    public boolean isCartEmpty() {
        return driver.findElements(CartPageUI.CART_ITEM).isEmpty();
    }

    public int getCartItemsCount() {
        return driver.findElements(CartPageUI.CART_ITEM).size();
    }

    public String getItemQuantity(String name) {
        return driver.findElement(CartPageUI.getItemQuantity(name)).getText();
    }

}


