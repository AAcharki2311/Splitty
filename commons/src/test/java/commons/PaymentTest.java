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
}
