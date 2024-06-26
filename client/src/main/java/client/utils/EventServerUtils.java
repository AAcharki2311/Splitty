package client.utils;

import commons.Event;
import commons.Expense;
import commons.Participant;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
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
    private static String serverURL;
    private StompSession WEBSOCKET;
    private StandardWebSocketClient client;
    private WebSocketStompClient stomp;

    /**
     * EventServerUtils constructor
     *
     * @param readURL readURL object
     */
    @Inject
    public EventServerUtils(ReadURL readURL) {
        this.readURL = readURL;
        serverURL = readURL.readServerUrl("src/main/resources/configfile.properties") + "/api/events";
    }

    /**
     * EventServerUtils constructor for mockito
     *
     * @param readURL readURL object
     * @param client StandardWebSocketClient object
     * @param stomp WebSocketStompClient object
     */
    public EventServerUtils(ReadURL readURL, StandardWebSocketClient client, WebSocketStompClient stomp) {
        this.readURL = readURL;
        this.client = client;
        this.stomp = stomp;
        serverURL = readURL.readServerUrl("src/main/resources/configfile.properties") + "/api/events";
    }

    /**
     * Gets all the events in the database
     *
     * @return a list of all the events
     */
    public List<Event> getAllEvents() {
        List<Event> res;
        try {
            res = ClientBuilder.newClient(new ClientConfig()) //
                    .target(serverURL) //
                    .request(APPLICATION_JSON) //
                    .accept(APPLICATION_JSON) //
                    .get(new GenericType<List<Event>>() {
                    });
        } catch (NotFoundException e) {
            return List.of();
        }
        return res;
    }

    private static final ExecutorService EXEC = Executors.newSingleThreadExecutor();

    /**
     * a method that registers for updates
     *
     * @param consumer the consumer that will accept the updates
     */
    public void registerForUpdates(Consumer<Event> consumer) {
        EXEC.submit(() -> {
            while (!Thread.interrupted()) {
                var res = ClientBuilder.newClient(new ClientConfig()) //
                        .target(serverURL).path("update") //
                        .request(APPLICATION_JSON) //
                        .accept(APPLICATION_JSON) //
                        .get(Response.class);

                if (res.getStatus() == 204) {
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
    public void stop() {
        EXEC.shutdownNow();
    }

    /**
     * Adds an event to the server
     *
     * @param event the event to add
     * @return the added event
     */
    public Event addEvent(Event event) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(serverURL) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(event, APPLICATION_JSON), Event.class);
    }

    /**
     * Get event by ID
     *
     * @param id the id of the vent
     * @return the event of that id
     */
    public Event getEventByID(long id) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(serverURL).path("/" + id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<Event>() {
                });
    }

    /**
     * Update event by id
     *
     * @param id the id of the event
     * @param event the new updated event
     * @return the updated event
     */
    public Event updateEventByID(long id, Event event) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverURL).path("/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(event, APPLICATION_JSON), Event.class);
    }

    /**
     * Delete event
     *
     * @param id The id of the event to delete
     * @return boolean to see if events has been deleted or not
     */
    public boolean deleteEventByID(long id) {
        Response response = ClientBuilder.newClient(new ClientConfig())
                .target(serverURL).path("/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete();
        return response.getStatus() == 204;
    }


    /**
     * Method that instantiates a websocket connection to the server
     *
     * @param url the url of the websocket endpoint
     * @return the connected session
     */
    StompSession connect(String url) {
        if(client == null) client = new StandardWebSocketClient();
        if(stomp == null) stomp = new WebSocketStompClient(client);
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
     * Method that creates a new websocket connection, severing any previous ones, and subscribes to all Participants and Expenses for a specific Event
     *
     * @param eventID             The ID of the Event the method should subscribe to.
     * @param participantConsumer The Consumer that handles all Participants received by the websocket
     * @param expenseConsumer     The Consumer that handles all Expenses received by the websocket
     */
    public void initiateWebsocketEventConnection(long eventID, Consumer<Participant> participantConsumer, Consumer<Expense> expenseConsumer) {
        if (WEBSOCKET != null && WEBSOCKET.isConnected()) {
            WEBSOCKET.disconnect();
            System.out.println("[Websocket] Disconnected from a previous event");
        }

        String websocketDest = findWebsocketURL(readURL.readServerUrl("src/main/resources/configfile.properties")) + "/websocket";
        System.out.println(websocketDest);
        WEBSOCKET = connect(websocketDest);

        String participantDest = "/topic/events/" + String.valueOf(eventID) + "/participants";
        WEBSOCKET.subscribe(participantDest, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Participant.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                participantConsumer.accept((Participant) payload);
            }
        });
        System.out.println("[Websocket] Subscribed to Participants on id: " + eventID);

        String expenseDest = "/topic/events/" + eventID + "/expenses";
        WEBSOCKET.subscribe(expenseDest, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Expense.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                expenseConsumer.accept((Expense) payload);
            }
        });
        System.out.println("[Websocket] Subscribed to Expenses on id: " + eventID);
    }

    /**
     * Method for disconnecting the websocket connection if one exists
     *
     * @return True if a connection was found and severed, False if no connection was found
     */
    public boolean disconnectFromWebsocket() {
        if (WEBSOCKET != null && WEBSOCKET.isConnected()) {
            WEBSOCKET.disconnect();
            return true;
        }
        return false;
    }

    private String findWebsocketURL(String httpServer) {
        if (httpServer != null && httpServer.contains("http")) {
            String result = httpServer.replaceFirst("http", "ws");
            return result;
        }
        throw new IllegalArgumentException("Server url contains no 'http'");
    }

    /**
     * Sends a payload to the websocket connection
     *
     * @param dest The URL to send to
     * @param o    The payload
     */
    public void send(String dest, Object o) {
        WEBSOCKET.send(dest, o);
        System.out.println("[WEBSOCKET] An object was sent:\n" + o);
    }

    /**
     * A simple setter for the websocket connection
     *
     * @param c the new StompSession to set
     */
    public void setWebsocketConnection(StompSession c) {
        this.WEBSOCKET = c;
    }

    /**
     * A simple getter for the websocket connection
     *
     * @return the StompSession
     */
    public StompSession getWebsocketConnection() {
        return WEBSOCKET;
    }
}
