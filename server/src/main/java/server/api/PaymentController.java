package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import commons.Payment;
import server.database.PaymentRepository;
import java.util.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final Random random;
    private final PaymentRepository paymentRepository;

    /**
     * Constructor for the controller of the payments
     *
     * @param random variable random
     * @param paymentRepository repository for payments
     */
    public PaymentController(Random random, PaymentRepository paymentRepository) {
        this.random = random;
        this.paymentRepository = paymentRepository;
    }

    /**
     * Method to get all the payments in the repository (unsorted)
     *
     * @return all the payments in the current repository
     */
    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }

    /**
     * Method to create a payment and add it to the repository
     *
     * @param payment the payment that will be added
     * @return the response from the action
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<Payment> add(@RequestBody Payment payment) {
        if (payment == null) {
            return ResponseEntity.badRequest().build();
        }
        Payment postedPayment = paymentRepository.save(payment);
        return ResponseEntity.ok(postedPayment);
    }

    // method to select sorted

}
