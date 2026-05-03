package demo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class CreateExcelData {
    public static void main(String[] args) {
        Workbook workbook = new XSSFWorkbook();

        // Sheet 1: Login
        Sheet loginSheet = workbook.createSheet("Login");
        Row loginHeader = loginSheet.createRow(0);
        loginHeader.createCell(0).setCellValue("Username");
        loginHeader.createCell(1).setCellValue("Password");
        loginHeader.createCell(2).setCellValue("ExpectedResult");

        Row loginRow1 = loginSheet.createRow(1);
        loginRow1.createCell(0).setCellValue("standard_user");
        loginRow1.createCell(1).setCellValue("secret_sauce");
        loginRow1.createCell(2).setCellValue("success");

        Row loginRow2 = loginSheet.createRow(2);
        loginRow2.createCell(0).setCellValue("locked_out_user");
        loginRow2.createCell(1).setCellValue("secret_sauce");
        loginRow2.createCell(2).setCellValue("locked_out");
        
        Row loginRow3 = loginSheet.createRow(3);
        loginRow3.createCell(0).setCellValue("");
        loginRow3.createCell(1).setCellValue("");
        loginRow3.createCell(2).setCellValue("empty");

        // Sheet 2: CheckoutInfo
        Sheet checkoutSheet = workbook.createSheet("CheckoutInfo");
        Row checkoutHeader = checkoutSheet.createRow(0);
        checkoutHeader.createCell(0).setCellValue("FirstName");
        checkoutHeader.createCell(1).setCellValue("LastName");
        checkoutHeader.createCell(2).setCellValue("ZipCode");
        checkoutHeader.createCell(3).setCellValue("ExpectedResult");

        Row coRow1 = checkoutSheet.createRow(1);
        coRow1.createCell(0).setCellValue("John");
        coRow1.createCell(1).setCellValue("Doe");
        coRow1.createCell(2).setCellValue("12345");
        coRow1.createCell(3).setCellValue("success");

        Row coRow2 = checkoutSheet.createRow(2);
        coRow2.createCell(0).setCellValue("");
        coRow2.createCell(1).setCellValue("Doe");
        coRow2.createCell(2).setCellValue("12345");
        coRow2.createCell(3).setCellValue("error_first_name");

        Row coRow3 = checkoutSheet.createRow(3);
        coRow3.createCell(0).setCellValue("John");
        coRow3.createCell(1).setCellValue("");
        coRow3.createCell(2).setCellValue("12345");
        coRow3.createCell(3).setCellValue("error_last_name");
        
        Row coRow4 = checkoutSheet.createRow(4);
        coRow4.createCell(0).setCellValue("John");
        coRow4.createCell(1).setCellValue("Doe");
        coRow4.createCell(2).setCellValue("");
        coRow4.createCell(3).setCellValue("error_zip_code");

        // Sheet 3: E2E_Test
        Sheet e2eSheet = workbook.createSheet("E2E_Test");
        Row e2eHeader = e2eSheet.createRow(0);
        e2eHeader.createCell(0).setCellValue("Username");
        e2eHeader.createCell(1).setCellValue("Password");
        e2eHeader.createCell(2).setCellValue("ProductName");
        e2eHeader.createCell(3).setCellValue("FirstName");
        e2eHeader.createCell(4).setCellValue("LastName");
        e2eHeader.createCell(5).setCellValue("ZipCode");

        Row e2eRow1 = e2eSheet.createRow(1);
        e2eRow1.createCell(0).setCellValue("standard_user");
        e2eRow1.createCell(1).setCellValue("secret_sauce");
        e2eRow1.createCell(2).setCellValue("Sauce Labs Backpack");
        e2eRow1.createCell(3).setCellValue("E2E");
        e2eRow1.createCell(4).setCellValue("User");
        e2eRow1.createCell(5).setCellValue("99999");

        try (FileOutputStream fileOut = new FileOutputStream("DemoTestData.xlsx")) {
            workbook.write(fileOut);
            System.out.println("Excel file generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
