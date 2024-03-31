package server.api;

import commons.Expense;
import commons.Event;
import commons.Participant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ExpenseControllerTest {
    public int nextInt;
    private server.api.ExpenseControllerTest.MyRandom random;
    private TestExpenseRepository expenseRepository;
    private ExpenseController expenseController;
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
        random = new server.api.ExpenseControllerTest.MyRandom();
        expenseRepository = new TestExpenseRepository();
        expenseController = new ExpenseController(random, expenseRepository);
        expense1 = new Expense(eventTest, participantTest1, 1.0, dateTest1, "b", "tahTest1");
        expense2 = new Expense(eventTest, participantTest2, 1.5, dateTest2, "a", "tagTest2");
    }

    /**
     * Tests for the methods add and get
     */
    @Test
    public void addAndGetTest() {
        expenseController.add(expense1);
        expenseController.add(expense2);
        List<Expense> list = expenseController.getExpenses().getBody();
        assertEquals(expense1, list.get(0));
        assertEquals(expense2, list.get(1));
    }

    /**
     * Test for the method getById
     */
    @Test
    public void getByIdTest() {
        expenseController.add(expense1);
        long id1 = expense1.getId();
        expenseController.add(expense2);
        long id2 = expense2.getId();
        ResponseEntity<Expense> responseEntity1 = expenseController.getById(id1);
        ResponseEntity<Expense> responseEntity2 = expenseController.getById(id2);
        assertEquals(expense1, responseEntity1.getBody());
        assertNotEquals(expense2, responseEntity2.getBody());
    }

    /**
     * Test for the method getById if it's non-existing
     */
    @Test
    public void getByWrongIdTest() {
        long id = -1;
        ResponseEntity<Expense> responseEntity1 = expenseController.getById(id);
        assertEquals(BAD_REQUEST, responseEntity1.getStatusCode());
    }

    /**
     * Test for the update method
     */
    @Test
    public void updateTest() {
        expenseController.add(expense1);
        Expense updatedExpense = new Expense(eventTest, participantTest1, 4.5, dateTest2, "titleUpdate","tagUpdate");
        ResponseEntity<Expense> responseEntity = expenseController.update(expense1.getId(), updatedExpense);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
        assertEquals(updatedExpense.getEvent(), responseEntity.getBody().getEvent()); // Check the updated event of the Expense
        assertEquals(updatedExpense.getAmount(), responseEntity.getBody().getAmount()); // Check the updated amount of the Expense
        assertEquals(updatedExpense.getDate(), responseEntity.getBody().getDate()); // Check the updated date of the Expense
        assertEquals(updatedExpense.getTitle(), responseEntity.getBody().getTitle()); // Check the updated title of the Expense
        assertEquals(updatedExpense.getTag(), responseEntity.getBody().getTag()); // Check the updated tag of the Expense
    }

    /**
     * Test for the delete method
     */
    @Test
    public void deleteTest() {
        expenseController.add(expense1);
        ResponseEntity<Void> responseEntity = expenseController.delete(expense1.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode()); // Check status code
        assertNull(responseEntity.getBody()); // Check that the object is null
    }

    /**
     * Test for the method getSortedExpensesTitle
     */
    @Test
    public void getSortedTitleTest() {
        expenseController.add(expense1);
        expenseController.add(expense2);
        List<Expense> checkExpense = new ArrayList<>();
        checkExpense.add(expense2);
        checkExpense.add(expense1);
        ResponseEntity<List<Expense>> responseEntity = expenseController.getSortedExpensesTitle();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
        assertEquals(checkExpense, responseEntity.getBody()); // Check request body
    }

    /**
     * Test for the method getSortedExpensesPerson
     */
    @Test
    public void getSortedPersonTest() {
        expenseController.add(expense1);
        expenseController.add(expense2);
        List<Expense> checkExpense = new ArrayList<>();
        checkExpense.add(expense2);
        checkExpense.add(expense1);
        ResponseEntity<List<Expense>> responseEntity = expenseController.getSortedExpensesPerson();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
        assertEquals(checkExpense, responseEntity.getBody()); // Check request body
    }

    /**
     * Test for the method getSortedExpensesDate
     */
    @Test
    public void getSortedDate() {
        expenseController.add(expense1);
        expenseController.add(expense2);
        List<Expense> checkExpense = new ArrayList<>();
        checkExpense.add(expense1);
        checkExpense.add(expense2);
        ResponseEntity<List<Expense>> responseEntity = expenseController.getSortedExpensesDate();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
        assertEquals(checkExpense, responseEntity.getBody()); // Check request body
    }

    @SuppressWarnings("serial")
    public class MyRandom extends Random {

        public boolean wasCalled = false;

        /**
         * Method necessary for testing
         *
         * @param bound the upper bound (exclusive).  Must be positive.
         * @return value of nextInt
         */
        @Override
        public int nextInt(int bound) {
            wasCalled = true;
            return nextInt;
        }
    }
}
