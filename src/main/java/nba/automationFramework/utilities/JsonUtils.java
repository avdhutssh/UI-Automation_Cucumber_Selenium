package nba.automationFramework.utilities;

import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.core.type.TypeReference;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;

public class JsonUtils {

    public static HashMap<String, String> getJsonObject(String fileName, String objectName) {
        String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "TestData" + File.separator + fileName;        // Read JSON file content as a string
        String jsonContent = null;
        try {
            jsonContent = new String(Files.readAllBytes(new File(filePath).toPath()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Deserialize JSON content into a HashMap
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> jsonData = null;
        try {
            jsonData = mapper.readValue(jsonContent, new TypeReference<HashMap<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Retrieve the desired object based on the object name
        HashMap<String, String> jsonObject = (HashMap<String, String>) jsonData.get(objectName);

        return jsonObject;
    }
}
