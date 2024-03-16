package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import commons.Event;
import server.database.EventRepository;
import java.util.*;

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
    public List<Event> getEvents() {
        return eventRepository.findAll();
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

    private boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

}
