package demo.test;

import demo.pages.DemoCartPage;
import demo.pages.DemoCheckoutInfoPage;
import demo.pages.DemoInventoryPage;
import demo.pages.DemoLoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ExcelUtils;

import java.util.List;
import java.util.Map;

public class DemoCheckoutTest extends DemoBaseTest {

    @BeforeMethod
    public void setupCartAndGoToCheckout() {
        DemoLoginPage loginPage = new DemoLoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        DemoInventoryPage inventoryPage = new DemoInventoryPage(driver);
        inventoryPage.clickCart();

        DemoCartPage cartPage = new DemoCartPage(driver);
        cartPage.clickCheckout();
    }

    @DataProvider(name = "checkoutData")
    public Object[][] getCheckoutData() {
        List<Map<String, String>> dataList = ExcelUtils.readExcelData("DemoTestData.xlsx", "CheckoutInfo");
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
        DemoCheckoutInfoPage infoPage = new DemoCheckoutInfoPage(driver);
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

            // UI Test: Verify error message background color
            String bgColor = infoPage.getErrorMessageBackgroundColor();
            Assert.assertTrue(bgColor.contains("rgba(226, 35, 26") || bgColor.contains("rgb(226, 35, 26"), "Background color is not red, actual: " + bgColor);
        }
    }
}
