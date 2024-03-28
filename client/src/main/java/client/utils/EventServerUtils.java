package client.utils;

import commons.Event;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

// import java.util.List;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class EventServerUtils {
    private final ReadURL readURL;
    private final String SERVER;

    /**
     * EventServerUtils constructor
     * @param readURL readURL object
     */
    @Inject
    public EventServerUtils(ReadURL readURL){
        this.readURL = readURL;
        this.SERVER = readURL.readServerUrl("src/main/resources/configfile.properties") + "/api/events";
    }

    /**
     * Javadoc
     * @return dweg
    */
    public List<Event> getAllEvents() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Event>>() {});
    }

    /**
     * Javadoc
     * @param event
     * @return duplicate return tag?
     */
    public Event addEvent(Event event) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(event, APPLICATION_JSON), Event.class);
    }

    /**
     * Javadoc
     * @param id
     * @return dsf
     */
    public Event getEventByID(long id) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/"+id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<Event>() {});
    }

    /**
     * Javadoc
     * @param id
     * @param event
     * @return sdf
     */
    public Event updateEventByID(long id, Event event) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/"+id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(event, APPLICATION_JSON), Event.class);
    }

    /**
     * Javadoc
     * @param id
     * @return sdf
     */
    public boolean deleteEventByID(long id) {
        Response response = ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete();
        return response.getStatus() == 204;
    }
}
