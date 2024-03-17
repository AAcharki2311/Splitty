package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import commons.Expense;
import server.database.ExpenseRepository;
import java.util.*;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final Random random;
    private final ExpenseRepository expenseRepository;

    /**
     * Constructor for the controller of the expenses
     *
     * @param random variable random
     * @param expenseRepository repository for expenses
     */
    public ExpenseController(Random random, ExpenseRepository expenseRepository) {
        this.random = random;
        this.expenseRepository = expenseRepository;
    }

    /**
     * Method to get all the expenses in the repository (unsorted)
     *
     * @return all the expenses in the current repository
     */
    @GetMapping(path = {"", "/"})
    public List<Expense> getExpenses() {
        return expenseRepository.findAll();
    }

    /**
     * Method to find an expense from its id
     *
     * @param id id of the expense to find
     * @return the expense with that specific id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getById(@PathVariable("id") long id) {
        if (id < 0 || !expenseRepository.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(expenseRepository.findById(id).get());
    }

    /**
     * Method to create an expense and add it to the repository
     *
     * @param expense the expense that will be added
     * @return the response from the action
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<Expense> add(@RequestBody Expense expense) {
        if (expense == null || expense.getId() < 0 ||
        expense.getAmount() < 0 || expense.getCreditor() == null ||
        expense.getDate() == null || isNullOrEmpty(expense.getTag()) ||
        isNullOrEmpty(expense.getTitle()) || expense.getEvent() == null) {
            return ResponseEntity.badRequest().build();
        }
        Expense postedExpense = expenseRepository.save(expense);
        return ResponseEntity.ok(postedExpense);
    }

    /**
     * Method to have a list of all the expenses sorted by their title
     *
     * @return list of expenses sorted by title
     */
    public List<Expense> getSortedExpensesTitle() {
        List<Expense> allExpenses = expenseRepository.findAll();
        allExpenses.sort(Comparator.comparing(Expense::getTitle));
        return allExpenses;
    }

    /**
     * Method to have a list of all the expenses sorted by their creditor's name
     *
     * @return list of expenses sorted by creditor's name
     */
    public List<Expense> getSortedExpensesPerson() {
        List<Expense> allExpenses = expenseRepository.findAll();
        allExpenses.sort(Comparator.comparing(expense -> expense.getCreditor().getName()));
        return allExpenses;
    }

    /**
     * Method to have a list of all the expenses sorted by their date
     *
     * @return list of expenses sorted by date
     */
    public List<Expense> getSortedExpensesDate() {
        List<Expense> allExpenses = expenseRepository.findAll();
        allExpenses.sort(Comparator.comparing(Expense::getDate));
        return allExpenses;
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
