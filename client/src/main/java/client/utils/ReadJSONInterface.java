package client.utils;


import java.io.IOException;
import java.util.HashMap;

public interface ReadJSONInterface {
        /**
         * This method reads a JSON file and returns a HashMap
         * @param filePath the path to the JSON file
         * @return a HashMap with the data from the JSON file
         */
        public HashMap<String, Object> readJsonToMap(String filePath) throws IOException;

}
