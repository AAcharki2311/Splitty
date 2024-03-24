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
     * Auto-generated method for a temporary repository
     */
    public <S extends Payment> S saveAndFlush(S entity) {
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    public <S extends Payment> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    public void deleteAllInBatch(Iterable<Payment> entities) {

    }

    /**
     * Auto-generated method for a temporary repository
     */
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    /**
     * Auto-generated method for a temporary repository
     */
    public void deleteAllInBatch() {

    }

    /**
     * Auto-generated method for a temporary repository
     */
    public Payment getOne(Long aLong) {
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    public Payment getById(Long aLong) {
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    public Payment getReferenceById(Long id) {
        call("getReferenceById");
        return find(id).get();
    }

    /**
     * Auto-generated method for a temporary repository
     */
    public <S extends Payment> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    /**
     * Auto-generated method for a temporary repository
     */
    public <S extends Payment> List<S> findAll(Example<S> example) {
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    public <S extends Payment> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    public <S extends Payment> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    public <S extends Payment> long count(Example<S> example) {
        return 0;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    public <S extends Payment> boolean exists(Example<S> example) {
        return false;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    public <S extends Payment, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    public <S extends Payment> S save(S entity) {
        call("save");
        payments.add(entity);
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    public <S extends Payment> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    private Optional<Payment> find(Long id) {
        return payments.stream().filter(q -> q.getId() == id).findFirst();
    }

    /**
     * Auto-generated method for a temporary repository
     */
    public Optional<Payment> findById(Long aLong) {
        return find(aLong);
    }

    /**
     * Auto-generated method for a temporary repository
     */
    public boolean existsById(Long aLong) {
        return false;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    public List<Payment> findAll() {
        calledMethods.add("findAll");
        return payments;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    public List<Payment> findAllById(Iterable<Long> longs) {
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    public long count() {
        return payments.size();
    }

    /**
     * Auto-generated method for a temporary repository
     */
    public void deleteById(Long aLong) {

    }

    /**
     * Auto-generated method for a temporary repository
     */
    public void delete(Payment entity) {

    }

    /**
     * Auto-generated method for a temporary repository
     */
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    /**
     * Auto-generated method for a temporary repository
     */
    public void deleteAll(Iterable<? extends Payment> entities) {

    }

    /**
     * Auto-generated method for a temporary repository
     */
    public void deleteAll() {

    }

    /**
     * Auto-generated method for a temporary repository
     */
    public List<Payment> findAll(Sort sort) {
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    public Page<Payment> findAll(Pageable pageable) {
        return null;
    }
}
