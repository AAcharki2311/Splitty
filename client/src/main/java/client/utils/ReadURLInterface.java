package client.utils;

public interface ReadURLInterface {
    /**
     * Read the server URL from the file
     * @return the server URL
     */
    String readServerUrl();

    /**
     * Write the server URL to the file
     * @param url the server URL
     */
    void writeServerUrl(String url);
}
