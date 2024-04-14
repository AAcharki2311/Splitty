package client.scenes;

import client.utils.*;
import commons.Event;
import commons.Expense;
import commons.Participant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class MainCtrlTest extends ApplicationTest {
    @Mock
    private ReadJSON jsonReader;
    @Mock
    private EventServerUtils eventServerUtilsMock;
    @Mock
    private ParticipantsServerUtil participantsServerUtilMock;
    @Mock
    private ExpensesServerUtils expensesServerUtilsMock;
    @Mock
    private PaymentsServerUtils paymentsServerUtilMock;
    @Mock
    private WriteEventNames writeEventNamesMock;
    @Mock
    private LanguageSwitch languageSwitchMock;
    private MainCtrl mainCtrl;
    private static final String TEST_CONFIG_PATH = "src/test/java/client/resources/testConfigEn.JSON";
    private static final String TEST_CONFIG_PATH_NL = "src/test/java/client/resources/testConfigNl.JSON";
    private static final String TEST_CONFIG_PATH_FR = "src/test/java/client/resources/testConfigFr.JSON";
    private final ReadJSON jsonReader2 = new ReadJSON();
    private final HashMap<String, String> h = jsonReader2.readJsonToMap("src/main/resources/languageJSONS/languageEN.json");

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

    @BeforeEach
    void setUp() {
        mainCtrl = new MainCtrl();
        this.mainCtrl.setHashmap(h);
//            mainCtrl.initialize(null, null, null, null, null, null, null, null, null, null, null);
    }

    @Test
    void testInitialize() {
        assertNotNull(mainCtrl);
    }

    private void writeTestFile(String language, String configFilePath) throws IOException {
        Properties properties = new Properties();
        properties.setProperty("language", language);

        FileOutputStream outputStream = new FileOutputStream(configFilePath);
        properties.store(outputStream, null);
        outputStream.close();
    }

    @Test
    public void testSetLanguage() throws IOException {
        writeTestFile("english", TEST_CONFIG_PATH);
        String language = mainCtrl.setLanguage(TEST_CONFIG_PATH);
        assertNotNull(language);
        assertTrue(language.equals("NL") || language.equals("FR") || language.equals("EN"));

        writeTestFile("french", TEST_CONFIG_PATH_FR);
        language = mainCtrl.setLanguage(TEST_CONFIG_PATH_FR);
        assertNotNull(language);
        assertTrue(language.equals("NL") || language.equals("FR") || language.equals("EN"));

        writeTestFile("dutch", TEST_CONFIG_PATH_NL);
        language = mainCtrl.setLanguage(TEST_CONFIG_PATH_NL);
        assertNotNull(language);
        assertTrue(language.equals("NL") || language.equals("FR") || language.equals("EN"));
    }

    @Test
    void getAndSetEventOverview() {
        EventOverviewCtrl eventOverviewCtrl = new EventOverviewCtrl(null, null, null, null, null, null);
        mainCtrl.setEventOCtrl(eventOverviewCtrl);
        assertEquals(eventOverviewCtrl, mainCtrl.getEventOCtrl());
    }

    @Test
    void getAndSetTemplist(){
        ArrayList<Expense> expectedExpenses = new ArrayList<>();
        expectedExpenses.add(new Expense(null, null, 100.0, new Date(2021-01-01), "Entertainment", "none", "EUR"));
        expectedExpenses.add(new Expense(null, null, 920.0, new Date(2021-01-01), "Entertainment", "none", "EUR"));
        EditExpenseCtrl editExpenseCtrl = new EditExpenseCtrl(null, null, null, null, null);
        editExpenseCtrl.setChangedExpenses(expectedExpenses);
        mainCtrl.setEditExpenseCtrl(editExpenseCtrl);
        mainCtrl.setTempList(expectedExpenses);
        assertEquals(expectedExpenses, mainCtrl.getTempList());
    }

    @Test
    void getAndSetParticipant(){
        Event event1 = new Event("Event1");
        Participant participant1 = new Participant(event1, "Participant1", "email1", "iban1", "bic1");
        ParticipantCtrl participantCtrl = new ParticipantCtrl(null, null, null, null);
        assertNotEquals(participant1, participantCtrl.getUserParticipant());

        mainCtrl.setParticipantCtrl(participantCtrl);
        mainCtrl.setParticipant(participant1);
        assertEquals(participant1, participantCtrl.getUserParticipant());
    }

    @Test
    void isServerReachable() {
        String url = "https://www.google.com";
        assertTrue(mainCtrl.isServerReachable(url));
    }
    @Test
    void isServerReachableException() {
        String url = "NotReachableSite.com";
        assertFalse(mainCtrl.isServerReachable(url));
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_CONFIG_PATH);
        File fileFR = new File(TEST_CONFIG_PATH_FR);
        File fileNL = new File(TEST_CONFIG_PATH_NL);

        if(file.exists() || fileFR.exists() || fileNL.exists()){
            file.delete();
            fileFR.delete();
            fileNL.delete();
        }
    }

    @Test
    void setupTable() {
        ReadJSON jsonReader2 = new ReadJSON();
        HashMap<String, String> h = jsonReader2.readJsonToMap("src/main/resources/languageJSONS/languageEN.json");
        var res = mainCtrl.setupTable(h);
        assertNotNull(res);
    }

    @Test
    void testSetAndGetHashMap() {
        HashMap<String, String> hash = new HashMap<>();
        hash.put("key", "value");
        mainCtrl.setHashmap(hash);
        assertEquals(hash, mainCtrl.getHashmap());
    }
}