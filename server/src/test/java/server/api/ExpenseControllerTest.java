package server.api;

import commons.Expense;
import commons.Event;
import commons.Participant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

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
    private Participant participantTest1 = new Participant(eventTest, "nameTest1", "emailTest", "ibanTest", "bicTest");
    private Participant participantTest2 = new Participant(eventTest, "nameTest2", "emailTest", "ibanTest", "bicTest");
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
        expense1 = new Expense(eventTest, participantTest1, 1.0, dateTest1, "titleTest1", "tahTest1");
        expense2 = new Expense(eventTest, participantTest2, 1.5, dateTest2, "titleTest2", "tagTest2");
    }

    /**
     * Tests for the methods add and get
     */
    @Test
    public void addAndGetTest() {
        expenseController.add(expense1);
        expenseController.add(expense2);
        List<Expense> list = expenseController.getExpenses();
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
