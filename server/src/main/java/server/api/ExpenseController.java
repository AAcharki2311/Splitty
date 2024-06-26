package server.api;

import commons.Event;
import commons.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import commons.Expense;
import server.database.EventRepository;
import server.database.ExpenseRepository;
import server.database.ParticipantRepository;

import java.util.*;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final Random random;
    private final ExpenseRepository expenseRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ParticipantRepository participantRepository;


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
    public ResponseEntity<List<Expense>> getExpenses() {
        List<Expense> allExpenses = expenseRepository.findAll();
        if (allExpenses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allExpenses);
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

        try{
            Event event = eventRepository.findById(expense.getEvent().getId()).get();
            Participant participant = participantRepository.findById(expense.getCreditor().getId()).get();
            if(event == null || participant == null) {
                throw new NoSuchElementException();
            }
            expense.setEvent(event);
            expense.setCreditor(participant);
            Expense postedExpense = expenseRepository.save(expense);
            return ResponseEntity.ok(postedExpense);
        } catch(NullPointerException e) {
            try {
            Expense postedExpense = expenseRepository.save(expense);
            return ResponseEntity.ok(postedExpense);
            } catch (Exception ex) {
                return ResponseEntity.badRequest().build();
            }
        }
    }

    /**
     * Method to update a expense from its id
     *
     * @param id id of the expense to update
     * @param expense expense with the new parameters
     * @return the response from the action
     */
    @PutMapping("/{id}")
    public ResponseEntity<Expense> update(@PathVariable("id") long id, @RequestBody Expense expense) {
        if (!expenseRepository.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        Expense currentExpense = expenseRepository.findById(id).orElse(null);
        if (currentExpense == null) {
            return ResponseEntity.notFound().build();
        }

        currentExpense.setAmount(expense.getAmount());
        currentExpense.setDate(expense.getDate());
        currentExpense.setTitle(expense.getTitle());
        currentExpense.setTag(expense.getTag());

        Expense newExpense = expenseRepository.save(currentExpense);
        return ResponseEntity.ok(newExpense);
    }

    /**
     * Method to delete an expense from its id
     *
     * @param id id of the expense to delete
     * @return the response from the action
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        if (!expenseRepository.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        expenseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Method to have a list of all the expenses sorted by their title
     *
     * @return list of expenses sorted by title
     */
    public ResponseEntity<List<Expense>> getSortedExpensesTitle() {
        List<Expense> allExpenses = expenseRepository.findAll();
        allExpenses.sort(Comparator.comparing(Expense::getTitle));
        if(allExpenses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allExpenses);
    }

    /**
     * Method to have a list of all the expenses sorted by their creditor's name
     *
     * @return list of expenses sorted by creditor's name
     */
    public ResponseEntity<List<Expense>> getSortedExpensesPerson() {
        List<Expense> allExpenses = expenseRepository.findAll();
        allExpenses.sort(Comparator.comparing(expense -> expense.getCreditor().getName()));
        if(allExpenses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allExpenses);
    }

    /**
     * Method to have a list of all the expenses sorted by their date
     *
     * @return list of expenses sorted by date
     */
    public ResponseEntity<List<Expense>> getSortedExpensesDate() {
        List<Expense> allExpenses = expenseRepository.findAll();
        allExpenses.sort(Comparator.comparing(Expense::getDate));
        if(allExpenses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allExpenses);
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
