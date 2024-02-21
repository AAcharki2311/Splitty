package commons;

import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;
import java.util.*;


/**
 * This class represents an expense object
 */
@Entity
@Table(name = "expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "eventId")
    protected Event event;

    @ManyToOne
    @JoinColumn(name = "creditorId")
    protected Participant participant;

    private double amount;
    private Date date;
    private String description;
    private String tag;

    /**
     * The default constructor for the expense without args
     */
    public Expense() {
        // for object mapper
    }

    /**
     * Constructor for creating a new expense
     * @param event the {Event} associated with this {Expense}
     * @param participant the {participant} associated with this {Expense}
     * @param amount amount of the expense
     * @param date date of the expense
     * @param description description of the expense
     * @param tag tag of the expense
     */
    public Expense(Event event, Participant participant,
                   double amount, Date date,
                   String description, String tag) {
        this.event = event;
        this.participant = participant;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.tag = tag;
    }

    /**
     * Getter for the ID of the expense
     * @return ID of the expense
     */
    public long getId() {
        return id;
    }

    /**
     * Getter for the ID of the event
     * @return ID of the event
     */
    public long getEventId() {
        if (event != null) {
            return event.getId();
        } else {
            return 00;
        }
    }

    /**
     * Getter for the ID of the creditor
     * @return ID of the creditor
     */
    public long getCreditorId() {
        if (participant != null) {
            return participant.getId();
        } else {
            return 00;
        }
    }

    /**
     * Getter for the amount of the expense
     * @return amount of the expense
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Getter for the date of the expense
     * @return date of the expense
     */
    public Date getDate() {
        return date;
    }

    /**
     * Getter for the description of the expense
     * @return description of the expense
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for the tag of the expense
     * @return tag of the expense
     */
    public String getTag() {
        return tag;
    }

    /**
     * Setter for the amount of the expense
     * @param amount of the expense
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Setter for the date of the expense
     * @param date of the expense
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Setter for the description of the expense
     * @param description of the expense
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Setter for the tag of the expense
     * @param tag of the expense
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * To string method for the expense
     * @return string representation of the expense
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }

    /**
     * Equals method for the expense
     * @param obj object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * Hash code method for the expense
     * @return hash code of the expense
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
