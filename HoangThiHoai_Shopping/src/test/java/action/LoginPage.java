package action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By usernameInput = By.xpath("//input[@id='user-name']");
    private By passwordInput = By.xpath("//input[@id='password']");
    private By loginButton =By.xpath("//input[@id='login-button']");

    private  By msgError =By.cssSelector("data-test='error'");

    public LoginPage(WebDriver driver){
        this.driver=driver;
        this.wait =new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterUsername(String username){
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).clear();
        driver.findElement(usernameInput).sendKeys(username);
    }

    public void enterPassword(String password){
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLoginButton(){
        driver.findElement(loginButton).click();
    }

//    public String getErrorMessage(){
//        return driver.findElement(msgError).getText();
//    }

    public void login(String username, String password){
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        //getErrorMessage();
    }

}
