package server.service;

import commons.Payment;
import server.database.PaymentRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PaymentService {
    private final PaymentRepository paymentRepository;

    /**
     * class constructor
     *
     * @param paymentRepository the repository of payments
     */
    public PaymentService(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }

    /**
     * @return all the payments in the current repository
     */
    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }

    /**
     * returns payment with the provided id
     *
     * @param id the id of the payment to find
     * @return the payment with the provided id
     */
    public Optional<Payment> getById(long id) {
        return paymentRepository.findById(id);
    }

    /**
     * adds payment to the repository
     *
     * @param payment the payment to add
     * @return the added payment
     */
    public Payment add(Payment payment) {
        return paymentRepository.save(payment);
    }

    /**
     * Updates a payment form id
     *
     * @param id the id of the payment to update
     * @param updatedPayment payment with the new parameters
     * @return the updated payment or an empty optional
     */
    public Optional<Payment> update(long id, Payment updatedPayment) {
        if(paymentRepository.existsById(id)) {
            updatedPayment.setId(id);
            return Optional.of(paymentRepository.save(updatedPayment));
        }
        else {
            return Optional.empty();
        }
    }

    /**
     * @param id the id of the payment to delete
     * @return true if the payment was deleted, false if it could not be found
     */
    public boolean delete(long id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * @return list of all payments sorted by the payer
     */
    public List<Payment> getSortedPaymentsPayer() {
        List<Payment> paymentsList = paymentRepository.findAll();
        paymentsList.sort(Comparator.comparing(payment -> payment.getPayer().getName()));
        return paymentsList;
    }

    /**
     * @return list of all payments sorted by the receiver's name
     */
    public List<Payment> getSortedPaymentsReceiver() {
        List<Payment> paymentsList = paymentRepository.findAll();
        paymentsList.sort(Comparator.comparing(payment -> payment.getReceiv().getName()));
        return paymentsList;
    }

    /**
     * @return list of all payments sorted by name of the related event
     */
    public List<Payment> getSortedPaymentsEvent() {
        List<Payment> paymentsList = paymentRepository.findAll();
        paymentsList.sort(Comparator.comparing(payment -> payment.getEvent().getName()));
        return paymentsList;
    }

    /**
     * @return list of all payments sorted by amount
     */
    public List<Payment> getSortedPaymentsAmount() {
        List<Payment> paymentsList = paymentRepository.findAll();
        paymentsList.sort(Comparator.comparing(Payment::getAmount));
        return paymentsList;
    }

    /**
     * @return list of all payments sorted by the date of the payment
     */
    public List<Payment> getSortedPaymentsDate() {
        List<Payment> paymentsList = paymentRepository.findAll();
        paymentsList.sort(Comparator.comparing(Payment::getDate));
        return paymentsList;
    }
}
