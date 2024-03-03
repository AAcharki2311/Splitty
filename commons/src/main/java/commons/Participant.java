package commons;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long p_id;
    @ManyToOne
    @JoinColumn(name = "eventId")
    private Event event;
    private String name;
    private String email;

    /**
     *
     * @param p_id the id of this person
     * @param event the event that this person belongs to
     * @param name the name of this person
     * @param email the email of this person
     */
    public Participant(long p_id, Event event, String name, String email) {
        this.p_id = p_id;
        this.event = event;
        this.name = name;
        this.email = email;
    }

    /**
     * Getter for the id of this participant
     * @return the id of this participant
     */
    public long getP_id() {
        return p_id;
    }

    /**
     * Getter for the event id of this person
     * @return the id of the event
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Getter for the name of this participant
     * @return the name of the participant
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of a participant, this can be used if someone made a spelling error
     * @param name the new name of the participant
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the email of a participant
     * @return the email of a participant
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for the email of a participant, this can be used if someone made a spelling error in their email
     * @param email the new email for this participant
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Equals method
     * @param o the object to be compared
     * @return true or false depending on equality
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return p_id == that.p_id && Objects.equals(event, that.event) && Objects.equals(name, that.name) && Objects.equals(email, that.email);
    }

    /**
     * Hashcode generator
     * @return the hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(p_id, event, name, email);
    }

    /**
     * Makes a readable string of the information of the participant for development purposes
     * @return a string with the information of the participant
     */
    @Override
    public String toString() {
        return "Participant{" +
                "p_id=" + p_id +
                ", event=" + event +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}


