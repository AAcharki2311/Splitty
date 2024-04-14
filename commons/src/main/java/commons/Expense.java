package commons;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;
import java.util.*;


/**
 * This class represents an expense object
 */

@JsonIgnoreProperties(ignoreUnknown = true)
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
    protected Participant creditor;

    private double amount;
    private Date date;
    private String title;
    private String tag;
    private String cur;

    /**
     * The default constructor for the expense without args
     */
    public Expense() {
        // for object mapper
    }

    /**
     * Constructor for creating a new expense
     * @param event the {Event} associated with this {Expense}
     * @param creditor the {participant} associated with this {Expense}
     * @param amount amount of the expense
     * @param date date of the expense
     * @param title description of the expense
     * @param tag tag of the expense
     * @param cur currency of the expense
     */
    public Expense(Event event, Participant creditor,
                   double amount, Date date,
                   String title, String tag, String cur) {
        this.event = event;
        this.creditor = creditor;
        this.amount = amount;
        this.date = date;
        this.title = title;
        this.tag = tag;
        this.cur = cur;
    }

    /**
     * Constructor for creating a new expense
     * @param event the {Event} associated with this {Expense}
     * @param creditor the {participant} associated with this {Expense}
     * @param amount amount of the expense
     * @param date date of the expense
     * @param title description of the expense
     * @param tag tag of the expense
     * @param cur currency of the expense
     * @param id the id of the event
     */
    public Expense(Event event, Participant creditor,
                   double amount, Date date,
                   String title, String tag, String cur, long id) {
        this.event = event;
        this.creditor = creditor;
        this.amount = amount;
        this.date = date;
        this.title = title;
        this.tag = tag;
        this.cur = cur;
        this.id = id;
    }

    /**
     * Getter for the ID of the expense
     * @return ID of the expense
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for the ID of the expense
     * @param id the ID of the expense
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for the ID of the event
     * @return ID of the event
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Setter for the event
     * @param event the event to set
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * Getter for the creditor
     * @return the creditor
     */
    public Participant getCreditor() {
        return creditor;
    }

    /**
     * Setter for the creditor
     * @param creditor the participant to set
     */
    public void setCreditor(Participant creditor) {
        this.creditor = creditor;
    }

    /**
     * Getter for the amount of the expense
     * @return amount of the expense
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Setter for the amount of the expense
     * @param amount of the expense
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Getter for the date of the expense
     * @return date of the expense
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter for the date of the expense
     * @param date of the expense
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Getter for the title of the expense
     * @return title of the expense
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for the title of the expense
     * @param title of the expense
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for the tag of the expense
     * @return tag of the expense
     */
    public String getTag() {
        return tag;
    }

    /**
     * Setter for the tag of the expense
     * @param tag of the expense
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Getter for the currency of the expense
     * @return currency of the expense
     */
    public String getCur() {
        return cur;
    }

    /**
     * Setter for the currency of the expense
     * @param cur of the expense
     */
    public void setCur(String cur) {
        this.cur = cur;
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
