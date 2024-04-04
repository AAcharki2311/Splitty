package client.utils;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadURL implements ReadURLInterface {
    /**
     * This method reads the config file and returns the server url
     * @param configFilePath the path to the config file
     * @return server url
     */
    @Override
    public String readServerUrl(String configFilePath) {
        try{
            Properties appProps = new Properties();

            FileInputStream inputStream = new FileInputStream(configFilePath);
            appProps.load(inputStream);
            inputStream.close();

            String url = appProps.getProperty("serverurl");
            return url;
        } catch (IOException e) {
            int choice = JOptionPane.showOptionDialog(null, "Error reading config file. Please check if the file exists and if the url is correct!" +
                    "\n Or do you want to use the default URL (http://localhost:8080)?", "404 Server not Found", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Use default", "No, Try again"}, "default");
            if(choice == 0){
                return "http://localhost:8080";
            } else{
                JOptionPane.showMessageDialog(null, "Error reading config file. Please check if the file exists and if the url is correct!");
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * This method reads the config file and returns the server url
     * @param configFilePath the path to the config file
     * @param url the url that the user wants to switch to
     */
    @Override
    public void writeServerUrl(String url, String configFilePath) {
        try {
            Properties appProps = new Properties();
            FileInputStream inputStream = new FileInputStream(configFilePath);
            appProps.load(inputStream);
            appProps.setProperty("serverurl", url);
            inputStream.close();

            FileOutputStream outputStream = new FileOutputStream(configFilePath);
            appProps.store(outputStream, null);
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
