package client.utils;


import java.io.IOException;
import java.util.HashMap;

public interface ReadJSONInterface {
        public HashMap<String, Object> readJsonToMap(String filePath) throws IOException;

}
