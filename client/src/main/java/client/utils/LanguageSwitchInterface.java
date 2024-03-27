package client.utils;

import javafx.scene.control.ComboBox;

public interface LanguageSwitchInterface {

    /**
     * This method is used to change the language of the application
     * @param configFilePath the path to the configuration file
     * @param language the selected language
     */
    void languageChange(String configFilePath, String language);
}