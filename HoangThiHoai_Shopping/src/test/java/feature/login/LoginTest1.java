package feature.login;

import action.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ExcelUtils;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class LoginTest1 {
    WebDriver driver;
    @DataProvider (name = "loginData")
    public Object[][] getLoginData(){
        List<Map<String, String>> excelData = ExcelUtils.readExcelData("DataTest.xlsx","Login");
        Object[][] data = new Object[excelData.size()][3];
        for (int i = 0; i < excelData.size(); i++) {
            Map<String, String> rowData = excelData.get(i);
            data[i][0]=rowData.get("Username");
            data[i][1]=rowData.get("Password");
            data[i][2]=rowData.get("ER");
        }
        return data;
    }
    @BeforeMethod
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }
    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password, String ER) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        if("Pass".equals(ER)){
            Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
            WebElement logo = driver.findElement(By.className("app_logo"));
            Assert.assertTrue(logo.isDisplayed(), "app-logo is not displayed.");
            int itemCount = driver.findElements(By.className("inventory_item")).size();
            Assert.assertTrue(itemCount > 0, "inventory-item is not found.");
        } else if ("Fail".equals(ER)) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));
            Assert.assertTrue(errorMsg.isDisplayed(), "Not displayed error message.");
            Assert.assertTrue(errorMsg.getText().contains("Epic sadface: Username and password do not match any user in this service"));

        }else if ("emptyUser".equals(ER)){
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));
            Assert.assertTrue(errorMsg.isDisplayed(), "Not displayed error message.");
            Assert.assertTrue(errorMsg.getText().contains("Epic sadface: Username is required"));

        } else if ("emptyPass".equals(ER)) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));
            Assert.assertTrue(errorMsg.isDisplayed(), "Not displayed error message.");
            Assert.assertTrue(errorMsg.getText().contains("Epic sadface: Password is required"));

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
