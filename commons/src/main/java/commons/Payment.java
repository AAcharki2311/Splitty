package commons;
import jakarta.persistence.*;

import java.util.*;
@Entity
public class Payment {
    @ManyToOne
    @JoinColumn(name = "payerId")
    private Participant payer;
    @ManyToOne
    @JoinColumn(name = "payeeId")
    private Participant payee;
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
     * @param payer Participant 1 id
     * @param payee Participant 2 id
     * @param event Event id
     * @param p_id Payment id
     * @param amount Amount paid
     * @param date Date of the payment
     */
    public Payment (Participant payer, Participant payee, Event event, int p_id, int amount, Date date) {
        this.payer = payer;
        this.payee = payee;
        this.event = event;
        this.p_id = p_id;
        this.amount = amount;
        this.date = date;
    }

    /**
     * The getter for payer
     *
     * @return Participant who paid
     */
    public Participant getPayer() {
        return payer;
    }

    /**
     * The setter for payer
     *
     * @param payer Participant who paid
     */
    public void setPayer(Participant payer) {
        this.payer = payer;
    }

    /**
     * The getter for payee
     *
     * @return Participant who got paid
     */
    public Participant getPayee() {
        return payee;
    }

    /**
     * The setter for payee
     *
     * @param payee Participant who got paid
     */
    public void setPayee(Participant payee) {
        this.payee = payee;
    }

    /**
     * The getter for event
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
        return Objects.hash(payer, payee, event, p_id, amount, date);
    }

    @Override
    public String toString() {
        return "Payments{" +
                "payer=" + payer +
                ", payee=" + payee +
                ", event=" + event +
                ", p_id=" + p_id +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}