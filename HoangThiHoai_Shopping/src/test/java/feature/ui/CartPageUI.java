package feature.ui;

import org.openqa.selenium.By;

public class CartPageUI {

    public static final By CHECKOUT_BUTTON = By.id("checkout");
    public static final By CONTINUE_SHOPPING_BUTTON = By.id("continue-shopping");
    public static final By CART_BADGE = By.className("shopping_cart_badge");
    public static final By CART_ICON = By.cssSelector("[data-test='shopping-cart-link']");
    public static final By TITLE = By.xpath("//span[@data-test='title']");
    public static final By APP_LOGO = By.className("app_logo");
    public  static final By TEXT_QTY=By.xpath("//div[@class='cart_quantity_label']");
    public static final By TEXT_DESCRIPTION=By.xpath("//div[@class='cart_desc_label']");
    
    public static By getItemName(String name) {
        return By.xpath("//div[text()='" + name + "']");
    }

    public static By getItemPrice(String name) {
        return By.xpath("//div[text()='" + name + "']/ancestor::div[@class='cart_item']/descendant::div[@class='inventory_item_price']");
    }

    public static By getItemDescription(String name) {
        return By.xpath("//div[text()='" + name + "']/ancestor::div[@class='cart_item']/descendant::div[@class='inventory_item_desc']");
    }

    public static By getRemoveButton(String name) {
        return By.xpath("//div[text()='" + name + "']/ancestor::div[@class='cart_item']/descendant::button");
    }
}
