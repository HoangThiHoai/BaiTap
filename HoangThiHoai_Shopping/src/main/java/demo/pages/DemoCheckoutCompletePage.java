package demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DemoCheckoutCompletePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By completeHeader = By.className("complete-header");
    private By backHomeButton = By.id("back-to-products");

    public DemoCheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(completeHeader));
    }

    private void safeClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public String getCompleteMessage() {
        return driver.findElement(completeHeader).getText();
    }
    
    public void clickBackHome() {
        safeClick(backHomeButton);
    }
}
