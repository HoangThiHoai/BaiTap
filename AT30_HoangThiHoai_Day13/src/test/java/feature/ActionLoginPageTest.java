package feature;

import Untils.ExcelUtils;
import action.LoginAction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Map;

public class ActionLoginPageTest {
    public static final String FILE_SHEET = "Login";
    private static final String FILE_PATH = "dataTestLogin.xlsx";
    private static final String URL = "https://saucelabs.com/request-demo";
    private static final String STT = "STT";
    private static final String EMAIL = "Email";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final String COMPANY = "Company";
    private static final String PHONE = "Phone";
    private static final String COUNTRY = "Country";
    private static final String INTEREST = "Interest";
    private static final String COMMENTS = "Comments";
    private static final String CHECKBOX = "Checkbox";
    private static final String KQMM = "KQMM";

    static void main(String[] args) throws InterruptedException {
        List<Map<String, String>> excelData = ExcelUtils.readExcelData(FILE_PATH, FILE_SHEET);
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        try {
            for (Map<String, String> rowData : excelData) {
                driver.get(URL);
                String actual= LoginAction.performLogin(driver,rowData.get(EMAIL), rowData.get(FIRST_NAME), rowData.get(LAST_NAME), rowData.get(COMPANY), rowData.get(PHONE),
                        rowData.get(COUNTRY), rowData.get(INTEREST), rowData.get(COMMENTS), rowData.get(CHECKBOX) );
                if (actual.equals(rowData.get(KQMM))) {
                    System.out.println("STT "+rowData.get(STT)+" PASS");
                }else  {
                    System.out.println("STT "+rowData.get(STT)+" FAIL ACTUAL: " +actual +" EXPECTED: " +rowData.get(KQMM));
                }
            }

        } finally {
            driver.quit();
        }
    }
}
