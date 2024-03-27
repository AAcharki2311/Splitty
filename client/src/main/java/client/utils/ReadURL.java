package client.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadURL {
    /**
     * This method reads the config file and returns the server url
     * @return server url
     */
    public String readServerUrl() throws IOException {
        Properties appProps = new Properties();
        String configFilePath = "src/main/resources/configfile.properties";

        FileInputStream inputStream = new FileInputStream(configFilePath);
        appProps.load(inputStream);
        inputStream.close();

        String url = appProps.getProperty("serverurl");
        return url;
    }
}
