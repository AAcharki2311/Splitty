package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
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

import static org.junit.jupiter.api.Assertions.*;

class EditParticipantCtrlTest extends ApplicationTest {
    @Mock
    private EventServerUtils server;
    @Mock
    private MainCtrl mc;
    @Mock
    private ReadJSON jsonReader;
    @Mock
    private ParticipantsServerUtil partServer;
    @InjectMocks
    private EditParticipantCtrl editParticipantCtrl;

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

        Pair<EditParticipantCtrl, Parent> screen = fxml.load(EditParticipantCtrl.class,
                "client", "scenes", "EditParticipantScreen.fxml");

        this.editParticipantCtrl = screen.getKey();
        MockitoAnnotations.openMocks(this).close();

        Scene scene = new Scene(screen.getValue());
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        editParticipantCtrl = new EditParticipantCtrl(server, mc, jsonReader, partServer);
    }

    @Test
    void validate() {
        String name = "John Doe";
        String email = "John@email.com";
        String iban = "NL91ABNA0417164300";
        String bic = "ABNANL2A";
        assertTrue(editParticipantCtrl.validate(name, email, iban, bic));
        assertFalse(editParticipantCtrl.validate(name, "wrong.email", iban, bic));

        assertFalse(editParticipantCtrl.validate("", email, iban, bic));
        assertFalse(editParticipantCtrl.validate(name, "", iban, bic));
        assertFalse(editParticipantCtrl.validate(name, email, "", bic));
        assertFalse(editParticipantCtrl.validate(name, email, iban, ""));
        assertFalse(editParticipantCtrl.validate(name, "wrong@email", iban, bic));
        assertFalse(editParticipantCtrl.validate(name, email, "06565", bic));
    }

    @AfterEach
    void tearDown() {
    }
}