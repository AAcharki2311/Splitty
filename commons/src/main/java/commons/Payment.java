package commons;
import jakarta.persistence.*;

import java.util.*;
@Entity
public class Payment {
    private int p1_id;
    private int p2_id;
    @ManyToOne
    @JoinColumn(name = "eventId")
    private Event event;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int p_id;
    private int amount;
    private Date date;

    /**
     * Constructor for Payments
     *
     * @param p1_id Participant 1 id
     * @param p2_id Participant 2 id
     * @param event Event id
     * @param p_id Payment id
     * @param amount Amount paid
     * @param date Date of the payment
     */
    public Payment (int p1_id, int p2_id, Event event, int p_id, int amount, Date date) {
        this.p1_id = p1_id;
        this.p2_id = p2_id;
        this.event = event;
        this.p_id = p_id;
        this.amount = amount;
        this.date = date;
    }

    /**
     * The getter for p1_id
     *
     * @return Participant 1 id
     */
    public int getP1_id() {
        return p1_id;
    }

    /**
     * The setter for p1_id
     *
     * @param p1_id Participant 1 id
     */
    public void setP1_id(int p1_id) {
        this.p1_id = p1_id;
    }

    /**
     * The getter for p2_id
     *
     * @return Participant 2 id
     */
    public int getP2_id() {
        return p2_id;
    }

    /**
     * The setter for p2_id
     *
     * @param p2_id Participant 2 id
     */
    public void setP2_id(int p2_id) {
        this.p2_id = p2_id;
    }

    /**
     * The getter for ev_id
     *
     * @return Event id
     */
    public Event getEvent() {
        return event;
    }

    /**
     * The setter for event
     *
     * @param event Event
     */
    public void setEv_id(Event event) {
        this.event = event;
    }

    /**
     * The getter for p_id
     *
     * @return Payment id
     */
    public int getP_id() {
        return p_id;
    }

    /**
     * The setter for p_id
     *
     * @param p_id Payment id
     */
    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    /**
     * The getter for amount
     *
     * @return Amount paid
     */
    public int getAmount() {
        return amount;
    }

    /**
     * The setter for amount
     *
     * @param amount Amount paid
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * The getter for date
     *
     * @return Date of the payment
     */
    public Date getDate() {
        return date;
    }

    /**
     * The setter for date
     *
     * @param date Date of the payment
     */
    public void setDate(Date date) {
        date = new Date();
    }

    /**
     * The equality for the class Payments
     * Two payments are equal if they are both objects Payments and their ids match
     *
     * @param o The object to compare to
     * @return True if equal, False if not or null
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payments = (Payment) o;
        return p_id == payments.p_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(p1_id, p2_id, event, p_id, amount, date);
    }

    @Override
    public String toString() {
        return "Payments{" +
                "p1_id=" + p1_id +
                ", p2_id=" + p2_id +
                ", event=" + event +
                ", p_id=" + p_id +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}