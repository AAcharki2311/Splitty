package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.EventRepository;
import commons.Event;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    /**
     * The constructor for the class
     *
     * @param eventRepository the repository of Event
     */
    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Method to get all the events in the repository
     *
     * @return all the events in the current repository
     */
    public List<Event> getEvents() {
        List<Event> allEvents = eventRepository.findAll();
        if(allEvents.isEmpty()) {
            throw new NoSuchElementException("The repository is empty");
        }
        return allEvents;
    }

    /**
     * Method to find an event from its id
     *
     * @param id id of the event to find
     * @return the event with that specific id (if there is one)
     */
    public Optional<Event> getById(long id) {
        Optional<Event> event = eventRepository.findById(id);
        if(event.isEmpty()) {
            throw new NoSuchElementException("There is no expense with the given ID");
        }
        return event;
    }

    /**
     * Method to add an event to the repository
     *
     * @param event the event that will be added
     * @return the added event
     */
    public Event add(Event event) {
        if (event == null || isNullOrEmpty(event.name) ||
                event.lastActDate == null || event.creationDate == null) {
            throw new IllegalArgumentException("Event is not valid");
        }
        return eventRepository.save(event);
    }

    /**
     * Method to update an event from its id
     *
     * @param id id of the event to update
     * @param updatedEvent event with the new parameters
     * @return the updated event if there are no errors
     */
    public Optional<Event> update(long id, Event updatedEvent) {
        if(eventRepository.existsById(id)) {
            updatedEvent.setId(id); // We need to keep the same id
            return Optional.of(eventRepository.save(updatedEvent));
        }
        else {
            return Optional.empty();
        }
    }

    /**
     * Method to delete an event from its id
     *
     * @param id id of the event to delete
     * @return true if the event has been deleted, false if there was no event with that id
     */
    public boolean delete(long id) {
        if (!eventRepository.existsById(id)) {
            return false;
        }
        eventRepository.deleteById(id);
        return true;
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
