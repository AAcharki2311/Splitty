package commons;

import org.junit.jupiter.api.Test;

import java.util.Date;

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
    public void checkCreationDateGetter() {
        Event e = new Event("TestEvent");
        assertNotNull(e.getCreationDate());
    }

    @Test
    public void checkActivityDate() {
        Event e = new Event("TestEvent");
        Date d = new Date();
        e.setLastActDate(d);
        assertEquals(d, e.getLastActDate());
    }

    @Test
    public void checkNameSetter() {
        Event e = new Event("TestEvent");
        e.setName("TestEvent2");
        assertEquals("TestEvent2", e.getName());
    }

    @Test
    public void checkEqualsNotSame() {
        Event e1 = new Event("TestEvent1");
        Event e2 = new Event("TestEvent2");
        assertFalse(e1.equals(e2));
    }

    @Test
    public void checkEqualsSelf() {
        Event e = new Event("TestEvent");
        assertTrue(e.equals(e));
    }

    @Test
    public void checkEqualsNull() {
        Event e = new Event("TestEvent");
        assertFalse(e.equals(null));
    }

    @Test
    public void chechHash(){
        Event e1 = new Event("TestEvent1");
        Event e2 = new Event("TestEvent2");
        assertEquals(e1.hashCode(), e1.hashCode());
        assertNotEquals(e1.hashCode(), e2.hashCode());
    }
}
