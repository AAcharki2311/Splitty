package client.utils;

import javafx.scene.control.ComboBox;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public interface languageSwitchInterface {

    /**
     * This method is used to switch the language of the application
     * @param taal the language that the user wants to switch to
     */
    public void langueageswitch(String taal);

    /**
     * This method is used to change the language of the application
     * @param comboboxLanguage the combobox that contains the languages
     */
    public default void languageChange(ComboBox comboboxLanguage){
        String selectedOption = String.valueOf(comboboxLanguage.getSelectionModel().getSelectedItem());
        try {
            Properties appProps = new Properties();
            String configFilePath = "C:\\Users\\ayoub\\oopp-ayoubacharki\\TEAM\\oopp-team-23\\commons\\src\\config\\configfile.properties";

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