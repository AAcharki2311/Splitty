package client.utils;

import commons.Payment;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import java.util.Comparator;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class PaymentsServerUtils {

    static ReadURL readURL = new ReadURL();
    private static final String SERVER = readURL.readServerUrl() + "/api/payments";

    /**
     * @return list of all payments
     */
    public List<Payment> getPayments() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Payment>>() {});
    }

    /**
     * @param payment the payment to add
     * @return the added payment
     */
    public Payment addPayments(Payment payment) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(payment, APPLICATION_JSON), Payment.class);
    }

    /**
     * @param id the id of the payment to retrieve
     * @return the retrieved payment
     */
    public Payment getPaymentByID(long id) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/"+id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<Payment>() {});
    }

    /**
     * @return a list of payments sorted by date
     */
    public List<Payment> getSortedPaymentsDate() {
        List<Payment> paymentsList = getPayments();
        paymentsList.sort(Comparator.comparing(Payment::getDate));
        return paymentsList;
    }

    /**
     * @return a list of payments sorted by payer
     */
    public List<Payment> getSortedPaymentsPayer() {
        List<Payment> paymentsList = getPayments();
        paymentsList.sort(Comparator.comparing(payment -> payment.getPayer().getName()));
        return paymentsList;
    }
}