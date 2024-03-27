package client.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ReadURLTest {

    private static final String TEST_CONFIG_PATH = "src/test/java/client/resources/testconfigfile.properties";
    private static final String TEST_NON_CONFIG_PATH = "nothing/testconfigfile.properties";
    private static final String TEST_SERVER_URL = "http://testserver:8080" ;
    private ReadURL readURL;

    @BeforeEach
    void setUp() {
        readURL = new ReadURL();
    }

    private void writeTestFile(String serverurl) throws IOException {
        Properties properties = new Properties();
        properties.setProperty("serverurl", serverurl);

        FileOutputStream outputStream = new FileOutputStream(TEST_CONFIG_PATH);
        properties.store(outputStream, null);
        outputStream.close();
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_CONFIG_PATH);
        if(file.exists()){
            file.delete();
        }
    }

    @Test
    void readAndWriteServerUrl() throws IOException {
        writeTestFile(TEST_SERVER_URL);
        readURL.writeServerUrl("http://updatetestserver:8080", TEST_CONFIG_PATH);
        String readUrl = readURL.readServerUrl(TEST_CONFIG_PATH);
        assertEquals(readUrl, "http://updatetestserver:8080");
    }

    @Test
    void exceptionThrown() {
        assertThrows(RuntimeException.class, () -> {
            readURL.writeServerUrl("http://updatetestserver:8080", TEST_NON_CONFIG_PATH);
        });
        assertThrows(RuntimeException.class, () -> {
            readURL.readServerUrl(TEST_NON_CONFIG_PATH);
        });
    }
}