package commons;
import java.util.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    private static final Event SOME_EVENT = new Event("Drinkfest");
    private static final Participant SOME_PAYER = new Participant(1205, SOME_EVENT,"A", "B");
    private static final Participant SOME_PAYEE = new Participant(1205, SOME_EVENT,"A", "B");

    @Test
    public void checkConstructor () {
        Date d = new Date();
        Event e = new Event();
        Payment  p = new Payment(SOME_PAYER, SOME_PAYEE, e, 4, 5, d);
        assertEquals(SOME_PAYER, p.getPayer());
        assertEquals(SOME_PAYEE, p.getPayee());
        assertEquals(e, p.getEvent());
        assertEquals(4, p.getP_id());
        assertEquals(5, p.getAmount());
        assertEquals(d, p.getDate());
    }

    @Test
    public void testGettersAndSetters() {
        Date d = new Date();
        Date d1 = new Date();
        Event e = new Event();
        Event e2 = new Event();
        Payment  p = new Payment(SOME_PAYER, SOME_PAYEE, e, 4, 5, d);
        p.setPayer(SOME_PAYEE);
        assertEquals(SOME_PAYEE, p.getPayer());
        p.setPayee(SOME_PAYER);
        assertEquals(SOME_PAYER, p.getPayee());
        p.setEv_id(e2);
        assertEquals(e2, p.getEvent());
        p.setAmount(9);
        assertEquals(9, p.getAmount());
        p.setDate(d1);
        assertEquals(d1, p.getDate());

    }

    @Test
    public void testEquals() {
        Date d = new Date();
        Event e = new Event();
        Payment p1 = new Payment(SOME_PAYER, SOME_PAYEE, e, 4, 5, d);
        Payment p2 = new Payment(SOME_PAYER, SOME_PAYEE, e, 4, 5, d);
        Payment p3 = new Payment(SOME_PAYER, SOME_PAYEE, e, 100, 5, d);
        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
    }

    @Test
    public void testHashCode() {
        Date d = new Date();
        Event e = new Event();
        Payment p1 = new Payment(SOME_PAYER, SOME_PAYEE, e, 4, 5, d);
        Payment p2 = new Payment(SOME_PAYER, SOME_PAYEE, e, 4, 5, d);
        Payment p3 = new Payment(SOME_PAYER, SOME_PAYEE, e, 100, 5, d);
        assert p1.hashCode() == p2.hashCode();
        assert p1.hashCode() != p3.hashCode();
    }

//    @Test
//    public void testToString() {
//        Date d = new Date();
//        Event e = new Event();
//        Payment p1 = new Payment(SOME_PAYER, SOME_PAYEE, e, 4, 5, d);
//        String expected = "Payments{p1_id=1, p2_id=2, ev_id=3, p_id=4, amount=5, date=" + p1.getDate() + "}";
//        assertEquals(p1.toString(), expected);
//    }
}
