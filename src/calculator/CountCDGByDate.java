package calculator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CountCDGByDate {
	public static void main(String[] args) {

		// 指定輸出到的檔案路徑
		String outputPath = "D:\\test_files\\output.txt";

		// 創建一個 FileWriter 並將其傳遞給 BufferedWriter，以便進行緩衝寫入
		try (FileWriter fileWriter = new FileWriter(outputPath);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

			// 將 System.out 重定向到 BufferedWriter
			System.setOut(new PrintStream(new OutputStream() {
				@Override
				public void write(int b) throws IOException {
					bufferedWriter.write(b);
				}
			}));

			String folderPath = "D:\\test_files\\input";

			File folder = new File(folderPath);
			File[] files = folder.listFiles();

			if (files != null) {
				Map<String, Integer> cdgCountByDate = new HashMap<>();
				DataFormatter dataFormatter = new DataFormatter();

				for (File file : files) {
					if (file.isFile() && file.getName().endsWith(".xlsx")) {
						try (FileInputStream fis = new FileInputStream(file);
								Workbook workbook = new XSSFWorkbook(fis)) {

							for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
								Sheet sheet = workbook.getSheetAt(sheetIndex);
								countCDGByDate(sheet, cdgCountByDate, dataFormatter);
							}

						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				// Print CDG count for each date
				for (Map.Entry<String, Integer> entry : cdgCountByDate.entrySet()) {
//                    System.out.println("Date: " + entry.getKey() + ", CDG Count: " + entry.getValue());
					System.out
							.println("Date: " + formatDateString(entry.getKey()) + ", CDG Count: " + entry.getValue());

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void countCDGByDate(Sheet sheet, Map<String, Integer> cdgCountByDate, DataFormatter dataFormatter) {
		for (Row row : sheet) {
			Cell dateCell = row.getCell(0); // Assuming A column is for dates
			Cell gCell = row.getCell(6); // G column is index 6 (0-based)

			if (dateCell != null) {
				String formattedDate = dataFormatter.formatCellValue(dateCell);

				if (gCell != null) {
					String cellValue = gCell.getStringCellValue();
					if (cellValue.contains("CDG")) {
						// Increment CDG count for the specific date
						cdgCountByDate.put(formattedDate, cdgCountByDate.getOrDefault(formattedDate, 0) + 1);
					}
				}
			}
		}
	}

	private static String formatDateString(String originalDateString) {
		try {
			SimpleDateFormat originalFormat = new SimpleDateFormat("MM/dd/yy");
			SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy/MM/dd");

			Date date = originalFormat.parse(originalDateString);
			return targetFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return originalDateString; // Return the original string if parsing fails
		}
	}
}
