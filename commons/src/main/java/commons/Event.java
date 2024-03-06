package commons;
import jakarta.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;
import java.util.*;


@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String name;
    public Date creationDate;
    public Date lastActDate;

    /**
     * The constructor for Event without args
     */
    public Event() {
        //for object mapper
    }

    /**
     * The constructor for Event
     *
     * @param name The name for the event
     */
    public Event(String name) {
        this.name = name;
        creationDate = new Date();
        lastActDate = new Date();
    }

    /**
     * The getter for the Event-ID
     *
     * @return the Event-ID
     */
    public long getId() {
        return id;
    }

    /**
     * The getter for the name of the Event
     *
     * @return the name of the Event
     */
    public String getName() {
        return name;
    }

    /**
     * The getter for the creation date of the Event
     *
     * @return the creation date of the Event
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * The getter for the date of the last activity of the Event
     *
     * @return the last activity date of the Event
     */
    public Date getLastActDate() {
        return lastActDate;
    }

    /**
     * The setter for the name of the Event
     *
     * @param name the name to set the name of the Event to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The setter for the date of the last activity of the Event
     *
     * @param lastActDate the date to set the last activity date of the Event to
     */
    public void setLastActDate(Date lastActDate) {
        this.lastActDate = lastActDate;
    }

    /**
     * The equality method for Event
     * Events are considered equal iff they are both Events and their id's match
     *
     * @param obj the object to compare to
     * @return True if equal
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * The hashing method for Event
     * The hash is solely based on the Event-ID
     *
     * @return the hash of the Event
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * A method to return event in a human-readable format
     *
     * @return A string with the Event object converted to a Human-Readable Format
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
