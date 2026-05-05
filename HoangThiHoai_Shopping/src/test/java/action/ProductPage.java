package action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ProductPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By sortOption = By.cssSelector("[data-test='product-sort-container']");
    private By cartIcon = By.cssSelector("[data-test='shopping-cart-link']");
    private By itemName = By.cssSelector("[data-test='inventory-item-name']");
    private By itemPrice = By.cssSelector("[data-test='inventory-item-price']");
    private By cartBadge = By.className("shopping_cart_badge");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void selectSortOption(String value) {
        Select select = new Select(driver.findElement(sortOption));
        select.selectByVisibleText(value);

    }

    public List<String> getAllItemNames() {
        List<WebElement> elementName = driver.findElements(itemName);
        List<String> names = new ArrayList<>();
        for (WebElement element : elementName) {
            names.add(element.getText());
        }
        return names;
    }

    public List<Double> getAllItemPrices() {
        List<WebElement> elementPrice = driver.findElements(itemPrice);
        List<Double> prices = new ArrayList<>();
        for (WebElement element : elementPrice) {
            String priceText = element.getText().replace("$", "");
            prices.add(Double.parseDouble(priceText));
        }
        return prices;
    }

    public WebElement getItemName(String name) {
        WebElement itemName = driver.findElement(By.xpath("//div[text()='" + name + "']"));
        return itemName;
    }

    public WebElement getItemPrice(String name) {
        WebElement itemPrice = driver.findElement(By.xpath("//div[text()='" + name + "']/ancestor::div[@class='inventory_item_description']/descendant::div[@class='inventory_item_price']"));
        return itemPrice;
    }

    public WebElement getItemDescription(String name) {
        WebElement itemDescription = driver.findElement(By.xpath("//div[text()='" + name + "']/ancestor::div[@class='inventory_item_description']/descendant::div[@class='inventory_item_desc']"));
        return itemDescription;
    }

    public WebElement getItemImage(String name) {
        WebElement itemImage = driver.findElement(By.xpath("//img[@alt='" + name + "']"));
        return itemImage;
    }

    public WebElement getButtonText(String name) {
        WebElement itemButton = driver.findElement(By.xpath(" //div[text()='" + name + "']/ancestor::div[@class='inventory_item_description']/descendant::button"));
        return itemButton;
    }

    public void clickProductByName(String name) {
        By nameLink = By.xpath("//div[text()='" + name + "']/ancestor::div[@class='inventory_item']/descendant::div[@data-test='inventory-item-name']");
        driver.findElement(nameLink).click();

    }

    public void clickAddToCart(String name) {
        By addToCartLink = By.xpath("//div[text()='" + name + "']/ancestor::div[@class='inventory_item_description']/descendant::button");
        driver.findElement(addToCartLink).click();
    }

    public void clickCart() {
        driver.findElement(cartIcon).click();
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

