package client.scenes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import client.utils.*;
import commons.Event;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EventOverviewCtrlTest {
    @Mock
    private EventServerUtils server;
    @Mock
    private MainCtrl mc;
    @Mock
    private ReadJSON jsonReader;
    @Mock
    private ParticipantsServerUtil partServer;
    @Mock
    private ExpensesServerUtils expServer;
    @Mock
    private LanguageSwitch languageSwitch;
    @InjectMocks
    private EventOverviewCtrl eventOverviewCtrl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        eventOverviewCtrl = new EventOverviewCtrl(server, mc, jsonReader, partServer, expServer, languageSwitch);
    }

    @Test
    void checkDuplicateName() {
        Event event1 = new Event("Event1");
        Event event2 = new Event("Event2");
        server.addEvent(event1);
        server.addEvent(event2);

        when(server.getAllEvents()).thenReturn(Arrays.asList(
                new Event("Event1"),
                new Event("Event2")
        ));

        // Test cases
        assertTrue(eventOverviewCtrl.checkDuplicateName("Event1"));
        assertTrue(eventOverviewCtrl.checkDuplicateName("Event2"));
        assertFalse(eventOverviewCtrl.checkDuplicateName("Event3"));
    }

    @Test
    void getCurrentEventID() {
        var res = eventOverviewCtrl.getCurrentEventID();
        assertTrue(res != -1);
    }

    @Test
    void getEventServerUtils() {
        var res = eventOverviewCtrl.getEventServerUtils();
        assertEquals(server, res);
    }

    @AfterEach
    void tearDown() {
    }
}