package commons;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    /**
     * Test contrsuctor
     */
    @Test
    public void checkConstructer() {
        Event e = new Event();
        assertNotNull(e);
    }

    /**
     * Test contrsuctor
     */
    @Test
    public void checkConstructer2() {
        Event e = new Event(101, "TestEvent", new Date(), new Date());
        assertNotNull(e);
    }

    /**
     * Test namegetter
     */
    @Test
    public void checkNameGetter() {
        Event e = new Event("TestEvent");
        assertEquals("TestEvent", e.getName());
    }

    /**
     * Test IDgetter
     */
    @Test
    public void checkIDGetter() {
        Event e = new Event("TestEvent");
        assertNotNull(e.getId());
    }

    /**
     * test creationdate getter
     */
    @Test
    public void checkCreationDateGetter() {
        Event e = new Event("TestEvent");
        assertNotNull(e.getCreationDate());
    }

    /**
     * test check date
     */
    @Test
    public void checkActivityDate() {
        Event e = new Event("TestEvent");
        Date d = new Date();
        e.setLastActDate(d);
        assertEquals(d, e.getLastActDate());
    }

    /**
     * test checkk name setter
     */
    @Test
    public void checkNameSetter() {
        Event e = new Event("TestEvent");
        e.setName("TestEvent2");
        assertEquals("TestEvent2", e.getName());
    }

    /**
     * test equals method
     */
    @Test
    public void checkEqualsNotSame() {
        Event e1 = new Event("TestEvent1");
        Event e2 = new Event("TestEvent2");
        assertFalse(e1.equals(e2));
    }

    /**
     * Test equals 2
     */
    @Test
    public void checkEqualsSelf() {
        Event e = new Event("TestEvent");
        assertTrue(e.equals(e));
    }

    /**
     * test equals with null
     */
    @Test
    public void checkEqualsNull() {
        Event e = new Event("TestEvent");
        assertFalse(e.equals(null));
    }

    /**
     * test for hash method
     */
    @Test
    public void chechHash(){
        Event e1 = new Event("TestEvent1");
        Event e2 = new Event("TestEvent2");
        assertEquals(e1.hashCode(), e1.hashCode());
        assertNotEquals(e1.hashCode(), e2.hashCode());
    }

    /**
     * test to string
     */
    @Test
    public void checkToString() {
        Event e = new Event("TestEvent");
        var actual = e.toString();
        assertTrue(actual.contains(Event.class.getSimpleName()));
        assertTrue(actual.contains("TestEvent"));
    }

    @Test
    void setId() {
        Event e = new Event("TestEvent");
        e.setId(101);
        assertEquals(101, e.getId());
    }
}
