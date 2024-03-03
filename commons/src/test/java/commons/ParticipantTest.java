package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantTest {

    @Test
    void getName(){
        Event e = new Event("TestEvent");
        Participant a = new Participant(e, "Max", "Max@gmail.com", "NL99ABNA0123456789", "RABONL2U");
        assertEquals("Max", a.getName());
    }

    @Test
    void setName(){
        Event e = new Event("TestEvent");
        Participant a = new Participant(e, "Ma", "Max@gmail.com", "NL99ABNA0123456789", "RABONL2U");
        a.setName(("Max"));
        assertEquals("Max", a.getName());
    }

    @Test
    void getEvent(){
        Event e = new Event("TestEvent");
        Participant a = new Participant(e, "Max", "Max@gmail.com", "NL99ABNA0123456789", "RABONL2U");
        assertEquals(a.getEvent(), e);
    }
    @Test
    void getEmail(){
        Event e = new Event("TestEvent");
        Participant a = new Participant(e, "Max", "Max@gmail.com", "NL99ABNA0123456789", "RABONL2U");
        assertEquals("Max@gmail.com", a.getEmail());
    }

    @Test
    void setEmail(){
        Event e = new Event("TestEvent");
        Participant a = new Participant(e, "Max", "Ma@gmail.com", "NL99ABNA0123456789", "RABONL2U");
        a.setEmail(("Max@gmail.com"));
        assertEquals("Max@gmail.com", a.getEmail());
    }

    @Test
    void testEquals1() {
        Event e = new Event("TestEvent");
        Participant a = new Participant(e, "Max", "Max@gmail.com", "NL99ABNA0123456789", "RABONL2U");
        assertTrue(a.equals(a));
    }

    @Test
    void testEquals0() {
        Event e = new Event("TestEvent");
        Participant a = new Participant(e, "Max", "Max@gmail.com", "NL99ABNA0123456789", "RABONL2U");
        assertNotEquals(null, a);
    }

    @Test
    void testEquals2() {
        Event ea = new Event("TestEvent");
        Participant a = new Participant(ea, "Max", "Max@gmail.com", "NL99ABNA0123456789", "RABONL2U");
        Participant b = new Participant(ea, "Max", "Max@gmail.com", "NL99ABNA0123456789", "RABONL2U");
        assertTrue(a.equals(b));
    }

    @Test
    void testEquals3() {
        Event ea = new Event("TestEvent");
        Participant a = new Participant(ea, "Max", "Max@gmail.com", "NL99ABNA0123456789", "RABONL2U");
        Event eb = new Event("TestEventDIFFERENT");
        Participant b = new Participant(eb, "Max", "Max@gmail.com", "NL99ABNA0123456789", "RABONL2U");
        assertFalse(a.equals(b));
    }


    @Test
    void getIban() {
        Event ea = new Event("TestEvent");
        Participant a = new Participant(ea, "Max", "Max@gmail.com", "NL99ABNA0123456789", "RABONL2U");
        assertEquals(a.getIban(), "NL99ABNA0123456789");
    }

    @Test
    void setIban() {
        Event ea = new Event("TestEvent");
        Participant a = new Participant(ea, "Max", "Max@gmail.com", "NLBLABLA", "RABONL2U");
        a.setIban("NL99ABNA0123456789");
        assertTrue(a.getIban().equals("NL99ABNA0123456789"));
    }

    @Test
    void getBic() {
        Event ea = new Event("TestEvent");
        Participant a = new Participant(ea, "Max", "Max@gmail.com", "NL99ABNA0123456789", "RABONL2U");
        assertEquals(a.getBic(), "RABONL2U");
    }

    @Test
    void setBic() {
        Event ea = new Event("TestEvent");
        Participant a = new Participant(ea, "Max", "Max@gmail.com", "NL99ABNA0123456789", "blabla");
        a.setBic("RABONL2U");
        assertTrue(a.getBic().equals("RABONL2U"));
    }

    @Test
    public void checkToString() {
        Event e = new Event("TestEvent");
        Participant a = new Participant(e, "Max", "Max@gmail.com", "NL99ABNA0123456789", "RABONL2U");
        var actual = a.toString();
        assertTrue(actual.contains(Participant.class.getSimpleName()));
        assertTrue(actual.contains(a.getEvent().toString()));
        assertTrue(actual.contains("Max"));
        assertTrue(actual.contains("Max@gmail.com"));
        assertTrue(actual.contains("NL99ABNA0123456789"));
        assertTrue(actual.contains("RABONL2U"));

    }
}