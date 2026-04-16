package UnHapyCase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BlankPrefixEmail {
    static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://saucelabs.com/request-demo");
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //Case: Bỏ trôống tiền tố

        // Đợi Email field xuất hiện
        WebElement elementEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='Email']")));
        elementEmail.sendKeys("@domain.com");

        WebElement elementCompany = driver.findElement(By.xpath("//input[@id='Company']"));
        elementCompany.sendKeys("Công ty A");

        WebElement elementInterest = driver.findElement(By.xpath("//select[@id='Solution_Interest__c']"));
        Select selectInterest = new Select(elementInterest);
        selectInterest.selectByValue("Mobile Application Testing");

        WebElement elementComment = driver.findElement(By.xpath("//textarea[@id='Sales_Contact_Comments__c']"));
        elementComment.sendKeys("Nhập ghi chú test");

        WebElement elementCheckBox = driver.findElement(By.xpath("//input[@id='mktoCheckbox_47709_0']"));
        elementCheckBox.click();

        WebElement elementLetTalk = driver.findElement(By.xpath("//button[@class='mktoButton']"));
        elementLetTalk.click();


        WebElement elementRequiedEmailRow= driver.findElement(By.xpath("//div[text()='Must be valid email. ']"));
        String KQMMRequiedEmailRow = "Must be valid email.\n" + "example@yourdomain.com";
        if (elementRequiedEmailRow.getText().equals(KQMMRequiedEmailRow)) {
            System.out.println("PASS");
        } else {
            System.out.println("FAIL");
        }

        Thread.sleep(10000);
        driver.quit();

    }
}
