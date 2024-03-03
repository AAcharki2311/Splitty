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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "eventId")
    protected Event event;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "creditorId")
    protected Participant participant;

    private double amount;
    private Date date;
    private String title;
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
     * @param title description of the expense
     * @param tag tag of the expense
     */
    public Expense(Event event, Participant participant,
                   double amount, Date date,
                   String title, String tag) {
        this.event = event;
        this.participant = participant;
        this.amount = amount;
        this.date = date;
        this.title = title;
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
    public Event getEvent() {
        return event;
    }

    /**
     * Getter for the creditor
     * @return the creditor
     */
    public Participant getCreditor() {
        return participant;
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
     * Getter for the title of the expense
     * @return title of the expense
     */
    public String getTitle() {
        return title;
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
     * Setter for the title of the expense
     * @param title of the expense
     */
    public void setTitle(String title) {
        this.title = title;
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
