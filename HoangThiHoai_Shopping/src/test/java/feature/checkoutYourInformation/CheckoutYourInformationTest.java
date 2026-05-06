package feature.checkoutYourInformation;

import action.CartPage;
import action.CheckoutYourInformationPage;
import action.LoginPage;
import action.ProductPage;
import feature.ui.CheckoutYourInformationPageUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.BaseTest;
import utils.ChromeOptionsUtils;
import utils.ExcelUtils;

import java.util.List;
import java.util.Map;

public class CheckoutYourInformationTest extends BaseTest {
    @BeforeMethod
    public void setup() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");


        String targetProductName = "Sauce Labs Backpack";
        String targetProductName2 = "Sauce Labs Bike Light";
        ProductPage productPage = new ProductPage(driver);
        productPage.clickAddToCart(targetProductName);
        productPage.clickAddToCart(targetProductName2);
        productPage.clickCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();
    }

    @DataProvider(name = "checkoutData")
    public Object[][] getCheckoutData() {
        List<Map<String, String>> dataList = ExcelUtils.readExcelData("DataTest.xlsx", "CheckoutInfo");
        Object[][] data = new Object[dataList.size()][4];
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, String> row = dataList.get(i);
            data[i][0] = row.get("FirstName");
            data[i][1] = row.get("LastName");
            data[i][2] = row.get("ZipCode");
            data[i][3] = row.get("ExpectedResult");
        }
        return data;
    }

    @Test(dataProvider = "checkoutData")
    public void testCheckoutInfo(String firstName, String lastName, String zipCode, String expectedResult) {
        CheckoutYourInformationPage infoPage = new CheckoutYourInformationPage(driver);
        infoPage.enterInfo(firstName, lastName, zipCode);
        infoPage.clickContinue();

        if ("success".equals(expectedResult)) {
            Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two.html"), "Should navigate to checkout overview");
        } else {
            String errorMsg = infoPage.getErrorMessage();
            if ("error_first_name".equals(expectedResult)) {
                Assert.assertTrue(errorMsg.contains("First Name is required"), "Error mismatch for first name");
            } else if ("error_last_name".equals(expectedResult)) {
                Assert.assertTrue(errorMsg.contains("Last Name is required"), "Error mismatch for last name");
            } else if ("error_zip_code".equals(expectedResult)) {
                Assert.assertTrue(errorMsg.contains("Postal Code is required"), "Error mismatch for zip code");
            }
        }
    }
    @Test
    public void verifyTextYourInformation() {

        WebElement title = driver.findElement(CheckoutYourInformationPageUI.TITLE);
        String actualTitle = title.getText();
        Assert.assertEquals(actualTitle,"Checkout: Your Information");
        Assert.assertTrue(title.isDisplayed(),"Title Checkout: Your Information không displayed");

        WebElement logo= driver.findElement(CheckoutYourInformationPageUI.APP_LOGO);
        String actualLogo = logo.getText();
        Assert.assertEquals(actualLogo,"Swag Labs");
        Assert.assertTrue(logo.isDisplayed(),"Logo không displayed");

        //verify button Continue
        CheckoutYourInformationPage yourInformation = new CheckoutYourInformationPage(driver);
        Assert.assertEquals(driver.findElement(CheckoutYourInformationPageUI.CONTINUE_BUTTON).getAttribute("value"),"Continue");
        yourInformation.enterInfo("First Name", "Last Name", "Zip Code");
        yourInformation.clickContinue();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two.html"));
        driver.navigate().back();


        //verify button Cancel
        Assert.assertEquals(driver.findElement(CheckoutYourInformationPageUI.CANCEL_BUTTON).getText(),"Cancel");
        yourInformation.clickCancel();
        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"));
        driver.navigate().back();


        //verify so luong gio hang

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