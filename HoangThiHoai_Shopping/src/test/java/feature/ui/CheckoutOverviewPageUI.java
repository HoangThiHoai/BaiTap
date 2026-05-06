package feature.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CheckoutOverviewPageUI {

    public static final By FINISH_BUTTON = By.id("finish");
    public static final By ITEM_PRICES = By.className("inventory_item_price");
    public static final By SUBTOTAL_LABEL = By.className("summary_subtotal_label");
    public static final By TAX_LABEL = By.className("summary_tax_label");
    public static final By TOTAL_LABEL = By.className("summary_total_label");
    public static final By CART_BADGE = By.className("shopping_cart_badge");
    public static final By CANCEL_BUTTON = By.id("cancel");
    public static final By TITLE = By.xpath("//span[@data-test='title']");
    public static final By APP_LOGO = By.className("app_logo");
    public static final By PAYMENT_LABEL = By.xpath("//div[@data-test='payment-info-label']");
    public static final By PAYMENT_VALUE = By.xpath("//div[@data-test='payment-info-value']");
    public static final By SHIPPING_LABEL = By.xpath("//div[@data-test='shipping-info-label']");
    public static final By SHIPPING_VALUE = By.xpath("//div[@data-test='shipping-info-value']");
    public static final By TOTAL_INFO_LABEL = By.xpath("//div[@data-test='total-info-label']");

    public static By getItemName(String name) {
        return By.xpath("//div[text()='" + name + "']");
    }

    public static By getItemPrice(String name) {
        return By.xpath("//div[text()='" + name + "']/ancestor::div[@class='cart_item']/descendant::div[@class='inventory_item_price']");
    }

    public static By getItemDescription(String name) {
        return By.xpath("//div[text()='" + name + "']/ancestor::div[@class='cart_item']/descendant::div[@class='inventory_item_desc']");
    }

}
