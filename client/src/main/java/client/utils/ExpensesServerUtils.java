package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.Comparator;
import java.util.List;

import commons.Expense;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;

public class ExpensesServerUtils {
    private final ReadURL readURL;
    private static String serverURL;
    /**
     * ExpensesServerUtils constructor
     * @param readURL readURL object
     */
    @Inject
    public ExpensesServerUtils(ReadURL readURL){
        this.readURL = readURL;
        serverURL = readURL.readServerUrl("src/main/resources/configfile.properties") + "/api/expenses";
    }

    /**
     * @return list of all expenses
     */
    public List<Expense> getExpenses() {
        List<Expense> res;
        try {
            res = ClientBuilder.newClient(new ClientConfig()) //
                    .target(serverURL)
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .get(new GenericType<List<Expense>>() {
                    });
        } catch (NotFoundException e) {
            return List.of();
        }
        return res;
    }

    /**
     * @param expense the expense to add
     * @return added expense
     */
    public Expense addExpense(Expense expense) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverURL)
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
                .target(serverURL).path("/"+id)
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
                .target(serverURL).path("/"+id)
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
                .target(serverURL).path("/"+id)
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