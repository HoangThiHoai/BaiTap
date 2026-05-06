package action;

import feature.ui.ProductPageUI;
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
    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void selectSortOption(String value) {
        Select select = new Select(driver.findElement(ProductPageUI.SORT_OPTION));
        select.selectByVisibleText(value);

    }

    public List<String> getAllItemNames() {
        List<WebElement> elementName = driver.findElements(ProductPageUI.ITEM_NAME);
        List<String> names = new ArrayList<>();
        for (WebElement element : elementName) {
            names.add(element.getText());
        }
        return names;
    }

    public List<Double> getAllItemPrices() {
        List<WebElement> elementPrice = driver.findElements(ProductPageUI.ITEM_PRICE);
        List<Double> prices = new ArrayList<>();
        for (WebElement element : elementPrice) {
            String priceText = element.getText().replace("$", "");
            prices.add(Double.parseDouble(priceText));
        }
        return prices;
    }

    public void clickProductByName(String name) {
        driver.findElement(ProductPageUI.getItemNameLink(name)).click();
    }

    public void clickAddToCart(String name) {
        driver.findElement(ProductPageUI.getAddToCartButton(name)).click();
    }

    public void clickCart() {
        driver.findElement(ProductPageUI.CART_ICON).click();
    }

    public String getCartBadgeCount() {
        List<WebElement> badges = driver.findElements(ProductPageUI.CART_BADGE);
        if (badges.isEmpty()) {
            return "";
        }
        return badges.get(0).getText();
    }

    public boolean isCartBadgeDisplayed() {
        return !driver.findElements(ProductPageUI.CART_BADGE).isEmpty();
    }
}

