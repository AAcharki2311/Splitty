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
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SettleDebtsCtrlTest extends ApplicationTest {
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
    private SettleDebtsCtrl settleDebtsCtrl;

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

        Pair<SettleDebtsCtrl, Parent> screen = fxml.load(SettleDebtsCtrl.class,
                "client", "scenes", "SettleDebts.fxml");

        this.settleDebtsCtrl = screen.getKey();
        MockitoAnnotations.openMocks(this).close();

        Scene scene = new Scene(screen.getValue());
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        settleDebtsCtrl = new SettleDebtsCtrl(server, mc, jsonReader, partServer, expServer);
    }

    @Test
    void getTotalExpenses() {
        Event event1 = new Event("Event1");
        Participant participant1 = new Participant(event1, "Participant1", "email1", "iban1", "bic1");
        Expense expense1 = new Expense(event1, participant1, 10.0, new Date(2021-01-01), "Expense1", "none", "EUR");
        expServer.addExpense(expense1);

        when(expServer.getExpenses()).thenReturn(Arrays.asList(
                new Expense(event1, participant1, 10.0, new Date(2021-01-01), "Expense1", "none", "EUR")
        ));

        assertEquals(settleDebtsCtrl.getTotalExpenses(), 10.0);
    }

    @Test
    void getPayedValue() {
        Event event1 = new Event("Event1");
        Participant participant1 = new Participant(event1, "Participant1", "email1", "iban1", "bic1");
        Expense expense1 = new Expense(event1, participant1, 10.0, new Date(2021-01-01), "Expense1", "none", "EUR");
        expServer.addExpense(expense1);

        when(expServer.getExpenses()).thenReturn(Arrays.asList(
                new Expense(event1, participant1, 10.0, new Date(2021-01-01), "Expense1", "none", "EUR")
        ));

        assertEquals(settleDebtsCtrl.getPayedValue(participant1), 10.0);
    }

    @Test
    void getOwedValue() {
        Event event1 = new Event("Event1");
        Participant participant1 = new Participant(event1, "Participant1", "email1", "iban1", "bic1");
        Expense expense1 = new Expense(event1, participant1, 10.0, new Date(2021-01-01), "Expense1", "none", "EUR");
        expServer.addExpense(expense1);

        when(expServer.getExpenses()).thenReturn(Arrays.asList(
                new Expense(event1, participant1, 10.0, new Date(2021-01-01), "Expense1", "none", "EUR")
        ));

        assertEquals(0.0, settleDebtsCtrl.getOwedValue(participant1, 1));
    }

    @Test
    void getOwedValueNotZero() {
        Event event1 = new Event("Event1");
        Participant participant1 = new Participant(event1, "Participant1", "email1", "iban1", "bic1");
        Participant participant2 = new Participant(event1, "Participant2", "email2", "iban2", "bic2");
        Expense expense1 = new Expense(event1, participant1, 10.0, new Date(2021-01-01), "Expense1", "none", "EUR");
        Expense expense2 = new Expense(event1, participant2, 20.0, new Date(2021-01-01), "Expense2", "none", "EUR");
        expServer.addExpense(expense1);
        expServer.addExpense(expense2);

        when(expServer.getExpenses()).thenReturn(Arrays.asList(
                new Expense(event1, participant1, 10.0, new Date(2021-01-01), "Expense1", "none", "EUR"),
                new Expense(event1, participant2, 20.0, new Date(2021-01-01), "Expense2", "none", "EUR")
        ));

        assertEquals(5.0, settleDebtsCtrl.getOwedValue(participant1, 2));
    }

    @Test
    void getPercentage() {
        Event event1 = new Event("Event1");
        Participant participant1 = new Participant(event1, "Participant1", "email1", "iban1", "bic1");
        Expense expense1 = new Expense(event1, participant1, 10.0, new Date(2021-01-01), "Expense1", "none", "EUR");
        expServer.addExpense(expense1);

        when(expServer.getExpenses()).thenReturn(Arrays.asList(
                new Expense(event1, participant1, 10.0, new Date(2021-01-01), "Expense1", "none", "EUR")
        ));

        assertEquals(settleDebtsCtrl.getPercentage(1), "10.00%");
    }

    @AfterEach
    void tearDown() {
    }
}