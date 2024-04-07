package server.api;

import commons.Payment;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.PaymentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestPaymentRepository implements PaymentRepository {
    public final List<Payment> payments = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();
    private void call(String name){
        calledMethods.add(name);
    }
    /**
     * Auto-generated method for a temporary repository
     */
    public void flush() {

    }

    /**
     * @param entity entity
     * @param <S> payment
     * @return null
     */
    public <S extends Payment> S saveAndFlush(S entity) {
        return null;
    }

    /**
     * @param entities entities
     * @param <S> payment
     * @return null
     */
    public <S extends Payment> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    /**
     * @param entities entities
     */
    public void deleteAllInBatch(Iterable<Payment> entities) {

    }


    /**
     * @param longs longs
     */
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    /**
     * Auto-generated method for a temporary repository
     */
    public void deleteAllInBatch() {

    }

    /**
     * @param aLong long
     * @return payment
     */
    public Payment getOne(Long aLong) {
        return null;
    }

    /**
     * @param aLong the id of the payment to find
     * @return payment with id
     */
    public Payment getById(Long aLong) {
        return null;
    }

    /**
     * @param id the id of the payment to find
     * @return payment with desired id
     */
    public Payment getReferenceById(Long id) {
        call("getReferenceById");
        return find(id).get();
    }

    /**
     * @param example example
     * @param <S> payment
     * @return optional payment
     */
    public <S extends Payment> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    /**
     * @param example example
     * @param <S> payment
     * @return null
     */
    public <S extends Payment> List<S> findAll(Example<S> example) {
        return null;
    }

    /**
     * @param example example
     * @param sort sort by
     * @param <S> payment
     * @return null
     */
    public <S extends Payment> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    /**
     * @param example example
     * @param pageable pageable
     * @param <S> payment
     * @return null
     */
    public <S extends Payment> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    /**
     * @param example example
     * @param <S> payment
     * @return the number of payments
     */
    public <S extends Payment> long count(Example<S> example) {
        return payments.size();
    }

    /**
     * @param example example
     * @param <S> payment
     * @return false
     */
    public <S extends Payment> boolean exists(Example<S> example) {
        return false;
    }

    /**
     * @param example example
     * @param queryFunction queryFunction
     * @param <S> payment
     * @param <R> payment
     * @return null
     */
    public <S extends Payment, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    /**
     * @param entity entity to save
     * @param <S> payment
     * @return null
     */
    public <S extends Payment> S save(S entity) {
        call("save");
        payments.add(entity);
        return null;
    }

    /**
     * @param entities entities
     * @param <S> payment
     * @return null
     */
    public <S extends Payment> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    /**
     * @param id the id of the payment to find
     * @return payment with the given id
     */
    private Optional<Payment> find(Long id) {
        return payments.stream().filter(q -> q.getId() == id).findFirst();
    }

    /**
     * @param aLong the id to find
     * @return payment with desired id
     */
    public Optional<Payment> findById(Long aLong) {
        return find(aLong);
    }

    /**
     * @param aLong id
     * @return false
     */
    public boolean existsById(Long aLong) {
        return false;
    }

    /**
     * @return list of all payments
     */
    public List<Payment> findAll() {
        calledMethods.add("findAll");
        return payments;
    }

    /**
     * @param longs ids to find
     * @return null
     */
    public List<Payment> findAllById(Iterable<Long> longs) {
        return null;
    }

    /**
     * @return the number of payments
     */
    public long count() {
        return payments.size();
    }

    /**
     * @param aLong the id of the payment to delete
     */
    public void deleteById(Long aLong) {

    }

    /**
     * @param entity the payment to delete
     */
    public void delete(Payment entity) {

    }

    /**
     * @param longs the ids of the payments to delete
     */
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    /**
     * @param entities the payments to delete
     */
    public void deleteAll(Iterable<? extends Payment> entities) {

    }

    /**
     * Auto-generated method for a temporary repository
     */
    public void deleteAll() {

    }

    /**
     * @param sort sort by
     * @return null
     */
    public List<Payment> findAll(Sort sort) {
        return null;
    }

    /**
     * @param pageable pageable
     * @return null
     */
    public Page<Payment> findAll(Pageable pageable) {
        return null;
    }
}
