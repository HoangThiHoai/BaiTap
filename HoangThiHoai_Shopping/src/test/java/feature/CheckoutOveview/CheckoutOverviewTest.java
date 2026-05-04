package feature.CheckoutOveview;

import action.CheckoutOverviewPage;
import action.LoginPage;
import action.YourInformation;
import demo.pages.DemoCartPage;
import demo.pages.DemoCheckoutInfoPage;
import demo.pages.DemoCheckoutOverviewPage;
import demo.pages.DemoInventoryPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ChromeOptionsUtils;

import java.util.List;

public class CheckoutOverviewTest {
    WebDriver driver;
    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver(ChromeOptionsUtils.getChromeOptions());
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");


        String targetProductName = "Sauce Labs Backpack";
        String targetProductName2 = "Sauce Labs Bike Light";
        DemoInventoryPage inventoryPage = new DemoInventoryPage(driver);
        inventoryPage.clickAddToCart(targetProductName);
        inventoryPage.clickAddToCart(targetProductName2);
        inventoryPage.clickCart();

        DemoCartPage cartPage = new DemoCartPage(driver);
        cartPage.clickCheckout();

        DemoCheckoutInfoPage checkoutInfoPage = new DemoCheckoutInfoPage(driver);
        checkoutInfoPage.enterInfo("Hoai","Hoang","3214234");
        checkoutInfoPage.clickContinue();
    }
    @Test
    public void verifyTextCheckoutOverview() {

        WebElement title = driver.findElement(By.xpath("//span[@data-test='title']"));
        String actualTitle = title.getText();
        Assert.assertEquals(actualTitle,"Checkout: Overview");
        Assert.assertTrue(title.isDisplayed(),"Title Checkout: Checkout: Overview không displayed");

        WebElement logo= driver.findElement(By.xpath("//div[@class='app_logo']"));
        String actualLogo = logo.getText();
        Assert.assertEquals(actualLogo,"Swag Labs");
        Assert.assertTrue(logo.isDisplayed(),"Logo không displayed");

        //verify san pham
        CheckoutOverviewPage  checkoutOverviewPage = new CheckoutOverviewPage(driver);
        String targetName ="Sauce Labs Backpack";
        Assert.assertEquals(checkoutOverviewPage.getItemPrice(targetName).getText(),"$29.99");
        Assert.assertTrue(checkoutOverviewPage.getItemName(targetName).isDisplayed(),"Text giá bán chưa không hiển thị");

        Assert.assertEquals(checkoutOverviewPage.getItemDescription(targetName).getText(),"carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.");
        Assert.assertTrue(checkoutOverviewPage.getItemDescription(targetName).isDisplayed(),"Text Mô tả chưa hiển thị");

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
        String actualPaymentLabel = driver.findElement(By.xpath("//div[@data-test='payment-info-label']")).getText();
        Assert.assertEquals(actualPaymentLabel,"Payment Information:");

        String actualPaymentValue = driver.findElement(By.xpath("//div[@data-test='payment-info-value']")).getText();
        Assert.assertEquals(actualPaymentValue,"SauceCard #31337");

        String  actualShippingLable =driver.findElement(By.xpath("//div[@data-test='shipping-info-label']")).getText();
        Assert.assertEquals(actualShippingLable,"Shipping Information:");

        String actualShippingValue =driver.findElement(By.xpath("//div[@data-test='shipping-info-value']")).getText();
        Assert.assertEquals(actualShippingValue,"Free Pony Express Delivery!");

        String actualTotalInfoLabel =driver.findElement(By.xpath("//div[@data-test='total-info-label']")).getText();
        Assert.assertEquals(actualTotalInfoLabel,"Price Total");

        //text item...

    }
    @Test
    public void testCheckoutOverview() {
        CheckoutOverviewPage overviewPage = new CheckoutOverviewPage(driver);

        // 1. Kiểm tra chéo thông tin sản phẩm trên trang Overview
        Assert.assertEquals(overviewPage.getItemName(expectedName1), expectedName1);
        Assert.assertEquals(overviewPage.getItemPrice(expectedName1), expectedPrice1);

        Assert.assertEquals(overviewPage.getItemName(expectedName2), expectedName2);
        Assert.assertEquals(overviewPage.getItemPrice(expectedName2), expectedPrice2);

        // 2. Kiểm tra logic tính toán tiền
        List<Double> itemPrices = overviewPage.getItemPrices();

        double expectedSubtotal = 0;
        for (Double price : itemPrices) {
            expectedSubtotal += price;
        }

        double actualSubtotal = overviewPage.getSubtotal();
        double actualTax = overviewPage.getTax();
        double actualTotal = overviewPage.getTotal();

        // So sánh
        Assert.assertEquals(actualSubtotal, expectedSubtotal, 0.01);
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
