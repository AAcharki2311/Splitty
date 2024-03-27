package client.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        HashMap<String, Object> map = readJSON.readJsonToMap(TEST_CONFIG_PATH);
        assertNotNull(map);
        assertFalse(map.isEmpty());
    }

    @Test
    void exceptionThrown() {
        assertThrows(RuntimeException.class, () -> {
            readJSON.readJsonToMap("Fake/Test/Path");
        });
    }
}