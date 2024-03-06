package client.utils;

import commons.Event;
import org.junit.jupiter.api.Test;

public class EventServerUtilsTest {
    @Test
    public void printEvents() {
        System.out.println(new EventServerUtils().getAllEvents());
    }

    @Test
    public void addEvent() {
        EventServerUtils ESU = new EventServerUtils();
        ESU.addEvent(new Event("Test Event"));
        System.out.println(ESU.getAllEvents());
    }
}
