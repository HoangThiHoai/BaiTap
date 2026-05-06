package action;

import feature.ui.LoginPageUI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;


    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterUsername(String username) {
        driver.findElement(LoginPageUI.USER_NAME).clear();
        driver.findElement(LoginPageUI.USER_NAME).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(LoginPageUI.PASSWORD).clear();
        driver.findElement(LoginPageUI.PASSWORD).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(LoginPageUI.LOGIN_BUTTON).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

}
