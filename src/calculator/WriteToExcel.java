package calculator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxml4j.exceptions.InvalidFormatException;
import org.openxml4j.opc.Package;

// String filePath = "C:\\Users\\Jim\\Desktop\\777777777.xlsx";
public class WriteToExcel {
    public static void main(String[] args) {
        // 指定Excel檔案路徑
        String filePath = "C:\\Users\\Jim\\Desktop\\777777777.xlsx";

        try {
            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(Package.open(fis));

            // 获取名为"Test01"的工作表，如果不存在则创建
            Sheet sheet = workbook.getSheet("Test01");
            if (sheet == null) {
                sheet = workbook.createSheet("Test01");
            }

            // 在B5單元格寫入數值55
            Row row = sheet.getRow(4); // 获取已存在的行，如果不存在则创建
            if (row == null) {
                row = sheet.createRow(4);
            }
            Cell cell = row.createCell(1); // B列的索引是1
            cell.setCellValue(55);

            // 將內容寫入檔案
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            System.out.println("Excel檔案已經成功更新！");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
