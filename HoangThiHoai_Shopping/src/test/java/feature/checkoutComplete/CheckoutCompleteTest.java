package feature.checkoutComplete;

import action.CartPage;
import action.CheckoutYourInformationPage;
import action.LoginPage;
import action.ProductPage;
import feature.ui.CartPageUI;
import feature.ui.CheckoutCompletePageUI;
import feature.ui.CheckoutOverviewPageUI;
import feature.ui.ProductPageUI;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.BaseTest;

public class CheckoutCompleteTest extends BaseTest {
    private String expectedName1;
    private String expectedName2;
    private String expectedPrice1;
    private String expectedPrice2;
    @BeforeMethod
    public void setup() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        ProductPage productPage = new ProductPage(driver);
        expectedName1= "Sauce Labs Backpack";
        expectedName2 = "Sauce Labs Bike Light";
        expectedPrice1 = driver.findElement(ProductPageUI.getItemPrice(expectedName1)).getText();
        expectedPrice2 = driver.findElement(ProductPageUI.getItemPrice(expectedName2)).getText();
        productPage.clickAddToCart(expectedName1);
        productPage.clickAddToCart(expectedName2);
        productPage.clickCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();

        CheckoutYourInformationPage checkoutInfoPage = new CheckoutYourInformationPage(driver);
        checkoutInfoPage.enterInfo("Hoai","Hoang","3214234");
        checkoutInfoPage.clickContinue();

        driver.findElement(CheckoutOverviewPageUI.FINISH_BUTTON).click();
    }
    @Test
    public void verifyUICompletePageTest(){
        WebElement title = driver.findElement(CheckoutCompletePageUI.TITLE);
        String actualTitle = title.getText();
        Assert.assertEquals(actualTitle, "Checkout: Complete!");
        Assert.assertTrue(title.isDisplayed(), "Title Products không displayed");

        WebElement logo = driver.findElement(CartPageUI.APP_LOGO);
        String actualLogo = logo.getText();
        Assert.assertEquals(actualLogo, "Swag Labs");
        Assert.assertTrue(logo.isDisplayed(), "Logo không displayed");

        Assert.assertTrue(driver.findElement(CheckoutCompletePageUI.IMAGE).getAttribute("src").contains("data:image/png;base64"));
        Assert.assertTrue(driver.findElement(CheckoutCompletePageUI.IMAGE).isDisplayed(),"Ảnh logo không displayed");

        Assert.assertEquals(driver.findElement(CheckoutCompletePageUI.COMPLETE_HEADER_LABEL).getText(), "Thank you for your order!");
        Assert.assertTrue(driver.findElement(CheckoutCompletePageUI.COMPLETE_HEADER_LABEL).isDisplayed(),"Label Thank you for your order! không displayed ");

        Assert.assertEquals(driver.findElement(CheckoutCompletePageUI.COMPLETE_TEXT_LABEL).getText(), "Your order has been dispatched, and will arrive just as fast as the pony can get there!");
        Assert.assertTrue(driver.findElement(CheckoutCompletePageUI.COMPLETE_TEXT_LABEL).isDisplayed(),"Label Your order has been không displayed ");

        Assert.assertEquals(driver.findElement(CheckoutCompletePageUI.BACK_HOME_BUTTON).getText(), "Back Home");
        Assert.assertTrue(driver.findElement(CheckoutCompletePageUI.BACK_HOME_BUTTON).isDisplayed(),"Button Back Home không displayed ");


        // Kiểm tra giỏ hàng phải trống (Badge không hiển thị)
        Assert.assertTrue(driver.findElements(CheckoutCompletePageUI.CART_BADGE).isEmpty(), "Giỏ hàng phải trống sau khi checkout thành công");

        driver.findElement(CheckoutCompletePageUI.BACK_HOME_BUTTON).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }
}
