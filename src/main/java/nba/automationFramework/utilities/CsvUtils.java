package nba.automationFramework.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvUtils {
    private static final Logger log = LogManager.getLogger(CsvUtils.class);

    public static String writeToCsv(List<String> data, String filePath) {

        try (FileWriter writer = new FileWriter(filePath)) {
            for (String line : data) {
                writer.write(line + "\n");
            }
            log.info("Data successfully written to CSV file at: " + filePath);
        } catch (IOException e) {
            log.error("Error writing to CSV file: " + e.getMessage());
        }
        return filePath;
    }

    public static List<String> readFromCsv(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
