package client.utils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

public class ReadJSON implements ReadJSONInterface {
    /**
     * This method reads a JSON file and returns a HashMap
     * @param filePath the path to the JSON file
     * @return a HashMap with the data from the JSON file
     */
    public HashMap<String, Object> readJsonToMap(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> map = new HashMap<>();
        try {
            map = mapper.readValue(Paths.get(filePath).toFile(), HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
