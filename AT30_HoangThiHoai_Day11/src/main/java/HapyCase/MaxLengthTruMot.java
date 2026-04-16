package HapyCase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

public class MaxLengthTruMot {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String INT = "0123456789";

    public static String generateRandomString(int n) {
        Random random = new Random();
        StringBuilder result = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = random.nextInt(CHARACTERS.length());
            result.append(CHARACTERS.charAt(index));
        }

        return result.toString();
    }

    public static String generateRandomInt(int n) {
        Random random = new Random();
        StringBuilder result = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = random.nextInt(INT.length());
            result.append(INT.charAt(index));
        }

        return result.toString();
    }


    static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://saucelabs.com/request-demo");
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //Case: Nhập maxlength-1

        // Đợi Email field xuất hiện
        WebElement elementEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='Email']")));
        elementEmail.sendKeys(generateRandomString(255 - 1 - 11) + "@domain.com");

        WebElement elementFirstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='FirstName']")));
        //WebElement elementFirstName = driver.findElement(By.xpath("//input[@id='FristName']"));
        elementFirstName.sendKeys(generateRandomString(100 - 1));

        //WebElement elementLastName= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='LastName']")));
        WebElement elementLastName = driver.findElement(By.xpath("//input[@id='LastName']"));
        elementLastName.sendKeys(generateRandomString(100 - 1));

        WebElement elementCompany = driver.findElement(By.xpath("//input[@id='Company']"));
        elementCompany.sendKeys(generateRandomString(255 - 1));

        WebElement elementPhone = driver.findElement(By.xpath("//input[@id='Phone']"));
        elementPhone.sendKeys(generateRandomInt(255 - 1));

        WebElement elementCountry = driver.findElement(By.xpath("//select[@id='Country']"));
        Select selectCountry = new Select(elementCountry);
        selectCountry.selectByVisibleText("Angola");

        WebElement elementInterest = driver.findElement(By.xpath("//select[@id='Solution_Interest__c']"));
        Select selectInterest = new Select(elementInterest);
        selectInterest.selectByValue("Mobile Application Testing");

        WebElement elementComment = driver.findElement(By.xpath("//textarea[@id='Sales_Contact_Comments__c']"));
        elementComment.sendKeys(generateRandomString(255 - 1));

        WebElement elementCheckBox = driver.findElement(By.xpath("//input[@id='mktoCheckbox_47709_0']"));
        elementCheckBox.click();

        WebElement elementLetTalk = driver.findElement(By.xpath("//button[@class='mktoButton']"));
        elementLetTalk.click();

        Thread.sleep(10000);

        String URL = driver.getCurrentUrl();
        String URLMM = "https://saucelabs.com/thank-you-contact";
        if (URL.equals(URLMM)) {
            System.out.println("PASS");
        } else System.out.println("FAIL");

        driver.quit();

    }
}
