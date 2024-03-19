package client.utils;

import commons.Payment;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class PaymentsServerUtils {

    private static final String SERVER = "http://localhost:8080/api/payments";

    public List<Payment> getPayments() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Payment>>() {});
    }

    public Payment addPayments(Payment payment) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(payment, APPLICATION_JSON), Payment.class);
    }

    public Payment getPaymentByID(long id) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/"+id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<Payment>() {});
    }
}