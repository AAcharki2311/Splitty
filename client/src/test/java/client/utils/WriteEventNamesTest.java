package client.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WriteEventNamesTest {

    private WriteEventNames writer;
    private static final String TEST_CONFIG_PATH = "src/test/java/client/resources/testEventFile.JSON";
    private static final String NEW_TEST_CONFIG_PATH = "src/test/java/client/resources/newTestEventFile.JSON";


    @BeforeEach
    void setUp() {
        writer = new WriteEventNames();
    }

    @Test
    void writeEventName() {
        writer.writeEventName(TEST_CONFIG_PATH, "ValueTest", "IDTest");
        List<String> result = writer.readEventsFromJson(TEST_CONFIG_PATH);
        assertTrue(result.contains("ValueTest"));
    }

//    @Test
//    void writeEventNameRemoveID() {
//        writer.writeEventName(TEST_CONFIG_PATH, "ValueTest23", "id: 4");
//        List<String> result = writer.readEventsFromJson(TEST_CONFIG_PATH);
//        assertTrue(result.contains("id: 4"));
//    }

    @Test
    void writeEventNameBigger4() {
        writer.writeEventName(NEW_TEST_CONFIG_PATH, "unique", "unique");
        List<String> result = writer.readEventsFromJson(NEW_TEST_CONFIG_PATH);
        assertTrue(result.contains("unique"));
    }

    @Test
    void readEventsFromJson() {
        List<String> result = writer.readEventsFromJson(TEST_CONFIG_PATH);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void emptyList() {
        List<String> result = writer.readEventsFromJson("Fake path");
        assertNotNull(result);
    }

//    @Test
//    void anotherExceptionThrown() {
//        writer.writeEventName("Fake path", "eventname", "id");
//        assertNotNull(result);
//    }
}