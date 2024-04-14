package server.service;

import commons.Event;
import commons.Participant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.repository.TestEventRepository;
import server.repository.TestParticipantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ParticipantServiceTest {
    private TestParticipantRepository participantRepository;
    private TestEventRepository eventRepository;
    private ParticipantService participantService;
    private Participant participant1;
    private Participant participant2;
    private Event eventTest = new Event("event");

    /**
     * Set up for the tests
     */
    @BeforeEach
    public void setup() {
        participantRepository = new TestParticipantRepository();
        participantService = new ParticipantService(participantRepository);
        participant1 = new Participant(eventTest, "nameTest1", "emailTest1", "ibanTest1", "bicTest1");
        participant2 = new Participant(eventTest, "nameTest2", "emailTest2", "ibanTest2", "bicTest2");
    }

    /**
     * Tests for the methods add and get
     */
    @Test
    public void addAndGetTest() {
        participantService.add(participant1);
        participantService.add(participant2);
        List<Participant> list = participantService.getParticipants();
        assertEquals(participant1, list.get(0));
        assertEquals(participant2, list.get(1));
    }

    /**
     * Test for the method getById
     */
    @Test
    public void getByIdTest() {
        participantService.add(participant1);
        long id1 = participant1.getId();
        participantService.add(participant2);
        long id2 = participant2.getId();
        Optional<Participant> participantTest1 = participantService.getById(id1);
        Optional<Participant> participantTest2 = participantService.getById(id2);
        assertTrue(participantTest1.isPresent());
        assertEquals(participant1, participantTest1.get());
        assertTrue(participantTest2.isPresent());
        assertNotEquals(participant2, participantTest2.get());
    }

    /**
     * Test for the method getById if it's non-existing
     */
    @Test
    public void getByWrongIdTest() {
        long id = -1;
        try {
            participantService.getById(id);
            fail("Expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // The method throws an exception if there is no object with that ID
        }
    }

    /**
     * Test for the update method
     */
    @Test
    public void updateTest() {
        participantService.add(participant1);
        Participant updatedParticipant = new Participant(eventTest, "nameTest", "emailTest", "ibanTest", "bicTest");
        Optional<Participant> participantTest = participantService.update(participant1.getId(), updatedParticipant);
        assertTrue(participantTest.isPresent());
        assertEquals(updatedParticipant, participantTest.get());
    }

    /**
     * Test to get all participants of a specific event
     */
    @Test
    public void getParticipantsEventTest() {
        participantService.add(participant1);
        participantService.add(participant2);
        List<Participant> eventParticipant = new ArrayList<>();
        eventParticipant.add(participant1);
        eventParticipant.add(participant2);
        List<Participant> participants = participantService.getParticipantsEvent(participant1.getEvent().getId());
        assertEquals(eventParticipant, participants);
    }

}
