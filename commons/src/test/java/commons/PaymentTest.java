package commons;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    @Test
    public void checkConstructor () {
        Date d = new Date();
        Payment  p = new Payment(1, 2, 3, 4, 5, d);
        assertEquals(1, p.getP1_id());
        assertEquals(2, p.getP2_id());
        assertEquals(3, p.getEv_id());
        assertEquals(4, p.getP_id());
        assertEquals(5, p.getAmount());
        assertEquals(d, p.getDate());
    }

    @Test
    public void testGettersAndSetters() {
        Date d = new Date();
        Date d1 = new Date();
        Payment p = new Payment(1, 2, 3, 4, 5, d);
        p.setP1_id(6);
        assertEquals(6, p.getP1_id());
        p.setP2_id(7);
        assertEquals(7, p.getP2_id());
        p.setEv_id(8);
        assertEquals(8, p.getEv_id());
        p.setAmount(9);
        assertEquals(9, p.getAmount());
        p.setDate(d1);
        assertEquals(d1, p.getDate());

    }

    @Test
    public void testEquals() {
        Date d = new Date();
        Payment p1 = new Payment(1, 2, 3, 4, 5, d);
        Payment p2 = new Payment(1, 2, 3, 4, 5, d);
        Payment p3 = new Payment(1, 2, 3, 100, 5, d);
        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
    }

    @Test
    public void testHashCode() {
        Date d = new Date();
        Payment p1 = new Payment(1, 2, 3, 4, 5, d);
        Payment p2 = new Payment(1, 2, 3, 4, 5, d);
        Payment p3 = new Payment(1, 2, 3, 100, 5, d);
        assert p1.hashCode() == p2.hashCode();
        assert p1.hashCode() != p3.hashCode();
    }

    @Test
    public void testToString() {
        Date d = new Date();
        Payment p1 = new Payment(1, 2, 3, 4, 5, d);
        String expected = "Payments{p1_id=1, p2_id=2, ev_id=3, p_id=4, amount=5, date=" + p1.getDate() + "}";
        assertEquals(p1.toString(), expected);
    }
}
