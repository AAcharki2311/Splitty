package server.service;
import commons.Expense;

import commons.Event;
import commons.Participant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.repository.TestExpenseRepository;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseServiceTest {
    private TestExpenseRepository expenseRepository;
    private ExpenseService expenseService;
    private Expense expense1;
    private Expense expense2;
    private Event eventTest = new Event("event");
    private Participant participantTest1 = new Participant(eventTest, "b", "emailTest", "ibanTest", "bicTest");
    private Participant participantTest2 = new Participant(eventTest, "a", "emailTest", "ibanTest", "bicTest");
    private Date dateTest1 = new Date();
    private Date dateTest2 = new Date();

    /**
     * Set up for the tests
     */
    @BeforeEach
    public void setup() {
        expenseRepository = new TestExpenseRepository();
        expenseService = new ExpenseService(expenseRepository);
        expense1 = new Expense(eventTest, participantTest1, 1.0, dateTest1, "b", "tahTest1", "EUR");
        expense2 = new Expense(eventTest, participantTest2, 1.5, dateTest2, "a", "tagTest2", "EUR");
    }

    /**
     * Tests for the methods add and get
     */
    @Test
    public void addAndGetTest() {
        expenseService.add(expense1);
        expenseService.add(expense2);
        List<Expense> list = expenseService.getExpenses();
        assertEquals(expense1, list.get(0));
        assertEquals(expense2, list.get(1));
    }
}