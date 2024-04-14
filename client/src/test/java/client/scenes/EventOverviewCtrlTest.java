package client.scenes;

import client.MyFXML;
import client.MyModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import client.utils.*;
import commons.Event;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EventOverviewCtrlTest extends ApplicationTest {
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

    @BeforeAll
    static void setAllUp(){
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("glass.platform", "Monocle");
        System.setProperty("monocle.platform", "Headless");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "true");
    }

//    @Override
//    public void start(Stage stage) throws Exception {
//        Injector injector = Guice.createInjector(new MyModule());
//        MyFXML fxml = new MyFXML(injector);
//
//        Pair<EventOverviewCtrl, Parent> screen = fxml.load(EventOverviewCtrl.class,
//                "client", "scenes", "EventOverview.fxml");
//
//        this.eventOverviewCtrl = screen.getKey();
//        MockitoAnnotations.openMocks(this).close();
//
//        Scene scene = new Scene(screen.getValue());
//        stage.setScene(scene);
//        stage.show();
//    }

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