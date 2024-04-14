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
     * Method to create a payment and add it to the repository
     *
     * @param payment the payment that will be added
     * @return the response from the action
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<Payment> add(@RequestBody Payment payment) {
        if (payment == null || payment.getId() < 0 ||
                payment.getAmount() <= 0 || payment.getDate() == null ||
                payment.getEvent() == null || payment.getPayer() == null ||
                payment.getReceiv() == null) {
            return ResponseEntity.badRequest().build();
        }
        Payment postedPayment = paymentRepository.save(payment);
        return ResponseEntity.ok(postedPayment);
    }

    /**
     * Method to have a list of all the payments sorted by their date
     *
     * @return list of payments sorted by date
     */
    public ResponseEntity<List<Payment>> getSortedPaymentsDate() {
        List<Payment> allPayments = paymentRepository.findAll();
        if(allPayments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        allPayments.sort(Comparator.comparing(Payment::getDate));
        return ResponseEntity.ok(allPayments);
    }

    /**
     * Method to have a list of all the payments sorted by their payer
     *
     * @return list of payments sorted by payer
     */
    public ResponseEntity<List<Payment>> getSortedPaymentsPayer() {
        List<Payment> allPayments = paymentRepository.findAll();
        if(allPayments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        allPayments.sort(Comparator.comparing(payment -> payment.getPayer().getName()));
        return ResponseEntity.ok(allPayments);
    }
}
