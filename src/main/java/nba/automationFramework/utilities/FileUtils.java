package nba.automationFramework.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {

    private static final Logger log = LogManager.getLogger(FileUtils.class);

    public static void createNewDirectoryAndFile(File filePath) {
        try {
            File directory = filePath.getParentFile();
            if (directory.exists()) {
                log.info("Deleting existing directory: " + directory.getAbsolutePath());
                deleteDirectory(directory);
            }
            Files.createDirectories(directory.toPath());
            log.info("Created new directory: " + directory.getAbsolutePath());
            Files.createFile(filePath.toPath());
            log.info("Created new file: " + filePath.getAbsolutePath());

        } catch (IOException e) {
            log.error("Error while creating directory and file: " + e.getMessage());
        }
    }

    public static void deleteDirectory(File directory) throws IOException {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        Files.delete(file.toPath());
                    }
                }
            }
            Files.delete(directory.toPath());
        }
    }
}
