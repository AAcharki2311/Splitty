package server.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import commons.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import commons.Participant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.repository.TestEventRepository;
import server.repository.TestParticipantRepository;

public class ParticipantControllerTest {
    public int nextInt;
    private ParticipantControllerTest.MyRandom random;
    private TestParticipantRepository participantRepository;
    private TestEventRepository eventRepository;
    private ParticipantController participantController;
    private Participant participant1;
    private Participant participant2;
    private Event eventTest = new Event("event");

    /**
     * Set up for the tests
     */
    @BeforeEach
    public void setup() {
        random = new ParticipantControllerTest.MyRandom();
        participantRepository = new TestParticipantRepository();
        participantController = new ParticipantController(random, participantRepository, eventRepository);
        participant1 = new Participant(eventTest, "nameTest1", "emailTest1", "ibanTest1", "bicTest1");
        participant2 = new Participant(eventTest, "nameTest2", "emailTest2", "ibanTest2", "bicTest2");
    }

    /**
     * Tests for the methods add and get
     */
    @Test
    public void addAndGetTest() {
        participantController.add(participant1);
        participantController.add(participant2);
        List<Participant> list = participantController.getParticipants();
        assertEquals(participant1, list.get(0));
        assertEquals(participant2, list.get(1));
    }

    /**
     * Test for the method getById
     */
    @Test
    public void getByIdTest() {
        participantController.add(participant1);
        long id1 = participant1.getId();
        participantController.add(participant2);
        long id2 = participant2.getId();
        ResponseEntity<Participant> responseEntity1 = participantController.getById(id1);
        ResponseEntity<Participant> responseEntity2 = participantController.getById(id2);
        assertEquals(participant1, responseEntity1.getBody());
        assertNotEquals(participant2, responseEntity2.getBody());
    }

    /**
     * Test for the method getById if it's non-existing
     */
    @Test
    public void getByWrongIdTest() {
        long id = -1;
        ResponseEntity<Participant> responseEntity1 = participantController.getById(id);
        assertEquals(BAD_REQUEST, responseEntity1.getStatusCode());
    }

    /**
     * Test for the update method
     */
    @Test
    public void updateTest() {
        participantController.add(participant1);
        Participant updatedParticipant = new Participant(eventTest, "nameTest", "emailTest", "ibanTest", "bicTest");
        ResponseEntity<Participant> responseEntity = participantController.update(participant1.getId(), updatedParticipant);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
        assertEquals(updatedParticipant.getName(), responseEntity.getBody().getName()); // Check the updated name of the Participant
        assertEquals(updatedParticipant.getEmail(), responseEntity.getBody().getEmail()); // Check the updated email of the Participant
    }

    /**
     * Test for the delete method
     */
    @Test
    public void deleteTest() {
        participantController.add(participant1);
        ResponseEntity<Void> responseEntity = participantController.delete(participant1.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode()); // Check status code
        assertNull(responseEntity.getBody()); // Check that the object is null
    }

    /**
     * Test to get all participants of a specific event
     */
    @Test
    public void getParticipantsEventTest() {
        participantController.add(participant1);
        participantController.add(participant2);
        List<Participant> eventParticipant = new ArrayList<>();
        eventParticipant.add(participant1);
        eventParticipant.add(participant2);
        ResponseEntity<List<Participant>> responseEntity = participantController.getParticipantsEvent(participant1.getEvent().getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
        assertEquals(responseEntity.getBody(), eventParticipant);
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
