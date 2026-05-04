package action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BookDemoPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By emailField = By.id("Email");
    private By firstNameField = By.id("FirstName");
    private By lastNameField = By.id("LastName");
    private By companyField = By.id("Company");
    private By phoneField = By.id("Phone");
    private By countryDropdown = By.id("Country");
    private By interestDropdown = By.id("Solution_Interest__c");
    private By commentsField = By.id("Sales_Contact_Comments__c");
    private By privacyCheckbox = By.xpath("//input[@type='checkbox']");
    private By submitButton = By.xpath("//button[@type='submit']");

    public BookDemoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField)).clear();
        //driver.findElement(firstNameField).clear();
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        driver.findElement(lastNameField).clear();
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    public void enterCompany(String company) {
        driver.findElement(companyField).clear();
        driver.findElement(companyField).sendKeys(company);
    }

    public void enterPhone(String phone) {
        driver.findElement(phoneField).clear();
        driver.findElement(phoneField).sendKeys(phone);
    }

    public void selectCountry(String country) {
        if (country != null && !country.isEmpty()) {
            new Select(driver.findElement(countryDropdown)).selectByVisibleText(country);
        }
    }

    public void selectInterest(String interest) {
        if (interest != null && !interest.isEmpty()) {
            new Select(driver.findElement(interestDropdown)).selectByVisibleText(interest);
        }
    }

    public void enterComments(String comments) {
        driver.findElement(commentsField).clear();
        driver.findElement(commentsField).sendKeys(comments);
    }

    public void tickPrivacyPolicy() {
        WebElement checkbox = driver.findElement(privacyCheckbox);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void clickSubmit() {
        driver.findElement(submitButton).click();
    }

    // Method gộp để tái sử dụng
    public void fillDemoForm(String email, String firstName, String lastName, String company,
                             String phone, String country, String interest, String comments) {
        enterEmail(email);
        enterFirstName(firstName);
        enterLastName(lastName);
        enterCompany(company);
        enterPhone(phone);
        selectCountry(country);
        selectInterest(interest);
        enterComments(comments);
        tickPrivacyPolicy();
    }
}