package client.utils;

import jakarta.inject.Inject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PasswordServerUtils {

    private final ReadURL readURL;
    private final String SERVER;

    /**
     * PasswordServerUtils constructor
     *
     * @param readURL readURL object
     */
    @Inject
    public PasswordServerUtils(ReadURL readURL) {
        this.readURL = readURL;
        this.SERVER = readURL.readServerUrl("src/main/resources/configfile.properties") + "/api/generate-password";
    }

    /**
     * Generates password from the server
     * @return the password
     * @throws IOException
     */
    public String generatePasswordFromServer() throws IOException {
        URL url = new URL(SERVER);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");

        // Check the response code
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String generatedPassword = reader.readLine(); // Assuming the server returns the password as plain text
            connection.disconnect();
            return generatedPassword;
        } else {
            connection.disconnect();
            throw new IOException("Failed to generate password. HTTP Error Code: " + connection.getResponseCode());
        }
    }
}
