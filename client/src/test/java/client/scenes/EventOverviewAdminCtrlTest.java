package client.scenes;

import commons.Event;
import commons.Expense;
import commons.Participant;
import commons.Payment;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventOverviewAdminCtrlTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void dataWrapperTest() {
        Event e = new Event("name");

        Participant p1 = new Participant(e, "name1", "email", "iban", "bic");
        Participant p2 = new Participant(e, "name2", "email", "iban", "bic");
        List<Participant> pl = new ArrayList<>();
        pl.add(p1);
        pl.add(p2);

        Expense ex = new Expense(e, p1, 28.13, new Date(), "title", "tag", "cur");
        List<Expense> exl = new ArrayList<>();
        exl.add(ex);

        Payment py = new Payment(e, p1, p2, 13.13, new Date());
        List<Payment> pyl = new ArrayList<>();
        pyl.add(py);

        EventOverviewAdminCtrl.DataWrapper dw = new EventOverviewAdminCtrl.DataWrapper(e, pl, pyl, exl);

        assertEquals(e, dw.getEvent());
        assertEquals(pl, dw.getParts());
        assertEquals(exl, dw.getExps());
        assertEquals(pyl, dw.getPays());
    }

    @AfterEach
    void tearDown() {
    }
}