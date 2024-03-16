package calculator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

//String outputPath = "D:\\隨身硬碟備份\\影片\\output.csv";

public class ReadDirectory {

    public static void main(String[] args) throws IOException {
    	// 設定要讀取的資料夾路徑
        String sourcePath = "E:\\影片";

        // 設定要寫入的 CSV 檔案路徑
        String outputPath = "D:\\隨身硬碟備份\\影片\\output.csv";

        // 建立 FileWriter 物件
        FileWriter writer = new FileWriter(outputPath);

        // 讀取資料夾
        File directory = new File(sourcePath);

        // 遞迴遍歷資料夾
        readDirectory(directory, writer);

        // 關閉 FileWriter 物件
        writer.close();
    }

    private static void readDirectory(File directory, FileWriter writer) throws IOException {
        // 遍歷資料夾下的所有檔案
        for (File file : directory.listFiles()) {
            // 如果是檔案，則寫入 CSV 檔案
            if (file.isFile()) {
                Path path = Paths.get(file.getAbsolutePath());
                System.out.println(path.toString());
                writer.write(path.toString() + "\n");
            } else if (file.isDirectory()) {
                // 如果是資料夾，則遞迴讀取
                readDirectory(file, writer);
            }
        }
    }
}