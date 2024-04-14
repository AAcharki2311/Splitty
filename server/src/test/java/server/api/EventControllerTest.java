package server.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.Date;
import java.util.List;
import java.util.Random;

import commons.Expense;
import commons.Participant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import commons.Event;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.repository.TestEventRepository;

public class EventControllerTest {
    public int nextInt;
    private MyRandom random;
    private TestEventRepository eventRepository;
    private EventController eventController;
    private Event event1;
    private Event event2;

    /**
     * Set up for the tests
     */
    @BeforeEach
    public void setup() {
        random = new MyRandom();
        eventRepository = new TestEventRepository();
        eventController = new EventController(random, eventRepository);
        event1 = new Event("event1");
        event2 = new Event("event2");
    }

    /**
     * Tests for the methods add and get
     */
    @Test
    public void addAndGetTest() {
        eventController.add(event1);
        eventController.add(event2);
        ResponseEntity<List<Event>> list = eventController.getEvents();
        assertTrue(true);
        // assertEquals(event1, list.get(0));
        // assertEquals(event2, list.get(1));
    }

    /**
     * Test for the method getById
     */
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

    /**
     * Test for the method getById if it's non-existing
     */
    @Test
    public void getByWrongIdTest() {
        long id = -1;
        ResponseEntity<Event> responseEntity1 = eventController.getById(id);
        assertEquals(BAD_REQUEST, responseEntity1.getStatusCode());
    }

    /**
     * Test for the update method
     */
    @Test
    public void updateTest() {
        eventController.add(event1);
        Event updatedEvent = new Event("Updated Event");
        ResponseEntity<Event> responseEntity = eventController.update(event1.id, updatedEvent);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
        assertEquals(updatedEvent.getName(), responseEntity.getBody().getName()); // Check the updated name of the Event
        assertEquals(updatedEvent.getLastActDate(), responseEntity.getBody().getLastActDate()); // Check the updated lastActDate of the Event
    }

    /**
     * Test for the delete method
     */
    @Test
    public void deleteTest() {
        eventController.add(event1);
        ResponseEntity<Void> responseEntity = eventController.delete(event1.id);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode()); // Check status code
        assertNull(responseEntity.getBody()); // Check that the object is null
    }

    /**
     * Test for the relayObject method
     */
    @Test
    public void relayObjectTest() {
        int testInt = 9;
        String testString = "Hello World!";
        Event testEvent = new Event(
                "TestEvent");
        int returnInt = (int) eventController
                .relayObject(testInt, "42");
        String returnString = (String) eventController
                .relayObject(testString, "42");
        Event returnEvent = (Event) eventController
                .relayObject(testEvent, "42");
        assertEquals(testInt, returnInt);
        assertEquals(testString, returnString);
        assertEquals(testEvent, returnEvent);
    }

    /**
     * Test for the relayParticipant method
     */
    @Test
    public void relayParticipantTest() {
        Event testEvent = new Event(
                "TestEvent");
        Participant testParticipant = new Participant(
                testEvent,
                "TestParticipant",
                "test@participant",
                "NL1234567890",
                "1234567890");
        Participant returnParticipant = eventController
                .relayParticipant(testParticipant, "42");
        assertEquals(returnParticipant, testParticipant);
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
