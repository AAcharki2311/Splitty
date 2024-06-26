package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
import commons.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;
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
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AddExpenseCtrlTest extends ApplicationTest {
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
    @InjectMocks
    private AddExpenseCtrl addExpenseCtrl;
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
        MockitoAnnotations.initMocks(this);
        addExpenseCtrl = new AddExpenseCtrl(server, mc, jsonReader, partServer, expServer);
        this.addExpenseCtrl.setHashmap(h);

    }

    @Override
    public void start(Stage stage) throws Exception {
        Injector injector = Guice.createInjector(new MyModule());
        MyFXML fxml = new MyFXML(injector);

        Pair<AddExpenseCtrl, Parent> addExpenseScreen = fxml.load(AddExpenseCtrl.class,
                "client", "scenes", "AddExpenseScreen.fxml");

        this.addExpenseCtrl = addExpenseScreen.getKey();
        this.addExpenseCtrl.setHashmap(h);
        MockitoAnnotations.openMocks(this).close();

        Scene scene = new Scene(addExpenseScreen.getValue());
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void checkDuplicate() {
        Event event1 = new Event("Event1");
        Participant participant1 = new Participant(event1, "Participant1", "email1", "iban1", "bic1");
        Expense expense1 = new Expense(event1, participant1, 10.0, new Date(2021-01-01), "Expense1", "none", "EUR");
        expServer.addExpense(expense1);

        when(expServer.getExpenses()).thenReturn(Arrays.asList(
                new Expense(event1, participant1, 10.0, new Date(2021-01-01), "Expense1", "none", "EUR")
        ));

        assertTrue(addExpenseCtrl.checkDuplicate("Participant1", "Expense1"));
    }

    @Test
    void validate() {
        String title = "Expense1";
        double money = 10.0;
        Date date = new Date(2021-01-01);
        assertFalse(addExpenseCtrl.validate(title, money, date, null, null));
        ComboBox comboBoxCurr = lookup("#comboBoxCurr").queryAs(ComboBox.class);
        clickOn(lookup("#comboBoxCurr").queryAs(ComboBox.class));
        clickOn("EUR");
        RadioButton splitRBtn = lookup("#splitRBtn").queryAs(RadioButton.class);
        clickOn(splitRBtn);
        assertTrue(addExpenseCtrl.validate(title, money, date, comboBoxCurr, splitRBtn));
    }

    @Test
    public void testElsemethodDuplicate() {
        HashMap<String, String> hashmapTest = new HashMap<>();
        hashmapTest.put("key68", "Expense title for this participant already exists");

        boolean duplicate = true;
        String res = addExpenseCtrl.elsemethod(duplicate, null, hashmapTest);
        assertEquals("Expense title for this participant already exists", res);
    }

    @Test
    public void testElsemethodMoney() {
        HashMap<String, String> hashmapTest = new HashMap<>();
        hashmapTest.put("key93", "Amount cannot be negative");

        var res = addExpenseCtrl.elsemethod(false, -101.50, hashmapTest);
        assertEquals("Amount cannot be negative", res);
    }

    @Test
    public void testElsemethod() {
        HashMap<String, String> hashmapTest = new HashMap<>();
        hashmapTest.put("key84", "Please fill in all fields correctly");

        var res = addExpenseCtrl.elsemethod(false, 101.50, hashmapTest);
        assertEquals("Please fill in all fields correctly", res);
    }

    @Test
    void submitError() {
        Text message = lookup("#message").queryAs(Text.class);
        clickOn(lookup("#okBtn").queryButton());
        assertEquals("No participant selected", message.getText());
    }

    @AfterEach
    void tearDown() {
    }

//    @Test
//    void langueageswitch() {
//        HashMap<String, String> hashmapTest = new HashMap<>();
//        hashmapTest = jsonReader.readJsonToMap("src/main/resources/languageJSONS/languageEN.json");
//        addExpenseCtrl.langueageswitch("EN");
//        var field = lookup("#titleOfScreen").queryAs(Text.class);
//        assertEquals(hashmapTest.get("key27"), field.getText());
//    }

    @Test
    void testSetAndGetHashMap() {
        HashMap<String, String> hash = new HashMap<>();
        hash.put("key", "value");
        addExpenseCtrl.setHashmap(hash);
        assertEquals(hash, addExpenseCtrl.getHashmap());
    }



}