package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.Comparator;
import java.util.List;

import commons.Expense;
import commons.Participant;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;

public class ExpensesServerUtils {

    private static final String SERVER = "http://localhost:8080/api/expenses";

    /**
     * @return list of all expenses
     */
    public List<Expense> getExpenses() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Expense>>() {});
    }

    /**
     * @param expense the expense to add
     * @return added expense
     */
    public Expense addExpense(Expense expense) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(expense, APPLICATION_JSON), Expense.class);
    }

    /**
     * @param id the id of the expense to get
     * @return expense with given id
     */
    public Expense getExpenseByID(long id) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/"+id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<Expense>() {});
    }

    /**
     * @param id the id of the expense to remove
     * @return if the delete operation was successful
     */
    public boolean deleteExpenseByID(long id) {
        Response response = ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/"+id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete();
        return response.getStatus() == 204;

    }

    /**
     * @param id the id of the expense to update
     *           expense found at: https://localhost:8080/api/expenses/id
     * @param expense the updated expense, replaces the original at id
     * @return the new updated expense
     */
    public Expense updateExpenseByID(long id, Expense expense) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/"+id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(expense,APPLICATION_JSON), Expense.class);
    }

    /**
     * @return list of expenses sorted by title
     */
    public List<Expense> getSortedExpensesTitle() {
        List<Expense> expenseList = getExpenses();
        expenseList.sort(Comparator.comparing(Expense::getTitle));
        return expenseList;
    }

    /**
     * @return list of expenses sorted by creditor's name
     */
    public List<Expense> getSortedExpensesPerson() {
        List<Expense> expenseList = getExpenses();
        expenseList.sort(Comparator.comparing(expense -> expense.getCreditor().getName()));
        return expenseList;
    }

    /**
     * @return list of expenses sorted by date
     */
    public List<Expense> getSortedExpensesDate() {
        List<Expense> expenseList = getExpenses();
        expenseList.sort(Comparator.comparing(Expense::getDate));
        return expenseList;
    }
}