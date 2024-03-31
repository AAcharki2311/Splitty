package server.service;

import commons.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.ParticipantRepository;
import commons.Participant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    /**
     * The constructor for the class
     *
     * @param participantRepository the repository of Event
     */
    @Autowired
    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    /**
     * Method to get all the events in the repository
     *
     * @return all the events in the current repository
     */
    public List<Participant> getParticipants() {
        return participantRepository.findAll();
    }

    /**
     * Method to find an event from its id
     *
     * @param id id of the event to find
     * @return the event with that specific id (if there is one)
     */
    public Optional<Participant> getById(long id) {
        return participantRepository.findById(id);
    }

    /**
     * Method to add an event to the repository
     *
     * @param participant the event that will be added
     * @return the added event
     */
    public Participant add(Participant participant) {
        return participantRepository.save(participant); // I still need to add some validation here
    }

    /**
     * Method to update an event from its id
     *
     * @param id id of the event to update
     * @param updatedParticipant event with the new parameters
     * @return the updated event if there are no errors
     */
    public Optional<Participant> update(long id, Participant updatedParticipant) {
        if(participantRepository.existsById(id)) {
            updatedParticipant.setId(id); // We need to keep the same id
            return Optional.of(participantRepository.save(updatedParticipant));
        }
        else {
            return Optional.empty();
        }
    }

    /**
     * Method to delete a participant from its id
     *
     * @param id id of the event to delete
     * @return true if the event has been deleted, false if there was no event with that id
     */
    public boolean delete(long id) {
        if (participantRepository.existsById(id)) {
            participantRepository.deleteById(id);
            return true;
        }
        else {return false;}
    }

    /**
     * Method to have list of all the participant in an event
     *
     * @param eventId Id of the event
     * @return a list with all the participants
     */
    public List<Participant> getParticipantsEvent(long eventId) {
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

}
