package server.api;

import commons.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import commons.Participant;
import server.database.EventRepository;
import server.database.ParticipantRepository;
import java.util.*;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {
    private final Random random;
    private final ParticipantRepository participantRepository;
    @Autowired
    private final EventRepository eventRepository;


    /**
     * Constructor for the controller of the participants
     *
     * @param random variable random
     * @param participantRepository repository for participants
     * @param eventRepository repository for events
     */
    public ParticipantController(Random random, ParticipantRepository participantRepository, EventRepository eventRepository) {
        this.random = random;
        this.participantRepository = participantRepository;
        this.eventRepository = eventRepository;
    }

    /**
     * Method to get all the participant in the repository (unsorted)
     *
     * @return all the participants in the current repository
     */
    @GetMapping(path = {"", "/"})
    public ResponseEntity<List<Participant>> getParticipants() {
        List<Participant> allParticipants = participantRepository.findAll();
        if (allParticipants.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allParticipants);
    }

    /**
     * Method to find a participant from its id
     *
     * @param id id of the participant to find
     * @return the participant with that specific id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Participant> getById(@PathVariable("id") long id) {
        if (id < 0 || !participantRepository.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(participantRepository.findById(id).get());
    }

    /**
     * Method to create a participant and add it to the repository
     *
     * @param participant the participant that will be added
     * @return the response from the action
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<Participant> add(@RequestBody Participant participant) {
        if (participant == null || participant.getId() < 0 ||
        isNullOrEmpty(participant.getEmail()) || isNullOrEmpty(participant.getName()) ||
        participant.getEvent() == null || isNullOrEmpty(participant.getBic()) ||
        isNullOrEmpty(participant.getIban())) {
            return ResponseEntity.badRequest().build();
        }

        try{
            Event event = eventRepository.findById(participant.getEvent().getId()).orElse(null);
            if(event == null) {
                throw new NoSuchElementException();
            }
            participant.setEvent(event);
            Participant postedParticipant = participantRepository.save(participant);
            return ResponseEntity.ok(postedParticipant);
        } catch(NullPointerException e) {
            try {
                Participant postedParticipant = participantRepository.save(participant);
                return ResponseEntity.ok(postedParticipant);
            } catch (Exception ex) {
                return ResponseEntity.badRequest().build();
            }
        }

    }

    /**
     * Method to update a participant from its id
     *
     * @param id id of the participant to update
     * @param participant participant with the new parameters
     * @return the response from the action
     */
    @PutMapping("/{id}")
    public ResponseEntity<Participant> update(@PathVariable("id") long id, @RequestBody Participant participant) {
        if (!participantRepository.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        Participant currentParticipant = participantRepository.findById(id).orElse(null);
        if (currentParticipant == null) {
            return ResponseEntity.notFound().build();
        }
        currentParticipant.setName(participant.getName());
        currentParticipant.setEmail(participant.getEmail());
        currentParticipant.setBic(participant.getBic());
        currentParticipant.setIban(participant.getIban());

        Participant newParticipant = participantRepository.save(currentParticipant);
        return ResponseEntity.ok(newParticipant);
    }

    /**
     * Method to delete a participant from its id
     *
     * @param id id of the participant to delete
     * @return the response from the action
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        if (!participantRepository.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        participantRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Method to get all the participants of a specific event
     *
     * @param eventId id of the event to use
     * @return the response from the action
     */
    @GetMapping("/participants/event/{eventId}")
    public ResponseEntity<List<Participant>> getParticipantsEvent(@PathVariable("eventId") long eventId) {
        List<Participant> eventParticipants = new ArrayList<>();
        List<Participant> allParticipants = participantRepository.findAll();
        for(int i = 0; i < allParticipants.size(); i++) {
            Event event = allParticipants.get(i).getEvent();
            if (event.getId() == eventId) {
                eventParticipants.add(allParticipants.get(i));
            }
        }
        if (eventParticipants.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(eventParticipants);
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

}
