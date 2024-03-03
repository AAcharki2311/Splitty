package commons;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {

    private static final Event SOME_EVENT = new Event("Drinkfest");
    private static final Participant SOME_PARTICIPANT = new Participant(1205, SOME_EVENT,"A", "B");

    @Test
    public void checkConstructor() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food");
//        var e = new Expense(SOME_EVENT, 4.50, new Date(2024-11-10), "snack", "food");
        assertEquals(SOME_EVENT, e.event);
        assertEquals(SOME_PARTICIPANT, e.participant);
        assertNotNull(e);
    }


    @Test
    void getEvent() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food");
        assertEquals(SOME_EVENT, e.getEvent());
    }

    @Test
    void getCreditor() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food");
        assertEquals(SOME_PARTICIPANT, e.getCreditor());
    }

    @Test
    void getAmount() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food");
        assertEquals(4.50, e.getAmount());
    }

    @Test
    void getDate() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food");
        assertEquals(new Date(2024-11-10), e.getDate());

    }

    @Test
    void getDescription() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food");
        assertEquals("snack", e.getDescription());
    }

    @Test
    void getTag() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food");
        assertEquals("food", e.getTag());
    }

    @Test
    void setAmount() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food");
        e.setAmount(2.00);
        assertEquals(2.00, e.getAmount());
    }

    @Test
    void setDate() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food");
        e.setDate(new Date(2024-8-2));
        assertEquals(new Date(2024-8-2), e.getDate());
    }

    @Test
    void setDescription() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food");
        e.setDescription("drinks");
        assertEquals("drinks", e.getDescription());
    }

    @Test
    void setTag() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food");
        e.setTag("activity");
        assertEquals("activity", e.getTag());
    }

    @Test
    public void hasToString() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food").toString();
        assertTrue(e.contains(Expense.class.getSimpleName()));
        assertTrue(e.contains("\n"));
        assertTrue(e.contains("amount"));
    }

    @Test
    void testEqualsHash() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food");
        var e2 = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food");
        assertEquals(e, e2);
        assertEquals(e.hashCode(), e2.hashCode());
    }

    @Test
    void testNotEqualsHash() {
        var e = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-11-10), "snack", "food");
        var e2 = new Expense(SOME_EVENT, SOME_PARTICIPANT, 4.50, new Date(2024-8-10), "snack", "food");
        assertNotEquals(e, e2);
        assertNotEquals(e.hashCode(), e2.hashCode());
    }
}