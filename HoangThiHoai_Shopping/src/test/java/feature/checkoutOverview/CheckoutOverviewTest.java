package feature.checkoutOverview;

import action.*;
import feature.ui.CheckoutOverviewPageUI;
import feature.ui.ProductPageUI;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.BaseTest;

import java.util.List;

public class CheckoutOverviewTest extends BaseTest {
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
    }
    @Test
    public void verifyTextCheckoutOverview() {

        WebElement title = driver.findElement(CheckoutOverviewPageUI.TITLE);
        String actualTitle = title.getText();
        Assert.assertEquals(actualTitle,"Checkout: Overview");
        Assert.assertTrue(title.isDisplayed(),"Title Checkout: Checkout: Overview không displayed");

        WebElement logo= driver.findElement(CheckoutOverviewPageUI.APP_LOGO);
        String actualLogo = logo.getText();
        Assert.assertEquals(actualLogo,"Swag Labs");
        Assert.assertTrue(logo.isDisplayed(),"Logo không displayed");

        //verify san pham
        CheckoutOverviewPage  checkoutOverviewPage = new CheckoutOverviewPage(driver);
        String targetName ="Sauce Labs Backpack";
        Assert.assertEquals(driver.findElement(CheckoutOverviewPageUI.getItemPrice(targetName)).getText(),"$29.99");
        Assert.assertTrue(driver.findElement(CheckoutOverviewPageUI.getItemName(targetName)).isDisplayed(),"Text giá bán chưa không hiển thị");

        Assert.assertEquals(driver.findElement(CheckoutOverviewPageUI.getItemDescription(targetName)).getText(),"carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.");
        Assert.assertTrue(driver.findElement(CheckoutOverviewPageUI.getItemDescription(targetName)).isDisplayed(),"Text Mô tả chưa hiển thị");

        //SL,header

        //verify so luong gio hang


        //verify button Finish
        Assert.assertEquals(checkoutOverviewPage.getButtonFinishText().getText(),"Finish");
        checkoutOverviewPage.clickFinish();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete.html"));
        driver.navigate().back();



        //verify button Cancel

        Assert.assertEquals(checkoutOverviewPage.getButtonCancelText().getText(),"Cancel");
        checkoutOverviewPage.clickCancel();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
        driver.navigate().back();

        //verify text group thanh toan
        String actualPaymentLabel = driver.findElement(CheckoutOverviewPageUI.PAYMENT_LABEL).getText();
        Assert.assertEquals(actualPaymentLabel,"Payment Information:");

        String actualPaymentValue = driver.findElement(CheckoutOverviewPageUI.PAYMENT_VALUE).getText();
        Assert.assertEquals(actualPaymentValue,"SauceCard #31337");

        String  actualShippingLable =driver.findElement(CheckoutOverviewPageUI.SHIPPING_LABEL).getText();
        Assert.assertEquals(actualShippingLable,"Shipping Information:");

        String actualShippingValue =driver.findElement(CheckoutOverviewPageUI.SHIPPING_VALUE).getText();
        Assert.assertEquals(actualShippingValue,"Free Pony Express Delivery!");

        String actualTotalInfoLabel =driver.findElement(CheckoutOverviewPageUI.TOTAL_INFO_LABEL).getText();
        Assert.assertEquals(actualTotalInfoLabel,"Price Total");


    }
    @Test
    public void testCheckoutOverview() {
        CheckoutOverviewPage overviewPage = new CheckoutOverviewPage(driver);

        // 1. Kiểm tra chéo thông tin sản phẩm trên trang Overview và Product
        Assert.assertEquals(driver.findElement(CheckoutOverviewPageUI.getItemName(expectedName1)).getText(), expectedName1);
        Assert.assertEquals(driver.findElement(CheckoutOverviewPageUI.getItemPrice(expectedName1)).getText(), expectedPrice1);

        Assert.assertEquals(driver.findElement(CheckoutOverviewPageUI.getItemName(expectedName2)).getText(), expectedName2);
        Assert.assertEquals(driver.findElement(CheckoutOverviewPageUI.getItemPrice(expectedName2)).getText(), expectedPrice2);

        // 2. Kiểm tra logic tính toán tiền
        List<Double> itemPrices = overviewPage.getItemPrices();

        double expectedSubtotal = 0;
        double expectedTax = 0;
        for (Double price : itemPrices) {
            expectedSubtotal += price;
            expectedTax += price*0.08;
        }

        double actualSubtotal = overviewPage.getSubtotal();
        double actualTax = overviewPage.getTax();
        double actualTotal = overviewPage.getTotal();

        // So sánh
        Assert.assertEquals(actualSubtotal, expectedSubtotal, 0.01);
        Assert.assertEquals(actualTax,expectedTax, 0.01);
        Assert.assertEquals(actualTotal, actualSubtotal + actualTax, 0.01);
    }
    @AfterMethod
    public void tearDown()
    {
        if (driver != null)
        {
            driver.quit();
        }
    }
}