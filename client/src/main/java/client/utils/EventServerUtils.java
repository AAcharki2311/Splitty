package client.utils;

import commons.Event;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

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

    private static final ExecutorService EXEC = Executors.newSingleThreadExecutor();

    /**
     * a method that registers for updates
     * @param consumer the consumer that will accept the updates
    */
    public void registerForUpdates(Consumer<Event> consumer) {
        EXEC.submit(() -> {
            while(!Thread.interrupted()){
                var res =  ClientBuilder.newClient(new ClientConfig()) //
                        .target(SERVER).path("update") //
                        .request(APPLICATION_JSON) //
                        .accept(APPLICATION_JSON) //
                        .get(Response.class);

                if(res.getStatus() == 204){
                    continue;
                }
                var e = res.readEntity(Event.class);
                consumer.accept(e);
            }
        });
    }

    /**
     * Stops the executor service
     */
    public void stop(){
        EXEC.shutdownNow();
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
     * @param id The id of the event to delete
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

    private StompSession session = connect("ws://localhost:8080/websocket");

    /**
     * Method that instantiates a websocket connection to the server
     *
     * @param url the url of the websocket endpoint
     * @return the connected session
     */
    private StompSession connect(String url) {
        var client = new StandardWebSocketClient();
        var stomp = new WebSocketStompClient(client);
        stomp.setMessageConverter(new MappingJackson2MessageConverter());
        try {
            StompSession session = stomp.connectAsync(url, new StompSessionHandlerAdapter() {
            }).get();
            System.out.println("[Websocket] Connected!");
            return session;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException();
    }

    /**
     * Method that registers for the messages on a channel
     *
     * @param dest the channel url to subscribe to
     * @param type the class to received messages of
     * @param consumer the consumer that will accept the messages
     * @param <T> The class to receive messages of
     */
    public <T> void registerForObjectUpdates(String dest, Class<T> type, Consumer<T> consumer) {
        session.subscribe(dest, new StompFrameHandler() {

            /**
             * Invoked before {@link #handleFrame(StompHeaders, Object)} to determine the
             * type of Object the payload should be converted to.
             *
             * @param headers the headers of a message
             */
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return type;
            }

            /**
             * Handle a STOMP frame with the payload converted to the target type returned
             * from {@link #getPayloadType(StompHeaders)}.
             *
             * @param headers the headers of the frame
             * @param payload the payload, or {@code null} if there was no payload
             */
            @SuppressWarnings("unchecked")
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((T) payload);
            }
        });
        System.out.println("Subscribed to "+'"'+dest+'"');
    }

    /**
     * Method for sending objects to the specified url
     *
     * @param dest the url
     * @param o the object to send
     */
    public void send(String dest, Object o) {
        session.send(dest, o);
        System.out.println("A message was sent:"+o);
    }
}
