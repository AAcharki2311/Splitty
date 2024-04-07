package commons;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {

    private static final Event SOME_EVENT = new Event("TestEvent");

    private static final Participant SOME_PARTICIPANT = new Participant(SOME_EVENT, "Max",
            "Max@gmail.com", "NL99ABNA0123456789", "RABONL2U");


    @Test
    void emptyConstructor(){
        Expense e = new Expense();
        assertNotNull(e);
    }

    @Test
    void getId() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food", "EUR");
        assertNotNull(e.getId());
    }


    @Test
    public void checkConstructor() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food", "EUR");
        assertEquals(SOME_EVENT, e.event);
        assertEquals(SOME_PARTICIPANT, e.creditor);
        assertNotNull(e);
    }

    @Test
    void getEvent() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food", "EUR");
        assertEquals(SOME_EVENT, e.getEvent());
    }

    @Test
    void getCreditor() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food", "EUR");
        assertEquals(SOME_PARTICIPANT, e.getCreditor());
    }

    @Test
    void getAmount() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food", "EUR");
        assertEquals(4.50, e.getAmount());
    }

    @Test
    void getDate() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food", "EUR");
        assertEquals(new Date(2024-11-10), e.getDate());
    }

    @Test
    void getCur() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food", "EUR");
        assertEquals("EUR", e.getCur());
    }

    @Test
    void getTitle() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food", "EUR");
        assertEquals("snack", e.getTitle());
    }

    @Test
    void getTag() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food", "EUR");
        assertEquals("food", e.getTag());
    }

    @Test
    void setCur(){
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food", "EUR");
        e.setCur("YEN");
        assertEquals("YEN", e.getCur());
    }

    @Test
    void setAmount() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food", "EUR");
        e.setAmount(2.00);
        assertEquals(2.00, e.getAmount());
    }

    @Test
    void setDate() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food", "EUR");
        e.setDate(new Date(2024-8-2));
        assertEquals(new Date(2024-8-2), e.getDate());
    }

    @Test
    void setTitle() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food", "EUR");
        e.setTitle("drinks");
        assertEquals("drinks", e.getTitle());
    }

    @Test
    void setId() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food", "EUR");
        e.setId(101);
        assertEquals(101, e.getId());
    }

    @Test
    void setTag() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food", "EUR");
        e.setTag("activity");
        assertEquals("activity", e.getTag());
    }

    @Test
    public void hasToString() {
        var actual = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food", "EUR").toString();
        assertTrue(actual.contains(Expense.class.getSimpleName()));
        assertTrue(actual.contains(SOME_EVENT.toString()));
        assertTrue(actual.contains(SOME_PARTICIPANT.toString()));
        assertTrue(actual.contains("4.5"));
        assertTrue(actual.contains("snack"));
        assertTrue(actual.contains("food"));
        assertTrue(actual.contains("EUR"));
        assertTrue(actual.contains(new Date(2024-11-10).toString()));
    }

    @Test
    void testEqualsHash() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food", "EUR");
        var e2 = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food", "EUR");
        assertEquals(e, e2);
        assertEquals(e.hashCode(), e2.hashCode());
    }

    @Test
    void testNotEqualsHash() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food", "EUR");
        var e2 = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-8-10), "snack", "food", "EUR");
        assertNotEquals(e, e2);
        assertNotEquals(e.hashCode(), e2.hashCode());
    }

    @Test
    void setEvent() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food", "EUR");
        Event newEvent = new Event("newEvent");
        e.setEvent(newEvent);
        assertEquals(newEvent, e.getEvent());
    }

    @Test
    void setCreditor() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food", "EUR");
        Participant newCreditor = new Participant(SOME_EVENT, "John",
                "John@gmail.com", "NL99INGB0123456789", "RABONL2U");
        e.setCreditor(newCreditor);
        assertEquals(newCreditor, e.getCreditor());
    }
}