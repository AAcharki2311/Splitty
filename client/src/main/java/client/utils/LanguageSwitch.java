package client.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class LanguageSwitch implements LanguageSwitchInterface{

    /**
     * This method is used to change the language of the application
     * @param language the selected language
     */
    @Override
    public void languageChange(String configFilePath, String language){
        String selectedOption = language;
        try {
            Properties appProps = new Properties();
            FileInputStream inputStream = new FileInputStream(configFilePath);
            appProps.load(inputStream);
            inputStream.close();

            appProps.setProperty("language", selectedOption);

            FileOutputStream outputStream = new FileOutputStream(configFilePath);
            appProps.store(outputStream, null);
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
