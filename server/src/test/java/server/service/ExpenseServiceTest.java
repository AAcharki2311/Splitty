package server.service;
import commons.Expense;

import commons.Event;
import commons.Participant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.repository.TestExpenseRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    /**
     * Test for the method getById
     */
    @Test
    public void getByIdTest() {
        expenseService.add(expense1);
        long id1 = expense1.getId();
        expenseService.add(expense2);
        long id2 = expense2.getId();
        assertTrue(expenseService.getById(id1).isPresent());
        assertEquals(expense1, expenseService.getById(id1).get());
        assertTrue(expenseService.getById(id2).isPresent());
        assertNotEquals(expense2, expenseService.getById(id2).get());
    }

    /**
     * Test for the method getById if it's non-existing
     */
    @Test
    public void getByWrongIdTest() {
        long id = -1;
        assertFalse(expenseService.getById(id).isPresent());
    }

    /**
     * Test for the update method
     */
    @Test
    public void updateTest() {
        expenseService.add(expense1);
        Expense updatedExpense = new Expense(eventTest, participantTest1, 4.5, dateTest2, "titleUpdate","tagUpdate", "curUpdate");
        assertTrue(expenseService.update(expense1.getId(), updatedExpense).isPresent());
        assertEquals(updatedExpense.getEvent(), expenseService.update(expense1.getId(), updatedExpense).get().getEvent()); // Check the updated event of the Expense
        assertEquals(updatedExpense.getAmount(), expenseService.update(expense1.getId(), updatedExpense).get().getAmount()); // Check the updated amount of the Expense
        assertEquals(updatedExpense.getDate(), expenseService.update(expense1.getId(), updatedExpense).get().getDate()); // Check the updated date of the Expense
        assertEquals(updatedExpense.getTitle(), expenseService.update(expense1.getId(), updatedExpense).get().getTitle()); // Check the updated title of the Expense
        assertEquals(updatedExpense.getTag(), expenseService.update(expense1.getId(), updatedExpense).get().getTag()); // Check the updated tag of the Expense
    }

    /**
     * Test for the delete method
     *
    @Test
    public void deleteTest() {
        expenseService.add(expense1);
        ResponseEntity<Void> responseEntity = expenseService.delete(expense1.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode()); // Check status code
        assertNull(responseEntity.getBody()); // Check that the object is null
    }
    */

    /**
     * Test for the method getSortedExpensesTitle
     */
    @Test
    public void getSortedTitleTest() {
        expenseService.add(expense1);
        expenseService.add(expense2);
        List<Expense> checkExpense = new ArrayList<>();
        checkExpense.add(expense2);
        checkExpense.add(expense1);
        assertEquals(checkExpense, expenseService.getSortedExpensesTitle()); // Check return list
    }

    /**
     * Test for the method getSortedExpensesPerson
     */
    @Test
    public void getSortedPersonTest() {
        expenseService.add(expense1);
        expenseService.add(expense2);
        List<Expense> checkExpense = new ArrayList<>();
        checkExpense.add(expense2);
        checkExpense.add(expense1);
        assertEquals(checkExpense, expenseService.getSortedExpensesPerson()); // Check return list
    }

    /**
     * Test for the method getSortedExpensesDate
     */
    @Test
    public void getSortedDateTest() {
        expenseService.add(expense1);
        expenseService.add(expense2);
        List<Expense> checkExpense = new ArrayList<>();
        checkExpense.add(expense1);
        checkExpense.add(expense2);
        assertEquals(checkExpense, expenseService.getSortedExpensesDate()); // Check return list
    }

}