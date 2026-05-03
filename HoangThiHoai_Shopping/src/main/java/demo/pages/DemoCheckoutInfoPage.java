package demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

import java.time.Duration;

public class DemoCheckoutInfoPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By firstNameInput = By.id("first-name");
    private By lastNameInput = By.id("last-name");
    private By zipCodeInput = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By errorMessage = By.cssSelector("[data-test='error']");
    private By errorContainer = By.cssSelector(".error-message-container.error");
    private By cartBadge = By.className("shopping_cart_badge");

    public DemoCheckoutInfoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(continueButton));
    }

    private void safeClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public void enterInfo(String firstName, String lastName, String zipCode) {
        driver.findElement(firstNameInput).clear();
        driver.findElement(firstNameInput).sendKeys(firstName);
        
        driver.findElement(lastNameInput).clear();
        driver.findElement(lastNameInput).sendKeys(lastName);
        
        driver.findElement(zipCodeInput).clear();
        driver.findElement(zipCodeInput).sendKeys(zipCode);
    }

    public void clickContinue() {
        safeClick(continueButton);
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }
    
    public String getErrorMessageBackgroundColor() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorContainer)).getCssValue("background-color");
    }

    public String getCartBadgeCount() {
        List<WebElement> badges = driver.findElements(cartBadge);
        if (badges.isEmpty()) {
            return "";
        }
        return badges.get(0).getText();
    }

    public boolean isCartBadgeDisplayed() {
        return !driver.findElements(cartBadge).isEmpty();
    }
}
