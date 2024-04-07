package server.service;

import commons.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.repository.TestEventRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTest {
    private TestEventRepository eventRepository;
    private EventService eventService;
    private Event event1;
    private Event event2;

    /**
     * Set up for the tests
     */
    @BeforeEach
    public void setup() {
        eventRepository = new TestEventRepository();
        eventService = new EventService(eventRepository);
        event1 = new Event("event1");
        event2 = new Event("event2");
    }

    /**
     * Tests for the methods add and get
     */
    @Test
    public void addAndGetTest() {
        eventService.add(event1);
        eventService.add(event2);
        List<Event> list = eventService.getEvents();
        assertEquals(event1, list.get(0));
        assertEquals(event2, list.get(1));
    }

    /**
     * Test for the method getById
     */
    @Test
    public void getByIdTest() {
        eventService.add(event1);
        long id1 = event1.id;
        eventService.add(event2);
        long id2 = event2.id;
        Optional<Event> event1Test = eventService.getById(id1);
        Optional<Event> event2Test = eventService.getById(id2);
        assertTrue(event1Test.isPresent());
        assertEquals(event1, event1Test.get());
        assertTrue(event2Test.isPresent());
        assertNotEquals(event1, event2Test.get());
    }

    /**
     * Test for the method getById if it's non-existing
     */
    @Test
    public void getByWrongIdTest() {
        long id = -1;
        Optional<Event> eventTest = eventService.getById(id);
        assertFalse(eventTest.isPresent());
    }

    /**
     * Test for the update method
     */
    @Test
    public void updateTest() {
        eventService.add(event1);
        Event updatedEvent = new Event("Updated Event");
        Optional<Event> eventTest = eventService.update(event1.id, updatedEvent);
        assertTrue(eventTest.isPresent());
        assertEquals(updatedEvent, eventTest.get());
    }

    /*
      Test for the delete method
     */
    // @Test
    // public void deleteTest() {
     //    eventService.add(event2);
     //    eventService.delete(event2.getId());
     //    assertNull(eventService.getById(event2.id));
    // }
}
