package feature.checkoutYourInformation;

import action.CartPage;
import action.CheckoutYourInformationPage;
import action.LoginPage;
import action.ProductPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ChromeOptionsUtils;
import utils.ExcelUtils;

import java.util.List;
import java.util.Map;

public class CheckoutYourInformationTest {
    WebDriver driver;
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

    @BeforeMethod
    public void loginBeforeTest() {
        ChromeOptions chromeOptions = ChromeOptionsUtils.GetChromeOptionsUtils();
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        ProductPage productPage = new ProductPage(driver);
        String targetName = "Sauce Labs Backpack";
        String targetName2 = "Sauce Labs Bike Light";
        productPage.clickAddToCart(targetName);
        productPage.clickAddToCart(targetName2);
        By cartIcon = By.cssSelector("[data-test='shopping-cart-link']");
        driver.findElement(cartIcon).click();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();
    }
    @Test(dataProvider = "checkoutData")
    public void testCheckoutInfo(String firstName, String lastName, String zipCode, String expectedResult) {
        CheckoutYourInformationPage checkoutYourInformationPage = new CheckoutYourInformationPage(driver);
        checkoutYourInformationPage.enterInfo(firstName, lastName, zipCode);
        checkoutYourInformationPage.clickContinue();

        if ("success".equals(expectedResult)) {
            Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two.html"), "Should navigate to checkout overview");
        } else {
            String errorMsg = checkoutYourInformationPage.getErrorMessage().getText();
            if ("error_first_name".equals(expectedResult)) {
                Assert.assertTrue(errorMsg.contains("First Name is required"), "Error mismatch for first name");
            } else if ("error_last_name".equals(expectedResult)) {
                Assert.assertTrue(errorMsg.contains("Last Name is required"), "Error mismatch for last name");
            } else if ("error_zip_code".equals(expectedResult)) {
                Assert.assertTrue(errorMsg.contains("Postal Code is required"), "Error mismatch for zip code");
            }
        }
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
