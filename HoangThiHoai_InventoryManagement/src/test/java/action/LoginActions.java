package action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginActions {
    public static void performLogin(WebDriver driver, String username, String password) {
        WebElement userInp = driver.findElement(By.id("user-name"));
        WebElement passInp = driver.findElement(By.id("password"));
        WebElement loginBtn = driver.findElement(By.id("login-button"));

        userInp.sendKeys(username);
        passInp.sendKeys(password);
        loginBtn.click();
    }

}