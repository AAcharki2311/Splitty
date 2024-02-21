package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    @Test
    public void checkConstructer() {
        Event e = new Event();
        assertNotNull(e);
    }

    @Test
    public void checkNameGetter() {
        Event e = new Event("TestEvent");
        assertEquals("TestEvent", e.getName());
    }

    @Test
    public void checkNameSetter() {
        Event e = new Event("TestEvent");
        e.setName("TestEvent2");
        assertEquals("TestEvent2", e.getName());
    }

    @Test
    public void checkEquals() {
        Event e1 = new Event("TestEvent1");
        Event e2 = new Event("TestEvent2");
        assertFalse(e1.equals(e2));
        assertTrue(e1.equals(e1));
    }

    @Test
    public void chechHash(){
        Event e1 = new Event("TestEvent1");
        Event e2 = new Event("TestEvent2");
        assertEquals(e1.hashCode(), e1.hashCode());
        assertNotEquals(e1.hashCode(), e2.hashCode());
    }
}
