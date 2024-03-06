package client.utils;

import commons.Event;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class EventServerUtilsTest {
    private static final EventServerUtils ESU = new EventServerUtils();
    @Test
    public void printEvents() {
        System.out.println(ESU.getAllEvents());
    }

    @Test
    public void addEvent() {
        Event e = ESU.addEvent(new Event("Test Event"));
        System.out.println(e);
        ESU.deleteEventByID(e.id);
    }

    @Test
    public void getEventByID() {
        System.out.println(ESU.getEventByID(0));
    }

    @Test
    public void updateEventByID() {
        Event e = new Event("mainEventTest"+(new Random().nextInt(0, 99)));
        e = ESU.updateEventByID(0, e);
        System.out.println(e);
    }

    @Test
    public void deleteEventByID() {
        System.out.println(ESU.deleteEventByID(1));
    }

    @Test
    public void CRUDEventByID() {
        Event e = new Event("deleteEventByIDTest");
        e = ESU.addEvent(e);
        System.out.println("Event created:");
        System.out.println(e);
        System.out.println("Event deleted:");
        System.out.println(ESU.deleteEventByID(e.id));
    }
}
