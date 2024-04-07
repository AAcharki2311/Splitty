package server.service;

import commons.Event;
import commons.Participant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.repository.TestEventRepository;
import server.repository.TestParticipantRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
