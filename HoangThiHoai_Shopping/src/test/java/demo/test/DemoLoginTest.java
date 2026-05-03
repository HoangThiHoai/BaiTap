package demo.test;

import demo.pages.DemoLoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ExcelUtils;

import java.util.List;
import java.util.Map;

public class DemoLoginTest extends DemoBaseTest {

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        List<Map<String, String>> dataList = ExcelUtils.readExcelData("DemoTestData.xlsx", "Login");
        Object[][] data = new Object[dataList.size()][3];
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, String> row = dataList.get(i);
            data[i][0] = row.get("Username");
            data[i][1] = row.get("Password");
            data[i][2] = row.get("ExpectedResult");
        }
        return data;
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password, String expectedResult) {
        DemoLoginPage loginPage = new DemoLoginPage(driver);
        loginPage.login(username, password);

        if ("success".equals(expectedResult)) {
            // Verify successful login by checking the URL or presence of products
            Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Login failed for valid user");
        } else if ("locked_out".equals(expectedResult)) {
            String errorMsg = loginPage.getErrorMessage();
            Assert.assertTrue(errorMsg.contains("locked out"), "Error message mismatch");
            
            // UI Test: Verify error message background is red
            String bgColor = loginPage.getErrorMessageBackgroundColor();
            // In SauceDemo, the error background is rgba(226, 35, 26, 1) or similar red color.
            Assert.assertTrue(bgColor.contains("rgba(226, 35, 26") || bgColor.contains("rgb(226, 35, 26"), "Background color is not red, actual: " + bgColor);
        } else if ("empty".equals(expectedResult)) {
            String errorMsg = loginPage.getErrorMessage();
            Assert.assertTrue(errorMsg.contains("Username is required"), "Error message mismatch");
        }
    }
}
