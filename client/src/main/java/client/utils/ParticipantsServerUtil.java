package client.utils;

import commons.Participant;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientBuilder;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ParticipantsServerUtil {
    private final ReadURL readURL;
    private final String SERVER;
    /**
     * ParticipantsServerUtil constructor
     * @param readURL readURL object
     */
    @Inject
    public ParticipantsServerUtil(ReadURL readURL){
        this.readURL = readURL;
        this.SERVER = readURL.readServerUrl() + "/api/participants";
    }


    /**
     * @param participant the participant to add
     * @return the added participant
     */
    public Participant addParticipant(Participant participant) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(participant, APPLICATION_JSON), Participant.class);
    }


    /**
     * @return a list of all participants
     */
    public List<Participant> getAllParticipants() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Participant>>() {});
    }

    /**
     * @param id the id of the participant to find
     *           participant found at: https://localhost:8080/api/participants/id
     * @return the found participant with given id
     */
    public Participant getParticipantByID(long id) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/"+id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<Participant>() {});
    }

    /**
     * @param id the id of the participant to update
     *           participant found at: https://localhost:8080/api/participants/id
     * @param participant the updated participant, replaces the original at id
     * @return the new updated participant
     */
    public Participant updateParticipantByID(long id, Participant participant) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/"+id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(participant,APPLICATION_JSON), Participant.class);
    }

    /**
     * @param id the id of the participant to delete
     * @return whether the deletion was a success
     */
    public boolean deleteParticipantByID(long id) {
        Response response = ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/"+id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete();
        return response.getStatus() == 204;

    }
}
