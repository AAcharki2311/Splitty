package commons;
import jakarta.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Event event;
    private String name;
    private String email;
    private String iban;
    private String bic;

    /**
     *
     * @param event the event that this person belongs to
     * @param name the name of this person
     * @param email the email of this person
     * @param iban the iban of this person
     * @param bic the bic of this person
     */
    public Participant(Event event, String name, String email, String iban, String bic) {
        this.event = event;
        this.name = name;
        this.email = email;
        this.iban = iban;
        this.bic = bic;
    }

    /**
     * The constructor for Participant without args
     */
    public Participant() {
        //for object mapper
    }

    /**
     * Getter for the id of this participant
     * @return the id of this participant
     */
    public long getId() {
        return id;
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
     * getter for the iban of this person
     * @return the iban of this person
     */
    public String getIban() {
        return iban;
    }

    /**
     * setter for the iban of this person
     * @param iban the new iban of this person
     */
    public void setIban(String iban) {
        this.iban = iban;
    }

    /**
     * getter for the bic of this person
     * @return the bic of this person
     */
    public String getBic() {
        return bic;
    }

    /**
     * setter for the bic of this person
     * @param bic the new bic of this person
     */
    public void setBic(String bic) {
        this.bic = bic;
    }

    /**
     * Equals method
     * @param obj the object to be compared
     * @return true or false depending on equality
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * Hashcode generator
     * @return the hashcode
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Makes a readable string of the information of the participant for development purposes
     * @return a string with the information of the participant
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }

    /**
     * Setter for the event of this participant
     * @param event the new event of this participant
     */
    public void setEvent(Event event) {
        this.event = event;
    }
}


