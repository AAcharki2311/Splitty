package server.api;

import commons.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import commons.Participant;
import server.database.ParticipantRepository;
import java.util.*;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {
    private final Random random;
    private final ParticipantRepository participantRepository;

    /**
     * Constructor for the controller of the participants
     *
     * @param random variable random
     * @param participantRepository repository for participants
     */
    public ParticipantController(Random random, ParticipantRepository participantRepository) {
        this.random = random;
        this.participantRepository = participantRepository;
    }

    /**
     * Method to get all the participant in the repository (unsorted)
     *
     * @return all the participants in the current repository
     */
    @GetMapping(path = {"", "/"})
    public List<Participant> getParticipants() {
        return participantRepository.findAll();
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
        Participant postedParticipant = participantRepository.save(participant);
        return ResponseEntity.ok(postedParticipant);
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
        // Update also the other attributes?

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
     * @return list of all the participants of a specific event
     */
    @GetMapping("/participants/event/{eventId}")
    public List<Participant> getParticipants(@PathVariable("eventID") long eventId) {
        List<Participant> eventParticipant = new ArrayList<>();
        List<Participant> allParticipants = getParticipants();
        for(int i = 0; i < allParticipants.size(); i++) {
            Event event = allParticipants.get(i).getEvent();
            if (event.getId() == eventId) {
                eventParticipant.add(allParticipants.get(i));
            }
        }
        return eventParticipant;
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

}
