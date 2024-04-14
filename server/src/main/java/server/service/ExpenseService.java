package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.ExpenseRepository;
import commons.Expense;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    /**
     * The constructor for the class
     *
     * @param expenseRepository the repository of Expense
     */
    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    /**
     * Method to get all the expense in the repository
     *
     * @return all the expenses in the current repository
     */
    public List<Expense> getExpenses() {
        List<Expense> allExpenses = expenseRepository.findAll();
        if(allExpenses.isEmpty()) {
            throw new NoSuchElementException("The repository is empty");
        }
        return allExpenses;
    }

    /**
     * Method to find an expense from its id
     *
     * @param id id of the expense to find
     * @return the expense with that specific id (if there is one)
     */
    public Optional<Expense> getById(long id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        if(expense.isEmpty()) {
            throw new NoSuchElementException("There is no expense with the given ID");
        }
        return expense;
    }

    /**
     * Method to add an expense to the repository
     *
     * @param expense the expense that will be added
     * @return the added expense
     */
    public Expense add(Expense expense) {
        if (expense == null || expense.getId() < 0 ||
                expense.getAmount() < 0 || expense.getCreditor() == null ||
                expense.getDate() == null || isNullOrEmpty(expense.getTag()) ||
                isNullOrEmpty(expense.getTitle()) || expense.getEvent() == null ||
                isNullOrEmpty(expense.getCur())) {
            throw new IllegalArgumentException("Expense is not valid");
        }
        return expenseRepository.save(expense);
    }

    /**
     * Method to update an expense from its id
     *
     * @param id id of the expense to update
     * @param updatedExpense expense with the new parameters
     * @return the updated expense if there are no errors
     */
    public Optional<Expense> update(long id, Expense updatedExpense) {
        if(expenseRepository.existsById(id)) {
            updatedExpense.setId(id); // We need to keep the same id
            return Optional.of(expenseRepository.save(updatedExpense));
        }
        else {
            return Optional.empty();
        }
    }

    /**
     * Method to delete an expense from its id
     *
     * @param id id of the expense to delete
     * @return true if the expense has been deleted, false if there was no expense with that id
     */
    public boolean delete(long id) {
        if (expenseRepository.findById(id).isPresent()) {
            expenseRepository.deleteById(id);
            return true;
        }
        else {return false;}
    }

    /**
     * Method to have a list of all the expenses sorted by their title
     *
     * @return list of expenses sorted by title
     */
    public List<Expense> getSortedExpensesTitle() {
        List<Expense> allExpenses = expenseRepository.findAll();
        if(allExpenses.isEmpty()) {
            throw new NoSuchElementException("The repository is empty");
        }
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
        if(allExpenses.isEmpty()) {
            throw new NoSuchElementException("The repository is empty");
        }
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
        if(allExpenses.isEmpty()) {
            throw new NoSuchElementException("The repository is empty");
        }
        allExpenses.sort(Comparator.comparing(Expense::getDate));
        return allExpenses;
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
