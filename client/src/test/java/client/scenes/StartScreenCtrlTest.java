package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
import commons.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;

import static org.junit.jupiter.api.Assertions.*;

class StartScreenCtrlTest extends ApplicationTest {
    @Mock
    private EventServerUtils server;
    @Mock
    private MainCtrl mc;
    @Mock
    private ReadJSON jsonReader;
    @Mock
    private ParticipantsServerUtil partServer;
    @Mock
    private WriteEventNames writeEventNames;
    @Mock
    private LanguageSwitch languageSwitch;
    @InjectMocks
    private StartScreenCtrl startScreenCtrl;
    private static final String TEST_CONFIG_PATH = "src/test/java/client/resources/readFile.txt";

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

        Pair<StartScreenCtrl, Parent> screen = fxml.load(StartScreenCtrl.class,
                "client", "scenes", "StartScreen.fxml");

        this.startScreenCtrl = screen.getKey();
        ReadJSON jsonReader2 = new ReadJSON();
        HashMap<String, String> h = jsonReader2.readJsonToMap("src/main/resources/languageJSONS/languageEN.json");
        this.startScreenCtrl.setHashmap(h);
        MockitoAnnotations.openMocks(this).close();

        Scene scene = new Scene(screen.getValue());
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        startScreenCtrl = new StartScreenCtrl(server, partServer, mc, jsonReader, writeEventNames, languageSwitch);
    }

    @Test
    void checkNumber() {
        assertTrue(startScreenCtrl.checkNumber("123"));
        assertFalse(startScreenCtrl.checkNumber("-50"));
        assertFalse(startScreenCtrl.checkNumber(""));
        assertFalse(startScreenCtrl.checkNumber("abc"));
    }

    @Test
    void readFileExceptionTest() throws IOException {
        assertThrows(IOException.class, () -> {
            String text = startScreenCtrl.readFile("testfile.txt");
        });
    }

    @Test
    void readFile() throws IOException {
        String actualtext = "serverurl=https\\://www.google.com/\n";
        String text = startScreenCtrl.readFile(TEST_CONFIG_PATH);
        assertNotNull(text);
        assertFalse(text.isEmpty());
        assertEquals(actualtext, text);
    }

    @Test
    void testSetAndGetParticipant() {
        Participant participant = new Participant(null, "name", "email@mail.com", "nl12ab1234", "123456");
        startScreenCtrl.setUserParticipant(participant);
        mc.setParticipant(participant);
        assertEquals(participant, startScreenCtrl.getuserParticipant());
    }

    @Test
    void testSetAndGetHashMap() {
        HashMap<String, String> hash = new HashMap<>();
        hash.put("key", "value");
        startScreenCtrl.setHashmap(hash);
        assertEquals(hash, startScreenCtrl.getHashmap());
    }

    @Test
    void testSetAndGetTimer() {
        Timer t = new Timer();
        startScreenCtrl.setTimer(t);
        assertEquals(t, startScreenCtrl.getTimer());
    }

    @Test
    void joinInvalidCodeTest() {
        Mockito.when(server.getEventByID(666666)).thenReturn(null);

        TextField field = lookup("#eventJoin").queryAs(TextField.class);
        Text message = lookup("#message").queryAs(Text.class);

        clickOn(field);
        write("666666");
        clickOn(lookup("#joinBTN").queryButton());

        assertEquals("This Id does not exist", message.getText());
    }

    @Test
    void joinInvalidCodeTestNotNumber() {
        TextField field = lookup("#eventJoin").queryAs(TextField.class);
        Text message = lookup("#message").queryAs(Text.class);

        clickOn(field);
        write("sdds");
        clickOn(lookup("#joinBTN").queryButton());

        assertEquals("This Id is incorrect", message.getText());
    }

    @Test
    void createInvalidCodeTest() {
        TextField field = lookup("#eventName").queryAs(TextField.class);
        Text message = lookup("#message").queryAs(Text.class);

        clickOn(field);
        write(" ");
        clickOn(lookup("#createBTN").queryButton());

        assertEquals("Name can not be empty", message.getText());
    }

    @AfterEach
    void tearDown() {
    }

//    @Test
//    void langueageswitch() {
//        Text welcometext = lookup("#welcometext").queryAs(Text.class);
//        startScreenCtrl.langueageswitch("EN");
//        assertEquals("Welcome to the Expense Manager", welcometext.getText());
//    }
}