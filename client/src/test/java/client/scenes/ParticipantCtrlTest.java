package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
import commons.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ParticipantCtrlTest extends ApplicationTest {
    @Mock
    private EventServerUtils server;
    @Mock
    private MainCtrl mc;
    @Mock
    private ReadJSON jsonReader;
    @Mock
    private ParticipantsServerUtil partServer;
    @InjectMocks
    private ParticipantCtrl participantCtrl;

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

    @Override
    public void start(Stage stage) throws Exception {
        Injector injector = Guice.createInjector(new MyModule());
        MyFXML fxml = new MyFXML(injector);

        Pair<ParticipantCtrl, Parent> screen = fxml.load(ParticipantCtrl.class,
                "client", "scenes", "AddParticipantScreen.fxml");

        this.participantCtrl = screen.getKey();
        MockitoAnnotations.openMocks(this).close();

        Scene scene = new Scene(screen.getValue());
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        participantCtrl = new ParticipantCtrl(server, mc, jsonReader, partServer);
    }

    @Test
    void checkDuplicate() {
        Event event1 = new Event("Event1");
        Participant participant1 = new Participant(event1, "Participant1", "email1", "iban1", "bic1");
        partServer.addParticipant(participant1);

        when(partServer.getAllParticipants()).thenReturn(Arrays.asList(
                new Participant(event1, "Participant1", "email1@email.com", "iban1", "bic1")
        ));

        assertTrue(participantCtrl.checkDuplicate("Participant1", "email1@email.com"));
    }

    @Test
    void validate() {
        String name = "John Doe";
        String email = "John@email.com";
        String iban = "NL91ABNA0417164300";
        String bic = "ABNANL2A";
        assertTrue(participantCtrl.validate(name, email, iban, bic));

        assertFalse(participantCtrl.validate(name, "wrong.email", iban, bic));

        assertFalse(participantCtrl.validate("", email, iban, bic));
        assertFalse(participantCtrl.validate(name, "", iban, bic));
        assertFalse(participantCtrl.validate(name, email, "", bic));
        assertFalse(participantCtrl.validate(name, email, iban, ""));
        assertFalse(participantCtrl.validate(name, "wrong@email", iban, bic));
        assertFalse(participantCtrl.validate(name, email, "06565", bic));
    }

    @Test
    void setUserParticipant() {
        Event event1 = new Event("Event1");
        Participant participant1 = new Participant(event1, "Participant1", "email1", "iban1", "bic1");
        participantCtrl.setUserParticipant(participant1);
        assertEquals(participant1, participantCtrl.getUserParticipant());
    }

    @Test
    void getUserParticipant() {
        Event event1 = new Event("Event1");
        Participant expectedParticipant = new Participant(event1, "Participant1", "email1", "iban1", "bic1");
        participantCtrl.setUserParticipant(expectedParticipant);
        Participant actualParticipant = participantCtrl.getUserParticipant();
        assertEquals(expectedParticipant, actualParticipant);
    }

    @Test
    public void testElsemethodDuplicate() {
        HashMap<String, String> hashmapTest = new HashMap<>();
        hashmapTest.put("key75", "Name or email already exists");

        boolean duplicate = true;
        String res = participantCtrl.elsemethod(duplicate, null, null, null, null, hashmapTest);
        assertEquals("Name or email already exists", res);
    }

    @Test
    public void testElsemethodName() {
        HashMap<String, String> hashmapTest = new HashMap<>();
        hashmapTest.put("key76", "Name is not correct");

        var res = participantCtrl.elsemethod(false, "", null, null, null, hashmapTest);
        assertEquals("Name is not correct", res);
    }

    @Test
    public void testElsemethodEmail() {
        HashMap<String, String> hashmapTest = new HashMap<>();
        hashmapTest.put("key77", "Email is not correct");

        var res = participantCtrl.elsemethod(false, "name", "mail@12", null, null, hashmapTest);
        assertEquals("Email is not correct", res);
        res = participantCtrl.elsemethod(false, "name", "mail.com", null, null, hashmapTest);
        assertEquals("Email is not correct", res);
        res = participantCtrl.elsemethod(false, "name", "mail", null, null, hashmapTest);
        assertEquals("Email is not correct", res);
    }

    @Test
    public void testElsemethodIban() {
        HashMap<String, String> hashmapTest = new HashMap<>();
        hashmapTest.put("key78", "IBAN is not correct");

        String name = "John Doe";
        String email = "John@email.com";
        String iban = "132664848";
        String bic = "ABNANL2A";
        participantCtrl.validate(name, email, iban, bic);

        var res = participantCtrl.elsemethod(false, "name", "email@mail.com", "132664848", null, hashmapTest);
        assertEquals("IBAN is not correct", res);
    }

    @Test
    public void testElsemethodBic() {
        HashMap<String, String> hashmapTest = new HashMap<>();
        hashmapTest.put("key79", "Bic is not correct");

        String name = "John Doe";
        String email = "John@email.com";
        String iban = "NL12ABCD1356565";
        String bic = "ABNANL2A";
        participantCtrl.validate(name, email, iban, bic);

        var res = participantCtrl.elsemethod(false, "name", "email@mail.com", "132664848", " ", hashmapTest);
        assertEquals("Bic is not correct", res);
    }

    @Test
    public void testElsemethod() {
        HashMap<String, String> hashmapTest = new HashMap<>();
        hashmapTest.put("key80", "Something went wrong");

        String name = "John Doe";
        String email = "John@email.com";
        String iban = "NL12ABCD1356565";
        String bic = "ABNANL2A";
        participantCtrl.validate(name, email, iban, bic);

        var res = participantCtrl.elsemethod(false, "name", "email@mail.com", "132664848", "1236565", hashmapTest);
        assertEquals("Something went wrong", res);
    }

    @Test
    void setEventid() {
        participantCtrl.setEventid(101);
        assertEquals(101, participantCtrl.getEventid());
    }

    @Test
    void getEventid() {
        long eventid = 101;
        participantCtrl.setEventid(eventid);
        assertEquals(eventid, participantCtrl.getEventid());
    }

    @AfterEach
    void tearDown() {
    }
}