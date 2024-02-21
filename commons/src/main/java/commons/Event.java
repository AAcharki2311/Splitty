package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;
import java.util.Objects;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private Date creationDate;
    private Date lastActDate;

    public Event(String name) {
        this.name = name;
        creationDate = new Date();
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
     * @param o the object to compare to
     * @return True if equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id;
    }

    /**
     * The hashing method for Event
     * The hash is solely based on the Event-ID
     *
     * @return the hash of the Event
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
