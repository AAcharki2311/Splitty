package client.utils;

import commons.Payment;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.http.ResponseEntity;

import java.util.Comparator;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class PaymentsServerUtils {

    private final ReadURL readURL;
    private final String SERVER;

    /**
     * PaymentsServerUtils constructor
     * @param readURL readURL object
     */
    @Inject
    public PaymentsServerUtils(ReadURL readURL){
        this.readURL = readURL;
        this.SERVER = readURL.readServerUrl("src/main/resources/configfile.properties") + "/api/payments";
    }
    /**
     * @return list of all payments
     */
    public List<Payment> getPayments() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<ResponseEntity<List<Payment>>>() {}).getBody();
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