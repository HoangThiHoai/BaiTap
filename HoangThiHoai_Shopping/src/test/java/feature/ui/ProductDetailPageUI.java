package feature.ui;

import org.openqa.selenium.By;

public class ProductDetailPageUI {

    public static final By PRODUCT_NAME = By.cssSelector("[data-test='inventory-item-name']");
    public static final By PRODUCT_DESC = By.cssSelector("[data-test='inventory-item-desc']");
    public static final By PRODUCT_PRICE = By.cssSelector("[data-test='inventory-item-price']");
    public static final By ADD_TO_CART_BUTTON = By.cssSelector("[data-test='add-to-cart']");
    public static final By REMOVE_BUTTON = By.cssSelector("[data-test='remove']");
    public static final By BACK_TO_PRODUCT_BUTTON = By.cssSelector("[data-test='back-to-products']");
    public static final By PRODUCT_IMAGE = By.cssSelector("[data-test='item-sauce-labs-backpack-img']");
    public static final By CART_BADGE = By.className("shopping_cart_badge");
}
