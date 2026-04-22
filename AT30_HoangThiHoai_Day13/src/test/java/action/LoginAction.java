package action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LoginAction {
    public static void performLogin(WebDriver driver, String email, String firstName, String lastName, String company, String phone, String country, String interest, String comment, String checkbox) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement elementEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='Email']")));
        elementEmail.sendKeys(email);


//        List <WebElement> elementFirstName = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//input[@id='FirstName']")));
//        for (WebElement webElement : elementFirstName) {
//            if (webElement != null) {
//                webElement.sendKeys(firstName);
//            }
//        }
        List <WebElement> elementFirstName = driver.findElements(By.xpath("//input[@id='FirstName']"));
        if(!elementFirstName.isEmpty()){
            elementFirstName.get(0).sendKeys(firstName);
        }

        List <WebElement> elementLastName = driver.findElements(By.xpath("//input[@id='LastName']"));
        if(!elementLastName.isEmpty()){
            elementLastName.get(0).sendKeys(lastName);
        }

//        WebElement elementLastName = driver.findElement(By.xpath("//input[@id='LastName']"));
//        elementLastName.sendKeys(lastName);

        WebElement elementCompany = driver.findElement(By.xpath("//input[@id='Company']"));
        elementCompany.sendKeys(company);

        List <WebElement> elementPhone = driver.findElements(By.xpath("//input[@id='Phone']"));
        for (WebElement webElement : elementPhone) {
            if (webElement != null) {
                webElement.sendKeys(phone);
            }
        }

//        WebElement elementPhone = driver.findElement(By.xpath("//input[@id='Phone']"));
//        elementPhone.sendKeys(phone);

        List <WebElement> elementCountry = driver.findElements(By.xpath("//input[@id='Country']"));
        for (int i = 0; i<elementCountry.size();i++) {
            if (elementCountry.get(i) != null) {
                Select selectCountry = new Select((WebElement) elementCountry);
                selectCountry.selectByIndex(Integer.parseInt(country));
            }
        }

//        WebElement elementCountry = driver.findElement(By.xpath("//select[@id='Country']"));
//        Select selectCountry = new Select(elementCountry);
//        selectCountry.selectByIndex(Integer.parseInt(country));

        WebElement elementInterest = driver.findElement(By.xpath("//select[@id='Solution_Interest__c']"));
        Select selectInterest = new Select(elementInterest);
        selectInterest.selectByIndex(Integer.parseInt(interest));

        WebElement elementComment = driver.findElement(By.xpath("//textarea[@id='Sales_Contact_Comments__c']"));
        elementComment.sendKeys(comment);

        WebElement elementCheckBox = driver.findElement(By.xpath("//input[@id='mktoCheckbox_47709_0']"));
        if (checkbox.equalsIgnoreCase("TRUE")) {
            elementCheckBox.click();

        }

        WebElement elementLetTalk = driver.findElement(By.xpath("//button[@class='mktoButton']"));
        elementLetTalk.click();
        Thread.sleep(6000);
    }
}
