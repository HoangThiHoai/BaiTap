package feature.login;

import action.LoginPage;
import feature.ui.LoginPageUI;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.BaseTest;
import utils.ExcelUtils;

import java.util.List;
import java.util.Map;

public class LoginTest extends BaseTest {
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        List<Map<String, String>> dataList = ExcelUtils.readExcelData("DataTest.xlsx", "Login");
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
    public void testLogin(String username, String password, String ExpectedResult) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        if ("Pass".equals(ExpectedResult)) {
            Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
            WebElement logo = driver.findElement(LoginPageUI.APP_LOGO);
            Assert.assertTrue(logo.isDisplayed(), "app-logo is not displayed.");
            int itemCount = driver.findElements(LoginPageUI.INVENTORY_ITEM).size();
            Assert.assertTrue(itemCount > 0, "inventory-item is not found.");
        } else if ("Fail".equals(ExpectedResult)) {
            WebElement errorMsg = driver.findElement(LoginPageUI.ERROR_MESSAGE);
            Assert.assertTrue(errorMsg.isDisplayed(), "Not displayed error message.");
            Assert.assertTrue(errorMsg.getText().contains("Epic sadface: Username and password do not match any user in this service"));

        } else if ("emptyUser".equals(ExpectedResult)) {
            WebElement errorMsg = driver.findElement(LoginPageUI.ERROR_MESSAGE);
            Assert.assertTrue(errorMsg.isDisplayed(), "Not displayed error message.");
            Assert.assertTrue(errorMsg.getText().contains("Epic sadface: Username is required"));

        } else if ("emptyPass".equals(ExpectedResult)) {
            WebElement errorMsg = driver.findElement(LoginPageUI.ERROR_MESSAGE);
            Assert.assertTrue(errorMsg.isDisplayed(), "Not displayed error message.");
            Assert.assertTrue(errorMsg.getText().contains("Epic sadface: Password is required"));

        }
    }
}
