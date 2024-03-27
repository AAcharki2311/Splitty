package client.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class LanguageSwitchTest {
    private LanguageSwitch languageSwitch;
    private static final String TEST_CONFIG_PATH = "src/test/java/client/resources/testLanguageFile.JSON";
    private static final String TEST_LANGUAGE = "English" ;

    @BeforeEach
    void setUp() {
        languageSwitch = new LanguageSwitch();
    }

    private void writeTestFile(String language) throws IOException {
        Properties properties = new Properties();
        properties.setProperty("language", language);

        FileOutputStream outputStream = new FileOutputStream(TEST_CONFIG_PATH);
        properties.store(outputStream, null);
        outputStream.close();
    }

    private String readTestFile(String configFilePath) throws IOException {
        Properties appProps = new Properties();
        FileInputStream inputStream = new FileInputStream(configFilePath);
        appProps.load(inputStream);
        inputStream.close();

        String value = appProps.getProperty("language");
        return value;
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_CONFIG_PATH);
        if(file.exists()){
            file.delete();
        }
    }

    @Test
    void languageChange() throws IOException {
        writeTestFile(TEST_LANGUAGE);
        languageSwitch.languageChange(TEST_CONFIG_PATH, "Dutch");
        String readLanguage = readTestFile(TEST_CONFIG_PATH);
        assertEquals(readLanguage, "Dutch");
    }

    @Test
    void exceptionThrown() {
        assertThrows(RuntimeException.class, () -> {
            languageSwitch.languageChange("Fake path", "Dutch");
        });
    }
}