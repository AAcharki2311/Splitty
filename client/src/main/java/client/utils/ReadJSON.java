package client.utils;
import com.fasterxml.jackson.databind.ObjectMapper;

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
}
