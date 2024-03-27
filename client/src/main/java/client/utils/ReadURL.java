package client.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadURL {
    /**
     * This method reads the config file and returns the server url
     * @return server url
     */
    public String readServerUrl() {
        try{
            Properties appProps = new Properties();
            String configFilePath = "src/main/resources/configfile.properties";

            FileInputStream inputStream = new FileInputStream(configFilePath);
            appProps.load(inputStream);
            inputStream.close();

            String url = appProps.getProperty("serverurl");
            return url;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method reads the config file and returns the server url
     * @param url the url that the user wants to switch to
     */
    public void writeServerUrl(String url) {
        try {
            Properties appProps = new Properties();
            String configFilePath = "src/main/resources/configfile.properties";
            FileInputStream inputStream = new FileInputStream(configFilePath);
            appProps.load(inputStream);
            inputStream.close();

            appProps.setProperty("serverurl", url);

            FileOutputStream outputStream = new FileOutputStream(configFilePath);
            appProps.store(outputStream, null);
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
