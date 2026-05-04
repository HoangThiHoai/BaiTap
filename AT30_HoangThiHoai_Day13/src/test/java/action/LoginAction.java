package action;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class LoginAction {
    public static String performLogin(WebDriver driver, String email, String firstName, String lastName, String company, String phone, String country, String interest, String comment, String checkbox) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement elementEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='Email']")));
        elementEmail.sendKeys(email);

        By firstNameLocator = By.xpath("//input[@id='FirstName']");
        List<WebElement> elementFirstName;
        try {
            elementFirstName = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(firstNameLocator));
        } catch (TimeoutException e) {
            elementFirstName = new ArrayList<>(); // không có → list rỗng
        }
        // xử lý tiếp
        if (!elementFirstName.isEmpty()) {
            elementFirstName.get(0).sendKeys(firstName);
        }

        List<WebElement> elementLastName = driver.findElements(By.xpath("//input[@id='LastName']"));
        if (!elementLastName.isEmpty()) {
            elementLastName.get(0).sendKeys(lastName);
        }

        WebElement elementCompany = driver.findElement(By.xpath("//input[@id='Company']"));
        elementCompany.sendKeys(company);

        List<WebElement> elementPhone = driver.findElements(By.xpath("//input[@id='Phone']"));
        if ((!elementPhone.isEmpty())) {
            elementPhone.get(0).sendKeys(phone);
        }

        List<WebElement> elementCountry = driver.findElements(By.xpath("//select[@id='Country']"));
        //chặn country rỗng vì rông java không parse được
        if (country != null && !country.isEmpty() && (!elementCountry.isEmpty())) {
            Select selectCountry = new Select(elementCountry.get(0));
            selectCountry.selectByIndex(Integer.parseInt(country));
        }

        List<WebElement> elementInterest = driver.findElements(By.xpath("//select[@id='Solution_Interest__c']"));
        if (interest != null && !interest.isEmpty() && (!elementInterest.isEmpty())) {
            Select selectInterest = new Select(elementInterest.get(0));
            selectInterest.selectByIndex(Integer.parseInt(interest));
        }

        WebElement elementComment = driver.findElement(By.xpath("//textarea[@id='Sales_Contact_Comments__c']"));
        elementComment.sendKeys(comment);

        WebElement elementCheckBox = driver.findElement(By.xpath("//input[@type='checkbox']"));
        if (checkbox.equalsIgnoreCase("TRUE")) {
            elementCheckBox.click();

        }

        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement elementLetTalk = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='mktoButton']")));
        elementLetTalk.click();


        // check từng field
        String emailError = getFieldError(driver, "Email");
        if (!emailError.isEmpty()) {
            return emailError;
        }

        String firstNameError = getFieldError(driver, "FirstName");
        if (!firstNameError.isEmpty()) {
            return firstNameError;
        }

        String lastNameError = getFieldError(driver, "LastName");
        if (!lastNameError.isEmpty()) {
            return lastNameError;
        }

        String companyError = getFieldError(driver, "Company");
        if (!companyError.isEmpty()) {
            return companyError;
        }

        String phoneError = getFieldError(driver, "Phone");
        if (!phoneError.isEmpty()) {
            return phoneError;
        }

        //select thì khác input → dùng select
        String countryError = getSelectError(driver, "Country");
        if (!countryError.isEmpty()) {
            return countryError;
        }

        String interestError = getSelectError(driver, "Solution_Interest__c");
        if (!interestError.isEmpty()) {
            return interestError;
        }

        String urlActual = driver.getCurrentUrl();
        if (urlActual.equals("https://saucelabs.com/thank-you-contact")) {
            return "https://saucelabs.com/thank-you-contact";
        }
        List<WebElement> errors = driver.findElements(By.cssSelector(".mktoErrorMsg"));
        if (!errors.isEmpty()) {
            return errors.get(0).getText();
        }
        return "UNKNOWN";
    }

    public static String getFieldError(WebDriver driver, String fieldId) {
        List<WebElement> errors = driver.findElements(By.xpath("//input[@id='" + fieldId + "']/following::div[contains(@class,'mktoErrorMsg')][1]"));

        return errors.isEmpty() ? "" : errors.get(0).getText();
    }

    public static String getSelectError(WebDriver driver, String fieldId) {
        List<WebElement> errors = driver.findElements(By.xpath("//select[@id='" + fieldId + "']/following::div[contains(@class,'mktoErrorMsg')][1]"));

        return errors.isEmpty() ? "" : errors.get(0).getText();
    }
}
