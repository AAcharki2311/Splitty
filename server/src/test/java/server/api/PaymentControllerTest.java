package server.api;

import commons.Participant;
import commons.Payment;
import commons.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentControllerTest {
    public int nextInt;
    private PaymentControllerTest.MyRandom random;
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
        random = new PaymentControllerTest.MyRandom();
        paymentRepository = new TestPaymentRepository();
        paymentController = new PaymentController(random,paymentRepository);
        payment1 = new Payment(eventTest, debtor,creditor,100.0,dateTest);
        payment2 = new Payment(eventTest, debtor,creditor,100.0,dateTest);
        debtor = new Participant(eventTest, "nameTest1","emailTest1","ibanTest1","bicTest1");
        creditor = new Participant(eventTest, "nameTest2","emailTest2","ibanTest2","bicTest2");
    }

    /**
     * Test for the add and get methods
     */
    @Test
    public void addTest() {
        paymentController.add(payment1);
        paymentController.add(payment2);
        List<Payment> list = paymentRepository.findAll();
        assertEquals(payment1, list.get(0));
        assertEquals(payment2, list.get(1));
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
