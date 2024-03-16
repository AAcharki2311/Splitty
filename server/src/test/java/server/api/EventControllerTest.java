package server.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import commons.Event;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class EventControllerTest {
    public int nextInt;
    private MyRandom random;
    private TestEventRepository eventRepository;
    private EventController eventController;
    private Event event1;
    private Event event2;

    @BeforeEach
    public void setup() {
        random = new MyRandom();
        eventRepository = new TestEventRepository();
        eventController = new EventController(random, eventRepository);
        event1 = new Event("event1");
        event2 = new Event("event2");
    }

    @Test
    public void addAndGetTest() {
        eventController.add(event1);
        eventController.add(event2);
        List<Event> list = eventController.getEvents();
        assertEquals(event1, list.get(0));
        assertEquals(event2, list.get(1));
    }

    @Test
    public void getByIdTest() {
        eventController.add(event1);
        long id1 = event1.id;
        eventController.add(event2);
        long id2 = event2.id;
        ResponseEntity<Event> responseEntity1 = eventController.getById(id1);
        ResponseEntity<Event> responseEntity2 = eventController.getById(id2);
        assertEquals(event1, responseEntity1.getBody());
        assertNotEquals(event1, responseEntity2.getBody());
    }

    @Test
    public void getByWrongIdTest() {
        long id = -1;
        ResponseEntity<Event> responseEntity1 = eventController.getById(id);
        assertEquals(BAD_REQUEST, responseEntity1.getStatusCode());
    }

    @Test
    public void updateTest() {
        eventController.add(event1);
        Event updatedEvent = new Event("Updated Event");
        ResponseEntity<Event> responseEntity = eventController.update(event1.id, updatedEvent);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
        assertEquals(updatedEvent.getName(), responseEntity.getBody().getName()); // Check the updated name of the Event
        updatedEvent.setLastActDate(event1.lastActDate);
        responseEntity = eventController.update(event1.id, updatedEvent);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
        assertEquals(updatedEvent.getLastActDate(), responseEntity.getBody().getLastActDate()); // Check the updated lastActDate of the Event
    }

    @Test
    public void deleteTest() {
        eventController.add(event1);
        ResponseEntity<Void> responseEntity = eventController.delete(event1.id);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode()); // Check status code
        assertNull(responseEntity.getBody()); // Check that the object is null
    }

    @SuppressWarnings("serial")
    public class MyRandom extends Random {

        public boolean wasCalled = false;

        /**
         * Method necessary for testing
         *
         * @param bound the upper bound (exclusive).  Must be positive.
         * @return value of nextInt
         */
        @Override
        public int nextInt(int bound) {
            wasCalled = true;
            return nextInt;
        }
    }
}
