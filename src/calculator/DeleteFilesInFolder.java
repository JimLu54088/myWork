import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class DeleteFilesInFolder {
    public static void main(String[] args) {
        String folderPath = "要删除的文件夹路径"; // 请替换为实际的文件夹路径

        try {

//            String sPropertyFile = args[0];
            String sPropertyFile = "";

            if(sPropertyFile == null || sPropertyFile.isEmpty() == true){
                System.err.println(
                        "ERROR : Property File Name is not given. Please provide info -Dproperty.file=<Path of property file>");
                System.exit(-1);
            }
            PropertyLoader.propertyFilePath = sPropertyFile;
            System.out.println(sPropertyFile);
            String folderPath = PropertyLoader.getProperty("folderPath");

            Files.walkFileTree(Paths.get(folderPath), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    System.out.println("Deleted file: " + file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    // Handle the exception
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    // Do nothing for directories
                    return FileVisitResult.CONTINUE;
                }
            });
            System.out.println("Files deleted successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
