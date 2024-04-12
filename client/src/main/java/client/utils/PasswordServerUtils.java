package client.utils;

import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import org.glassfish.jersey.client.ClientConfig;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class PasswordServerUtils {

    private final ReadURL readURL;
    private static String serverUrl;

    /**
     * PasswordServerUtils constructor
     *
     * @param readURL readURL object
     */
    @Inject
    public PasswordServerUtils(ReadURL readURL) {
        this.readURL = readURL;
        serverUrl = readURL.readServerUrl("src/main/resources/configfile.properties") + "/api/password";
    }

    /**
     * Method to check if the input is correct
     * @param input the input to check
     * @return true if the input is correct
     */
    public boolean checkPassword(String input) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverUrl)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(input, APPLICATION_JSON), Boolean.class);
    }
}
