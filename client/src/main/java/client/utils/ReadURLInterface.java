package client.utils;

public interface ReadURLInterface {
    /**
     * Read the server URL from the file
     * @param configFilePath the path to the config file
     * @return the server URL
     */
    String readServerUrl(String configFilePath);

    /**
     * Write the server URL to the file
     * @param configFilePath the path to the config file
     * @param url the server URL
     */
    void writeServerUrl(String url, String configFilePath);
}
