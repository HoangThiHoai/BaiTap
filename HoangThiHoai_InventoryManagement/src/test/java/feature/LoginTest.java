package feature;

import action.LoginActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {
    WebDriver driver;

    @BeforeMethod
    public void setup(){
        // Khởi tạo trình duyệt mới cho mỗi test case để đảm bảo tính độc lập tuyệt đối
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void testLoginWithValidCredentials() {
        LoginActions.performLogin(driver, "standard_user", "secret_sauce");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");

        WebElement logo = driver.findElement(By.className("app_logo"));
        Assert.assertTrue(logo.isDisplayed(), "app-logo is not displayed.");

        int itemCount = driver.findElements(By.className("inventory_item")).size();
        Assert.assertTrue(itemCount > 0, "inventory-item is not found.");
    }

    @Test
    public void testLoginWithInValidUsername(){
        LoginActions.performLogin(driver, "standard_user234324", "standard_user");

        // Sử dụng Explicit Wait để đảm bảo thông báo lỗi xuất hiện
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));

        Assert.assertTrue(errorMsg.isDisplayed(), "Not displayed error message.");
        Assert.assertTrue(errorMsg.getText().contains("Epic sadface: Username and password do not match any user in this service"));
    }

    @Test
    public void testLoginWithInValidPassword(){
        LoginActions.performLogin(driver, "standard_user", "standard_user6666");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));

        Assert.assertTrue(errorMsg.isDisplayed(), "Not displayed error message.");
        Assert.assertTrue(errorMsg.getText().contains("Epic sadface: Username and password do not match any user in this service"));
    }

    @Test
    public void testLoginWithEmptyUsername(){
        LoginActions.performLogin(driver, "", "standard_user");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));

        Assert.assertTrue(errorMsg.isDisplayed(), "Not displayed error message.");
        Assert.assertTrue(errorMsg.getText().contains("Epic sadface: Username is required"));
    }

    @Test
    public void testLoginWithEmptyPassword(){
        LoginActions.performLogin(driver, "standard_user", "");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));

        Assert.assertTrue(errorMsg.isDisplayed(), "Not displayed error message.");
        Assert.assertTrue(errorMsg.getText().contains("Epic sadface: Password is required"));
    }

    @AfterMethod
    public void tearDown(){
        System.out.println("Kết thúc 1 TC - Đã đóng trình duyệt.");
        if (driver != null) {
            driver.quit();
        }
    }
}
