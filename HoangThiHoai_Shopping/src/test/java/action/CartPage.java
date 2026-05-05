package action;

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

    private By checkoutButton = By.id("checkout");
    private By continueShoppingButton = By.id("continue-shopping");
    private By cartBadge = By.className("shopping_cart_badge");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebElement getItemName(String name) {
        WebElement itemName = driver.findElement(By.xpath("//div[text()='" + name + "']"));
        return itemName;
    }

    public WebElement getItemPrice(String name) {
        WebElement itemPrice = driver.findElement(By.xpath("//div[text()='" + name + "']/ancestor::div[@class='cart_item']/descendant::div[@class='inventory_item_price']"));
        return itemPrice;
    }

    public WebElement getItemDescription(String name) {
        WebElement itemDescription = driver.findElement(By.xpath("//div[text()='" + name + "']/ancestor::div[@class='cart_item']/descendant::div[@class='inventory_item_desc']"));
        return itemDescription;
    }


    public WebElement getButtonText(String name) {
        WebElement itemButton = driver.findElement(By.xpath(" //div[text()='" + name + "']/ancestor::div[@class='cart_item']/descendant::button"));
        return itemButton;
    }

    public void clickProductByName(String name) {
        By nameLink = By.xpath("//div[text()='" + name + "']");
        driver.findElement(nameLink).click();

    }

    public void clickRemoveButton(String name) {
        By removeButton = By.xpath("//div[text()='" + name + "']/ancestor::div[@class='cart_item']/descendant::button");
        driver.findElement(removeButton).click();
    }
    public void clickCheckout() {
        driver.findElement(checkoutButton).click();
        wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
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

