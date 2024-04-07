package server.api;

import commons.Event;
import commons.Participant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.database.EventRepository;

import java.util.*;
import java.util.function.Consumer;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final Random random;
    private final EventRepository eventRepository;

    /**
     * Constructor for the controller of the events
     *
     * @param random variable random
     * @param eventRepository repository for events
     */
    public EventController(Random random, EventRepository eventRepository) {
        this.random = random;
        this.eventRepository = eventRepository;
    }

    /**
     * Method to get all the events in the repository
     *
     * @return all the events in the current repository
     */
    @GetMapping(path = {"", "/"})
    public ResponseEntity<List<Event>> getEvents() {
        List<Event> allEvents = eventRepository.findAll();
        if (allEvents.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allEvents); // Fix in other classes + tests
    }

    private Map<Object, Consumer<Event>> listeners = new HashMap<>();

    /**
     * Method to get updates from the events
     * @return the updates from the events
     */
    @GetMapping("/update")
    public DeferredResult<ResponseEntity<Event>> getUpdates() {
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var error = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        var res = new DeferredResult<ResponseEntity<Event>>(5000L, noContent);

        res.onTimeout(() -> {
            res.setErrorResult(noContent);
        });

        res.onError(err -> {
            res.setErrorResult(error);
        });

        var key = new Object();
        listeners.put(key, event -> {
            res.setResult(ResponseEntity.ok(event));
        });

        res.onCompletion(() -> {
            listeners.remove(key);
        });

        return res;
    }

    /**
     * Method to find an event from its id
     *
     * @param id id of the event to find
     * @return the event with that specific id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Event> getById(@PathVariable("id") long id) {
        if (id < 0 || !eventRepository.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if(optionalEvent.isPresent()) {
            return ResponseEntity.ok(eventRepository.findById(id).get());
        } else return ResponseEntity.notFound().build();
    }

    /**
     * Method to create an event and add it to the repository
     *
     * @param event the event that will be added
     * @return the response from the action
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<Event> add(@RequestBody Event event) {
        if (event == null || isNullOrEmpty(event.name) || event.lastActDate == null || event.creationDate == null) {
            return ResponseEntity.badRequest().build();
        }

        listeners.forEach((key, consumer) -> consumer.accept(event));

        Event postedEvent = eventRepository.save(event);
        return ResponseEntity.ok(postedEvent);
    }

    /**
     * Method to update an event from its id
     *
     * @param id id of the event to update
     * @param event event with the new parameters
     * @return the response from the action
     */
    @PutMapping("/{id}")
    public ResponseEntity<Event> update(@PathVariable("id") long id, @RequestBody Event event) {
        if (!eventRepository.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        Event currentEvent = eventRepository.findById(id).orElse(null);
        if (currentEvent == null) {
            return ResponseEntity.notFound().build();
        }
        currentEvent.setName(event.getName());
        currentEvent.setLastActDate(event.getLastActDate());

        listeners.forEach((key, consumer) -> consumer.accept(currentEvent));

        Event newEvent = eventRepository.save(currentEvent);
        return ResponseEntity.ok(newEvent);
    }

    /**
     * Method to delete an event from its id
     *
     * @param id id of the event to delete
     * @return the response from the action
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        if (!eventRepository.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        eventRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * Method that receives and sends participants via the websocket
     *
     * @param p The Participant received
     * @param id The id of the event from which received
     * @return The Participant that was received
     */
    @MessageMapping("/events/{id}") // /app/events/{id}
    @SendTo("/topic/events/{id}")
    public Participant sendConfirmationMessage(Participant p, @DestinationVariable("id") String id) {
        System.out.println("[Websocket] Received and sending to id("+id+"):\n"+p);
        return p;
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

}
