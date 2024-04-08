package client.utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

public class ReadJSON implements ReadJSONInterface{
    /**
     * This method reads a JSON file and returns a HashMap
     * @param filePath the path to the JSON file
     * @return a HashMap with the data from the JSON file
     */
    @Override
    public HashMap<String, String> readJsonToMap(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> map = new HashMap<>();
        try {
            map = mapper.readValue(Paths.get(filePath).toFile(), HashMap.class);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return map;
    }

    /**
     * Update tag map
     * @param dataMap the map to write to a file
     * @param filePath the file to write to
     */
    public static void writeMapToJsonFile(HashMap<String, String> dataMap, String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            // Convert HashMap to JSON string
            String jsonString = objectMapper.writeValueAsString(dataMap);
            // System.out.println(jsonString);

            // Write JSON string to file
            objectMapper.writeValue(new File(filePath), dataMap);

            System.out.println("Tag has been written to the file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}
