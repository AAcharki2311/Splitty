package commons;
import jakarta.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;
import java.util.*;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "eventId")
    private Event event;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payerId")
    private Participant payer;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "receivId")
    private Participant receiv;

    private double amount;
    private Date date;

    /**
     * Constructor for Payments
     *
     * @param payer Participant 1 id
     * @param receiv Participant 2 id
     * @param event Event id
     * @param amount Amount paid
     * @param date Date of the payment
     */
    public Payment (Event event, Participant payer, Participant receiv, double amount, Date date) {
        this.event = event;
        this.payer = payer;
        this.receiv = receiv;
        this.amount = amount;
        this.date = date;
    }

    /**
     * The default constructor for the Payment without args
     */
    public Payment() {
        // for object mapper
    }

    /**
     * The getter for p_id
     * @return Payment id
     */
    public long getId() {
        return id;
    }

    /**
     * The setter for p_id
     * @param id Payment id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * The getter for event
     * @return Event id
     */
    public Event getEvent() {
        return event;
    }

    /**
     * The setter for event
     * @param event Event
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * The getter for payer
     * @return Participant who paid
     */
    public Participant getPayer() {
        return payer;
    }

    /**
     * The setter for payer
     * @param payer Participant who paid
     */
    public void setPayer(Participant payer) {
        this.payer = payer;
    }

    /**
     * The getter for receiv
     * @return Participant who got paid
     */
    public Participant getReceiv() {
        return receiv;
    }

    /**
     * The setter for receiv
     * @param receiv Participant who got paid
     */
    public void setReceiv(Participant receiv) {
        this.receiv = receiv;
    }

    /**
     * The getter for amount
     *
     * @return Amount paid
     */
    public double getAmount() {
        return amount;
    }

    /**
     * The setter for amount
     * @param amount Amount paid
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * The getter for date
     * @return Date of the payment
     */
    public Date getDate() {
        return date;
    }

    /**
     * The setter for date
     * @param date Date of the payment
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * The equality for the class Payments
     * Two payments are equal if they are both objects Payments and their ids match
     * @param obj The object to compare to
     * @return True if equal, False if not or null
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * The hashcode for the class Payments
     * @return The hashcode of the object
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * The toString method for the class Payments
     * @return The string representation of the object
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}