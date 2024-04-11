package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
import commons.*;
//import javafx.application.Platform;
//import javafx.scene.control.TableView;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class EditExpenseCtrlTest extends ApplicationTest {
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
    private EditExpenseCtrl editExpenseCtrl;

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

        Pair<EditExpenseCtrl, Parent> editExpenseScreen = fxml.load(EditExpenseCtrl.class,
                "client", "scenes", "EditExpenseScreen.fxml");

        this.editExpenseCtrl = editExpenseScreen.getKey();
        MockitoAnnotations.openMocks(this).close();

        Scene scene = new Scene(editExpenseScreen.getValue());
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        editExpenseCtrl = new EditExpenseCtrl(server, mc, jsonReader, partServer, expServer);
    }

    @Test
    void validate() {
        String title = "Expense1";
        double money = 10.0;
        Date date = new Date(2021-01-01);
        assertFalse(editExpenseCtrl.validate(title, money, date, null, null));
    }

    @Test
    public void testGetChangedExpenses() {
        ArrayList<Expense> expectedExpenses = new ArrayList<>();
        expectedExpenses.add(new Expense(null, null, 100.0, new Date(2021-01-01), "Entertainment", "none", "EUR"));
        expectedExpenses.add(new Expense(null, null, 920.0, new Date(2021-01-01), "Entertainment", "none", "EUR"));
        editExpenseCtrl.setChangedExpenses(expectedExpenses);

        ArrayList<Expense> actualExpenses = editExpenseCtrl.getChangedExpenses();

        assertEquals(actualExpenses, expectedExpenses);
    }

    @Test
    public void testSetChangedExpenses() {
        ArrayList<Expense> expectedExpenses = new ArrayList<>();
        expectedExpenses.add(new Expense(null, null, 100.0, new Date(2021 - 01 - 01), "Entertainment", "none", "EUR"));
        expectedExpenses.add(new Expense(null, null, 920.0, new Date(2021 - 01 - 01), "Entertainment", "none", "EUR"));
        editExpenseCtrl.setChangedExpenses(expectedExpenses);

        assertEquals(expectedExpenses, editExpenseCtrl.getChangedExpenses());
    }

    @Test
    void setupTable(){
//        ArrayList<Expense> expenses = new ArrayList<>();
//        expenses.add(new Expense(null, null, 100.0, new Date(2021-01-01), "Entertainment", "none", "EUR"));
//        expenses.add(new Expense(null, null, 920.0, new Date(2021-01-01), "Entertainment", "none", "EUR"));
//        editExpenseCtrl.setChangedExpenses(expenses);
//        Platform.startup(() -> {
//            TableView<Expense> tb = editExpenseCtrl.setupTable();
//            assertNotNull(tb);
//        });
    }

    @Test
    public void testElsemethod() {
        HashMap<String, String> hashmapTest = new HashMap<>();
        hashmapTest.put("key93", "Amount cannot be negative");

        var res = editExpenseCtrl.elsemethod(-101.50, hashmapTest);
        assertEquals("Amount cannot be negative", res);
    }

    @Test
    public void testElsemethodMoney() {
        HashMap<String, String> hashmapTest = new HashMap<>();
        hashmapTest.put("key84", "Please fill in all fields correctly");

        var res = editExpenseCtrl.elsemethod(100, hashmapTest);
        assertEquals("Please fill in all fields correctly", res);
    }

    @AfterEach
    void tearDown() {
    }
}