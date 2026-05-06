package action;

import feature.ui.CheckoutYourInformationPageUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CheckoutYourInformationPage {
    private WebDriver driver;
    private WebDriverWait wait;


    public CheckoutYourInformationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterInfo(String firstName, String lastName, String zipCode) {
        driver.findElement(CheckoutYourInformationPageUI.FIRSTNAME_INPUT).clear();
        driver.findElement(CheckoutYourInformationPageUI.FIRSTNAME_INPUT).sendKeys(firstName);

        driver.findElement(CheckoutYourInformationPageUI.LASTNAME_INPUT).clear();
        driver.findElement(CheckoutYourInformationPageUI.LASTNAME_INPUT).sendKeys(lastName);

        driver.findElement(CheckoutYourInformationPageUI.ZIPCODE_INPUT).clear();
        driver.findElement(CheckoutYourInformationPageUI.ZIPCODE_INPUT).sendKeys(zipCode);
    }

    public void clickContinue() {
        driver.findElement(CheckoutYourInformationPageUI.CONTINUE_BUTTON).click();
    }

    public void clickCancel() {
        driver.findElement(CheckoutYourInformationPageUI.CANCEL_BUTTON).click();
    }

    public String getErrorMessage() {
        return driver.findElement(CheckoutYourInformationPageUI.ERROR_MESSAGE).getText();
    }

    public String getCartBadgeCount() {
        List<WebElement> badges = driver.findElements(CheckoutYourInformationPageUI.CART_BADGE);
        if (badges.isEmpty()) {
            return "";
        }
        return badges.get(0).getText();
    }

    public boolean isCartBadgeDisplayed() {
        return !driver.findElements(CheckoutYourInformationPageUI.CART_BADGE).isEmpty();
    }
}