package client.scenes;

import client.utils.*;
import commons.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AddExpenseCtrlTest {
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        addExpenseCtrl = new AddExpenseCtrl(server, mc, jsonReader, partServer, expServer);
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

    @AfterEach
    void tearDown() {
    }
}