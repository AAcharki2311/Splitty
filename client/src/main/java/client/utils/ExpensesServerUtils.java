package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import commons.Expense;
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
}