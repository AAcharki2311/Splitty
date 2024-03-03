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
        if (expense == null) {
            return ResponseEntity.badRequest().build();
        }
        Expense postedExpense = expenseRepository.save(expense);
        return ResponseEntity.ok(postedExpense);
    }

    // method to select sorted

}
