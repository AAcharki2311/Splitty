package client.scenes;

import client.utils.*;
import commons.Participant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;

import static org.junit.jupiter.api.Assertions.*;

class StartScreenCtrlTest {
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

    @AfterEach
    void tearDown() {
    }
}