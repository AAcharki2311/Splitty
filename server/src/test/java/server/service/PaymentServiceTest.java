package server.service;

import commons.Participant;
import commons.Payment;
import commons.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.api.PaymentController;
import server.api.TestPaymentRepository;

import java.util.List;
import java.util.Random;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentServiceTest {
    public int nextInt;
    private PaymentServiceTest.MyRandom random;
    private TestPaymentRepository paymentRepository;
    private PaymentController paymentController;
    private Payment payment1;
    private Payment payment2;
    private Participant debtor;
    private Participant creditor;
    private Event eventTest = new Event("event");
    private Date dateTest = new Date(2000,1,1);

    /**
     * setup for the tests
     */
    @BeforeEach
    public void setup() {
        random = new PaymentServiceTest.MyRandom();
        paymentRepository = new TestPaymentRepository();
        paymentController = new PaymentController(random,paymentRepository);
        debtor = new Participant(eventTest, "nameTest1","emailTest1","ibanTest1","bicTest1");
        creditor = new Participant(eventTest, "nameTest2","emailTest2","ibanTest2","bicTest2");
        payment1 = new Payment(eventTest, debtor,creditor,100.0,dateTest);
        payment2 = new Payment(eventTest, debtor,creditor,100.0,new Date(2002,1,1));
        payment1.setId(1);
        payment2.setId(2);
    }

    @SuppressWarnings("serial")
    public class MyRandom extends Random {

        public boolean wasCalled = false;

        /**
         * Method necessary for testing
         *
         * @param bound the upper bound (exclusive).  Must be positive.
         * @return value of nextInt
         */
        @Override
        public int nextInt(int bound) {
            wasCalled = true;
            return nextInt;
        }

    }
}

