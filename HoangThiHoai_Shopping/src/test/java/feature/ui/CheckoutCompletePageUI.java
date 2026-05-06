package feature.ui;

import org.openqa.selenium.By;

public class CheckoutCompletePageUI {
    public static final By TITLE = By.xpath("//span[@data-test='title']");
    public static final By APP_LOGO = By.className("app_logo");
    public static final By IMAGE = By.cssSelector("[data-test='pony-express']");
    public static final By COMPLETE_HEADER_LABEL = By.cssSelector("[data-test='complete-header']");
    public static final By COMPLETE_TEXT_LABEL= By.cssSelector("[data-test='complete-text']");
    public static final By BACK_HOME_BUTTON = By.cssSelector("[data-test='back-to-products']");
    public static final By CART_BADGE = By.className("shopping_cart_badge");
}
