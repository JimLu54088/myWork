package calculator;

import com.jim.loganalyzer.common.PropertyLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class DeleteFileexceptProperties {

    public static void main(String[] args) {
        try {

            String sPropertyFile = args[0];
//            String sPropertyFile = "";

            if (sPropertyFile == null || sPropertyFile.isEmpty() == true) {
                System.err.println(
                        "ERROR : Property File Name is not given. Please provide info -Dproperty.file=<Path of property file>");
                System.exit(-1);
            }
            PropertyLoader.propertyFilePath = sPropertyFile;
            System.out.println(sPropertyFile);
            String folderPath = PropertyLoader.getProperty("folderPathToDeleteExceptproperties");

            deleteFilesExceptProperties(new File(folderPath));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


    private static void deleteFilesExceptProperties(File folder) throws Exception{

        String fileName = PropertyLoader.getProperty("folderPathToDeleteExceptpropertiesFileName");

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteFilesExceptProperties(file);
                }
            }
        } else {
            if (!folder.getName().equals(fileName)) {
                boolean deleted = folder.delete();
                if (deleted) {
                    System.out.println("file " + folder.getAbsolutePath() + "is deleted.");
                } else {
                    System.out.println("file " + folder.getAbsolutePath() + "cannot be deleted.");
                }
            }
        }
    }


}
