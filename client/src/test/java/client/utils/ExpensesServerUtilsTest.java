package client.utils;

import commons.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import java.util.ArrayList;
import java.util.List;

class ExpensesServerUtilsTest {

    private List<Expense> expenses;
    private ExpensesServerUtils expserver;

    @BeforeEach
    public void setupExpenses() {
        expserver = new ExpensesServerUtils(new ReadURL());
        expenses = new ArrayList<>();
        expenses.add(new Expense(null, null, 10, new Date(), "A", "A", "A", 1));
        expenses.add(new Expense(null, null, 15, new Date(), "C", "C", "C", 3));
        expenses.add(new Expense(null, null, 120, new Date(), "B", "B", "B", 2));
    }

    @Test
    void getSortedExpensesTitle() {
        // List<Expense> test = expserver.getSortedExpensesTitle();
    }
}