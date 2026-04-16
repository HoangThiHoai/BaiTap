package UnHapyCase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SpaceLastName {
    static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://saucelabs.com/request-demo");
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //Case:lastName nhaapj toàn khaonrg cách

        // Đợi Email field xuất hiện
        WebElement elementEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='Email']")));
        elementEmail.sendKeys("hoai@domain.com");

        WebElement elementFirstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='FirstName']")));
        //WebElement elementFirstName = driver.findElement(By.xpath("//input[@id='FristName']"));
        elementFirstName.sendKeys("Hoài");

        //WebElement elementLastName= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='LastName']")));
        WebElement elementLastName = driver.findElement(By.xpath("//input[@id='LastName']"));
        elementLastName.sendKeys("         ");

        WebElement elementCompany = driver.findElement(By.xpath("//input[@id='Company']"));
        elementCompany.sendKeys("Công ty A");

        WebElement elementPhone = driver.findElement(By.xpath("//input[@id='Phone']"));
        elementPhone.sendKeys("123456789");

        WebElement elementCountry = driver.findElement(By.xpath("//select[@id='Country']"));
        Select selectCountry = new Select(elementCountry);
        selectCountry.selectByVisibleText("Angola");

        WebElement elementInterest = driver.findElement(By.xpath("//select[@id='Solution_Interest__c']"));
        Select selectInterest = new Select(elementInterest);
        selectInterest.selectByValue("Mobile Application Testing");

        WebElement elementComment = driver.findElement(By.xpath("//textarea[@id='Sales_Contact_Comments__c']"));
        elementComment.sendKeys("Nhập ghi chú test");

        WebElement elementCheckBox = driver.findElement(By.xpath("//input[@id='mktoCheckbox_47709_0']"));
        elementCheckBox.click();

        WebElement elementLetTalk = driver.findElement(By.xpath("//button[@class='mktoButton']"));
        elementLetTalk.click();

        WebElement elementRequiedLastName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='ValidMsgLastName']")));
        String KQMMRequieLastName = "This field is required.";
        if (elementRequiedLastName.getText().equals(KQMMRequieLastName)) {
            System.out.println("PASS");
        } else {
            System.out.println("FAIL");
        }

        Thread.sleep(3000);
        driver.quit();

    }
}
