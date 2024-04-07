package server.service;

import commons.Participant;
import commons.Payment;
import commons.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.api.TestPaymentRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceTest {
    public int nextInt;
    private PaymentServiceTest.MyRandom random;
    private TestPaymentRepository paymentRepository;
    private PaymentService paymentService;
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
        paymentService = new PaymentService(paymentRepository);
        debtor = new Participant(eventTest, "nameTest1","emailTest1","ibanTest1","bicTest1");
        creditor = new Participant(eventTest, "nameTest2","emailTest2","ibanTest2","bicTest2");
        payment1 = new Payment(eventTest, debtor,creditor,120.0,dateTest);
        payment2 = new Payment(eventTest, debtor,creditor,100.0,new Date(2002,1,1));
        payment1.setId(1);
        payment2.setId(2);
    }

    /**
     * tests te add and get methods
     */
    @Test
    public void testAddAndGet() {
        paymentService.add(payment1);
        paymentService.add(payment2);
        List<Payment> list = paymentService.getPayments();
        assertEquals(list.get(0),payment1);
        assertEquals(list.get(1),payment2);
    }

    /**
     * Test the getById Method
     */
    @Test
    public void testGetById() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);
        long id1 = payment1.getId();
        long id2 = payment2.getId();
        Optional<Payment> comapre1 = paymentService.getById(id1);
        Optional<Payment> comapre2 = paymentService.getById(id2);

        assertTrue(comapre1.isPresent());
        assertTrue(comapre2.isPresent());
        assertEquals(payment1,comapre1.get());
        assertEquals(payment2,comapre2.get());
    }

//    @Test
//    public void testDeleteById() {
//        paymentRepository.save(payment1);
//        long id = payment1.getId();
//        paymentService.delete(id);
//        Optional<Payment> emptyPayment = paymentService.getById(id);
//        assertFalse(emptyPayment.isPresent());
//    }
//
//    @Test
//    public void testUpdate() {
//        paymentService.add(payment1);
//        Payment updatedPayment = new Payment(eventTest, debtor,creditor,20000.0,dateTest); //updated amount
//        Optional<Payment> paymentTest = paymentService.update(payment1.getId(),updatedPayment);
//        assertTrue(paymentTest.isPresent());
//        assertEquals(paymentTest.get(),updatedPayment);
//    }

    /**
     * test for sorting payments by amount
     */
    @Test
    public void getSortedPaymentsAmountTest() {
        paymentService.add(payment1);
        paymentService.add(payment2);
        List<Payment> sortedList = paymentService.getSortedPaymentsAmount();
        assertEquals(sortedList.get(0),payment2);
        assertEquals(sortedList.get(1),payment1);
    }

    /**
     * test for sorting payments by date
     */
    @Test
    public void getSortedPaymentsDateTest() {
        paymentService.add(payment1);
        paymentService.add(payment2);
        List<Payment> sortedList = paymentService.getSortedPaymentsDate();
        assertEquals(sortedList.get(0),payment1);
        assertEquals(sortedList.get(1),payment2);
    }

    /**
     * test for sorting the list of payments by event
     */
    @Test void getSortedPaymentsEventTest() {
        Event event1 = new Event("AAA_start");
        Event event2 = new Event("ZZZ_end");
        Payment testPayment1 = payment1;
        Payment testPayment2 = payment2;
        payment1.setEvent(event1);
        payment2.setEvent(event2);

        paymentService.add(testPayment1);
        paymentService.add(testPayment2);
        List<Payment> sortedList = paymentService.getSortedPaymentsEvent();
        assertEquals(sortedList.get(0),testPayment1);
        assertEquals(sortedList.get(1),testPayment2);
    }

    /**
     * test for sorting the list of payments by the payers name
     */
    @Test void getSortedPaymentsPayerTest() {
        Participant payer1 = new Participant(eventTest, "Andrew","emailTest1","ibanTest1","bicTest1"); //top of list
        Participant payer2 = new Participant(eventTest, "Zorian","emailTest1","ibanTest1","bicTest1"); //bottom of list
        Payment testPayment1 = payment1;
        Payment testPayment2 = payment2;
        payment1.setPayer(payer1);
        payment2.setPayer(payer2);

        paymentService.add(testPayment1);
        paymentService.add(testPayment2);
        List<Payment> sortedList = paymentService.getSortedPaymentsPayer();
        assertEquals(sortedList.get(0),testPayment1);
        assertEquals(sortedList.get(1),testPayment2);
    }

    /**
     * test for sorting the list of payments by the payers name
     */
    @Test void getSortedPaymentsPayerEmailTest() {
        Participant payer1 = new Participant(eventTest, "Andrew","AAemailTest1","ibanTest1","bicTest1"); //top of list
        Participant payer2 = new Participant(eventTest, "Zorian","BBemailTest1","ibanTest1","bicTest1"); //bottom of list
        Payment testPayment1 = payment1;
        Payment testPayment2 = payment2;
        payment1.setPayer(payer1);
        payment2.setPayer(payer2);

        paymentService.add(testPayment1);
        paymentService.add(testPayment2);
        List<Payment> sortedList = paymentService.getSortedPaymentsPayerEmail();
        assertEquals(sortedList.get(0),testPayment1);
        assertEquals(sortedList.get(1),testPayment2);
    }

    @Test void getSortedPaymentsRecieverTest() {
        Participant reciever1 = new Participant(eventTest, "Andrew","emailTest1","ibanTest1","bicTest1"); //top of list
        Participant reciever2 = new Participant(eventTest, "Zorian","emailTest1","ibanTest1","bicTest1"); //bottom of list
        Payment testPayment1 = payment1;
        Payment testPayment2 = payment2;
        payment1.setReceiv(reciever1);
        payment2.setReceiv(reciever2);

        paymentService.add(testPayment1);
        paymentService.add(testPayment2);
        List<Payment> sortedList = paymentService.getSortedPaymentsReceiver();
        assertEquals(sortedList.get(0),testPayment1);
        assertEquals(sortedList.get(1),testPayment2);
    }

    @Test void getSortedPaymentsRecieverEmailTest() {
        Participant reciever1 = new Participant(eventTest, "Andrew","AAemailTest1","ibanTest1","bicTest1"); //top of list
        Participant reciever2 = new Participant(eventTest, "Zorian","ZZemailTest1","ibanTest1","bicTest1"); //bottom of list
        Payment testPayment1 = payment1;
        Payment testPayment2 = payment2;
        payment1.setReceiv(reciever1);
        payment2.setReceiv(reciever2);

        paymentService.add(testPayment1);
        paymentService.add(testPayment2);
        List<Payment> sortedList = paymentService.getSortedPaymentsReceiverEmail();
        assertEquals(sortedList.get(0),testPayment1);
        assertEquals(sortedList.get(1),testPayment2);
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

