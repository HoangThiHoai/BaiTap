package feature;

import action.BookDemoPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.ExcelUtils;

import java.util.List;
import java.util.Map;

public class ActionLoginPageTest {
    static final String EXCEL_PATH ="dataTestLogin.xlsx" ;
    static final String SHEET_NAME ="Login" ;
    static void main(String[] args) {
        WebDriver driver =new ChromeDriver();
        driver.get("https://saucelabs.com/request-demo");
        BookDemoPage bookDemoPage = new BookDemoPage(driver);
        testBookDemoDataDriven(bookDemoPage);
    }
    public static void testBookDemoDataDriven(BookDemoPage bookDemoPage) {
        // Đọc dữ liệu từ Excel
        List<Map<String, String>> excelData = ExcelUtils.readExcelData(EXCEL_PATH, SHEET_NAME);

        for (Map<String, String> rowData : excelData) {
            System.out.println("Đang xử lý biểu mẫu cho email: " + rowData.get("Business Email"));

            // Sử dụng method gộp từ Page Object để tái sử dụng
            bookDemoPage.fillDemoForm(
                    rowData.get("Email"),
                    rowData.get("FirstName"),
                    rowData.get("LastName"),
                    rowData.get("Company"),
                    rowData.get("Phone"),
                    rowData.get("Country"),
                    rowData.get("Interest"),
                    rowData.get("Comments")
            );

            // Thực hiện action click submit
            bookDemoPage.clickSubmit();
        }
    }

}