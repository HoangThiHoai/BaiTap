package utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;

public class ExcelUtils {

    /**
     * Đọc dữ liệu từ file Excel và trả về danh sách Map (Header -> Value)
     *
     * @param filePath  Đường dẫn file Excel
     * @param sheetName Tên sheet cần đọc
     * @return List<Map<String, String>>
     */
    public static List<Map<String, String>> readExcelData(String filePath, String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (OPCPackage pkg = OPCPackage.open(filePath, PackageAccess.READ); Workbook workbook = new XSSFWorkbook(pkg)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Không tìm thấy sheet: " + sheetName);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) return data;

            int colCount = headerRow.getLastCellNum();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> rowMap = new HashMap<>();
                boolean isEmptyRow = true;

                for (int j = 0; j < colCount; j++) {
                    String header = formatter.formatCellValue(headerRow.getCell(j));
                    String value = formatter.formatCellValue(row.getCell(j)).trim();

                    if (!value.isEmpty()) {
                        isEmptyRow = false;
                    }
                    rowMap.put(header, value);
                }

                if (!isEmptyRow) {
                    data.add(rowMap);
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file Excel: " + e.getMessage());
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
