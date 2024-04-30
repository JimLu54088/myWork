package calculator;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExcelMerger {

    public static void main(String[] args) {
    	mergeExcelSheets(Arrays.asList("D:\\test_files\\excel_combined\\work\\SDR1.xlsx", "D:\\test_files\\excel_combined\\work\\SDR2.xlsx","D:\\test_files\\excel_combined\\work\\SDR3.xlsx"), "D:\\test_files\\excel_combined\\output\\merged.xlsx");
    }

    public static void mergeExcelSheets(List<String> inputFiles, String outputFile) {
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {

            Workbook mergedWorkbook = new XSSFWorkbook();
            Set<String> mergedSheetNames = new HashSet<>();

            for (String inputFile : inputFiles) {
                try (FileInputStream fis = new FileInputStream(inputFile)) {
                    Workbook workbook = new XSSFWorkbook(fis);
                    mergeSheets(workbook, mergedWorkbook, mergedSheetNames);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            mergedWorkbook.write(fos);
            System.out.println("Merged excel files successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void mergeSheets(Workbook sourceWorkbook, Workbook targetWorkbook, Set<String> mergedSheetNames) {
        for (int i = 0; i < sourceWorkbook.getNumberOfSheets(); i++) {
            Sheet sourceSheet = sourceWorkbook.getSheetAt(i);
            String sheetName = sourceSheet.getSheetName();

            if (!mergedSheetNames.contains(sheetName)) {
                Sheet targetSheet = targetWorkbook.createSheet(sheetName);
                copySheet(sourceSheet, targetSheet);
                mergedSheetNames.add(sheetName);
            } else {
                // Skip copying header for already merged sheets
                copySheetWithoutHeader(sourceSheet, targetWorkbook.getSheet(sheetName));
            }
        }
    }

    private static void copySheet(Sheet sourceSheet, Sheet targetSheet) {
        for (int i = 0; i <= sourceSheet.getLastRowNum(); i++) {
            Row sourceRow = sourceSheet.getRow(i);
            Row targetRow = targetSheet.createRow(targetSheet.getLastRowNum() + 1);
            if (sourceRow != null) {
                for (int j = 0; j <= sourceRow.getLastCellNum(); j++) {
                    Cell sourceCell = sourceRow.getCell(j);
                    if (sourceCell != null) {
                        Cell targetCell = targetRow.createCell(j, sourceCell.getCellType());
                        copyCellValue(sourceCell, targetCell);
                    }
                }
            }
        }
    }

    private static void copySheetWithoutHeader(Sheet sourceSheet, Sheet targetSheet) {
        for (int i = 1; i <= sourceSheet.getLastRowNum(); i++) {
            Row sourceRow = sourceSheet.getRow(i);
            Row targetRow = targetSheet.createRow(targetSheet.getLastRowNum() + 1);
            if (sourceRow != null) {
                for (int j = 0; j <= sourceRow.getLastCellNum(); j++) {
                    Cell sourceCell = sourceRow.getCell(j);
                    if (sourceCell != null) {
                        Cell targetCell = targetRow.createCell(j, sourceCell.getCellType());
                        copyCellValue(sourceCell, targetCell);
                    }
                }
            }
        }
    }

    private static void copyCellValue(Cell sourceCell, Cell targetCell) {
        switch (sourceCell.getCellType()) {
            case STRING:
                targetCell.setCellValue(sourceCell.getStringCellValue());
                break;
            case NUMERIC:
                targetCell.setCellValue(sourceCell.getNumericCellValue());
                break;
            case BOOLEAN:
                targetCell.setCellValue(sourceCell.getBooleanCellValue());
                break;
            case FORMULA:
                targetCell.setCellFormula(sourceCell.getCellFormula());
                break;
            case BLANK:
                targetCell.setBlank();
                break;
            default:
                break;
        }
    }
}
