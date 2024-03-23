package commons;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    private static final Event SOME_EVENT = new Event("TestEvent");

    private static final Participant SOME_PAYER = new Participant(SOME_EVENT, "Max",
            "Max@gmail.com", "NL99ABNA0123456789", "RABONL2U");
    private static final Participant SOME_RECIVER = new Participant(SOME_EVENT, "John",
            "John@gmail.com", "NL98INGB9876543210", "INGBL47");
    private static final Event NEW_EVENT = new Event("NewEvent");
    private static final Participant NEW_PAYER = new Participant(NEW_EVENT, "MaxNEW",
            "MaxNEW@gmail.com", "NLNEW99ABNA0123456789", "NEWRABONL2U");
    private static final Participant NEW_RECIVER = new Participant(NEW_EVENT, "NEWJohn",
            "NEWJohn@gmail.com", "NEWNL98INGB9876543210", "NEWINGBL47");

    /**
     * test constructor
     */
    @Test
    public void checkConstructor () {
        Date d = new Date(2024-11-10);
        Payment p = new Payment(SOME_EVENT, SOME_PAYER, SOME_RECIVER, 9.65, d);
        assertEquals(SOME_EVENT, p.getEvent());
        assertEquals(SOME_PAYER, p.getPayer());
        assertEquals(SOME_RECIVER, p.getReceiv());
        assertEquals(9.65, p.getAmount());
        assertEquals(d, p.getDate());
    }

    @Test
    void emptyConstructor(){
        Payment p = new Payment();
        assertNotNull(p);
    }

    @Test
    void getId(){
        Date d = new Date(2024-11-10);
        Payment p = new Payment(SOME_EVENT, SOME_PAYER, SOME_RECIVER, 9.65, d);
        assertNotNull(p.getId());
    }

    @Test
    void setId(){
        Date d = new Date(2024-11-10);
        Payment p = new Payment(SOME_EVENT, SOME_PAYER, SOME_RECIVER, 9.65, d);
        p.setId(5);
        assertEquals(5, p.getId());
    }

    /**
     * test getter and setters
     */
    @Test
    public void testGettersAndSetters() {
        Date d = new Date(2024-11-10);
        Payment p = new Payment(SOME_EVENT, SOME_PAYER, SOME_RECIVER, 9.65, d);
        Date d1 = new Date(2023-12-11);

        p.setEvent(NEW_EVENT);
        assertEquals(NEW_EVENT, p.getEvent());
        p.setPayer(NEW_PAYER);
        assertEquals(NEW_PAYER, p.getPayer());
        p.setReceiv(NEW_RECIVER);
        assertEquals(NEW_RECIVER, p.getReceiv());

        p.setAmount(8.72);
        assertEquals(8.72, p.getAmount());
        p.setDate(d1);
        assertEquals(d1, p.getDate());

    }

    /**
     * test equals
     */
    @Test
    public void testEquals() {
        Date d = new Date(2024-11-10);
        Payment p1 = new Payment(SOME_EVENT, SOME_PAYER, SOME_RECIVER, 9.65, d);
        Payment p2 = new Payment(SOME_EVENT, SOME_PAYER, SOME_RECIVER, 9.65, d);
        Payment p3 = new Payment(SOME_EVENT, SOME_PAYER, SOME_RECIVER, 5.73, d);
        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
    }

    /**
     * test hash method
     */
    @Test
    public void testHashCode() {
        Date d = new Date(2024-11-10);
        Payment p1 = new Payment(SOME_EVENT, SOME_PAYER, SOME_RECIVER, 9.65, d);
        Payment p2 = new Payment(SOME_EVENT, SOME_PAYER, SOME_RECIVER, 9.65, d);
        Payment p3 = new Payment(SOME_EVENT, SOME_PAYER, SOME_RECIVER, 5.73, d);
        assert p1.hashCode() == p2.hashCode();
        assert p1.hashCode() != p3.hashCode();
    }

    /**
     * test to string
     */
    @Test
    public void testToString() {
        Date d = new Date(2024-11-10);
        Payment p1 = new Payment(SOME_EVENT, SOME_PAYER, SOME_RECIVER, 9.65, d);
        var actual = p1.toString();
        assertTrue(actual.contains(Payment.class.getSimpleName()));
        assertTrue(actual.contains(SOME_EVENT.getName()));
        assertTrue(actual.contains(SOME_PAYER.getName()));
        assertTrue(actual.contains(SOME_RECIVER.getName()));
        assertTrue(actual.contains("9.65"));
        assertTrue(actual.contains(d.toString()));
    }
}
