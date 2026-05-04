package demo.test;

import demo.pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class DemoCheckoutOverviewTest extends DemoBaseTest {

    private String expectedName1;
    private String expectedPrice1;
    private String expectedName2;
    private String expectedPrice2;

    @BeforeMethod
    public void setupAndGoToOverview() {
        // 1. Login
        DemoLoginPage loginPage = new DemoLoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        // 2. Grab original info & Add multiple items to cart
        DemoInventoryPage inventoryPage = new DemoInventoryPage(driver);
        expectedName1 = "Sauce Labs Backpack";
        expectedName2 = "Sauce Labs Bike Light";
        
        expectedPrice1 = inventoryPage.getItemPrice(expectedName1);
        expectedPrice2 = inventoryPage.getItemPrice(expectedName2);
        
        inventoryPage.clickAddToCart(expectedName1);
        inventoryPage.clickAddToCart(expectedName2);
        
        // 3. Go to Cart -> Checkout
        inventoryPage.clickCart();
        DemoCartPage cartPage = new DemoCartPage(driver);
        cartPage.clickCheckout();
        
        // 4. Fill Info -> Continue
        DemoCheckoutInfoPage infoPage = new DemoCheckoutInfoPage(driver);
        infoPage.enterInfo("Test", "User", "10000");
        infoPage.clickContinue();
    }

    @Test
    public void testCheckoutOverviewDeepVerification() {
        DemoCheckoutOverviewPage overviewPage = new DemoCheckoutOverviewPage(driver);
        
        // 1. Kiểm tra chéo thông tin sản phẩm trên trang Overview
        Assert.assertEquals(overviewPage.getItemName(expectedName1), expectedName1);
        Assert.assertEquals(overviewPage.getItemPrice(expectedName1), expectedPrice1);
        
        Assert.assertEquals(overviewPage.getItemName(expectedName2), expectedName2);
        Assert.assertEquals(overviewPage.getItemPrice(expectedName2), expectedPrice2);

        // 2. Kiểm tra logic tính toán tiền
        List<Double> itemPrices = overviewPage.getIndividualItemPrices();
        
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
}
