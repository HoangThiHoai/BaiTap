package demo.test;

import demo.CreateExcelData;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.time.Duration;

public class DemoBaseTest {
    protected WebDriver driver;

    static {
        File excelFile = new File("DemoTestData.xlsx");
        if (!excelFile.exists()) {
            System.out.println("Excel file not found. Generating DemoTestData.xlsx...");
            demo.CreateExcelData.main(new String[]{});
        }
    }

    @BeforeSuite
    public void globalSetup() {
        // Any other global setup can go here
    }

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
