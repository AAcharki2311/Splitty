package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantTest {

    @Test
    void getP_id() {
        Participant a = new Participant(1, null, "Max", "Max@gmail.com");
        assertEquals(a.getP_id(), 1);
    }

    @Test
    void getName(){
        Participant a = new Participant(1, null, "Max", "Max@gmail.com");
        assertTrue(a.getName().equals("Max"));
    }

    @Test
    void setName(){
        Participant a = new Participant(1, null, "Ma", "Max@gmail.com");
        a.setName(("Max"));
        assertTrue(a.getName().equals("Max"));
    }

//    @Test
//    void getEvent(){
//        Participant a = new Participant(1, null, "Max", "Max@gmail.com");
//        assertTrue(a.getEvent().equals(null));
//    }
    @Test
    void getEmail(){
        Participant a = new Participant(1, null, "Max", "Max@gmail.com");
        assertTrue(a.getEmail().equals("Max@gmail.com"));
    }

    @Test
    void setEmail(){
        Participant a = new Participant(1, null, "Ma", "Ma@gmail.com");
        a.setEmail(("Max@gmail.com"));
        assertTrue(a.getEmail().equals("Max@gmail.com"));
    }

    @Test
    void testEquals1() {
        Participant a = new Participant(1, null, "Max", "Max@gmail.com");
        assertTrue(a.equals(a));
    }

    @Test
    void testEquals0() {
        Participant a = new Participant(1, null, "Max", "Max@gmail.com");
        assertFalse(a.equals(null));
    }

    @Test
    void testEquals2() {
        Participant a = new Participant(1, null, "Max", "Max@gmail.com");
        Participant b = new Participant(1, null, "Max", "Max@gmail.com");
        assertTrue(a.equals(b));
    }

    @Test
    void testEquals3() {
        Participant a = new Participant(1, null, "Max", "Max@gmail.com");
        Participant b = new Participant(2, null, "Max", "Max@gmail.com");
        assertFalse(a.equals(b));
    }
}