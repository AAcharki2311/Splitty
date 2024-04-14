package client.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ReadJSONTest {
    private ReadJSON readJSON;
    private static final String TEST_CONFIG_PATH = "src/test/java/client/resources/testFile.JSON";


    @BeforeEach
    void setUp() {
        readJSON = new ReadJSON();
    }

    @Test
    void readJsonToMap() {
        HashMap<String, String> map = readJSON.readJsonToMap(TEST_CONFIG_PATH);
        assertNotNull(map);
        assertFalse(map.isEmpty());
    }

    @Test
    void exceptionThrown() {
        assertThrows(RuntimeException.class, () -> {
            readJSON.readJsonToMap("Fake/Test/Path");
        });
    }

    @Test
    void writeMapToJsonFile() {
        HashMap<String, String> map = new HashMap<>();
        map.put("testkey", "testvalue");
        File file = new File("src/test/java/client/resources/testReadJSONFile.JSON");
        String path = "src/test/java/client/resources/testReadJSONFile.JSON";
        readJSON.writeMapToJsonFile(map, path);
        assertNotNull(readJSON.readJsonToMap(path));
        if(file.exists()){ file.delete();}
    }

    @Test
    void writeMapToJsonFileException() {
        assertThrows(RuntimeException.class, () -> readJSON.writeMapToJsonFile(null, null));
    }
}