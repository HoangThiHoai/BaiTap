package feature.ui;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;

public class ProductPageUI {

    public static final By SORT_OPTION = By.cssSelector("[data-test='product-sort-container']");
    public static final By CART_ICON = By.cssSelector("[data-test='shopping-cart-link']");
    public static final By ITEM_NAME = By.cssSelector("[data-test='inventory-item-name']");
    public static final By ITEM_PRICE = By.cssSelector("[data-test='inventory-item-price']");
    public static final By CART_BADGE = By.className("shopping_cart_badge");
    public static final By TITLE = By.xpath("//span[@data-test='title']");
    public static final By APP_LOGO       = By.className("app_logo");

    public static By getItemName(String name) {
        return By.xpath("//div[text()='" + name + "']");
    }

    public static By getItemPrice(String name) {
        return By.xpath("//div[text()='" + name + "']/ancestor::div[@class='inventory_item_description']/descendant::div[@class='inventory_item_price']");
    }

    public static By getItemDescription(String name) {
        return By.xpath("//div[text()='" + name + "']/ancestor::div[@class='inventory_item_description']/descendant::div[@class='inventory_item_desc']");
    }

    public static By getItemImage(String name) {
        return By.xpath("//img[@alt='" + name + "']");
    }

    public static By getAddToCartButton(String name) {
        return By.xpath("//div[text()='" + name + "']/ancestor::div[@class='inventory_item_description']/descendant::button");
    }

    public static By getItemNameLink(String name) {
        return By.xpath("//div[text()='" + name + "']/ancestor::div[@class='inventory_item']/descendant::div[@data-test='inventory-item-name']");
    }
}
