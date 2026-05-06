package feature.ui;

import org.openqa.selenium.By;

public class CheckoutYourInformationPageUI {
    public static final By FIRSTNAME_INPUT = By.id("first-name");
    public static final By LASTNAME_INPUT = By.id("last-name");
    public static final By ZIPCODE_INPUT = By.id("postal-code");
    public static final By CONTINUE_BUTTON = By.id("continue");
    public static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");
    public static final By CART_BADGE = By.className("shopping_cart_badge");
    public static final By CANCEL_BUTTON = By.id("cancel");
    public static final By TITLE = By.xpath("//span[@data-test='title']");
    public static final By APP_LOGO = By.className("app_logo");
}
