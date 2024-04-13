package client.scenes;

import client.utils.*;
import commons.*;
//import javafx.application.Platform;
//import javafx.scene.control.TableView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class EditExpenseCtrlTest {
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