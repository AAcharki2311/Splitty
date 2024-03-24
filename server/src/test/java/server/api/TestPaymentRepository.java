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

    public void flush() {

    }

    public <S extends Payment> S saveAndFlush(S entity) {
        return null;
    }

    public <S extends Payment> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    public void deleteAllInBatch(Iterable<Payment> entities) {

    }

    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    public void deleteAllInBatch() {

    }

    public Payment getOne(Long aLong) {
        return null;
    }

    public Payment getById(Long aLong) {
        return null;
    }

    public Payment getReferenceById(Long id) {
        call("getReferenceById");
        return find(id).get();
    }

    public <S extends Payment> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    public <S extends Payment> List<S> findAll(Example<S> example) {
        return null;
    }

    public <S extends Payment> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    public <S extends Payment> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    public <S extends Payment> long count(Example<S> example) {
        return 0;
    }

    public <S extends Payment> boolean exists(Example<S> example) {
        return false;
    }

    public <S extends Payment, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    public <S extends Payment> S save(S entity) {
        call("save");
        payments.add(entity);
        return null;
    }

    public <S extends Payment> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    private Optional<Payment> find(Long id) {
        return payments.stream().filter(q -> q.getId() == id).findFirst();
    }

    public Optional<Payment> findById(Long aLong) {
        return find(aLong);
    }

    public boolean existsById(Long aLong) {
        return false;
    }

    public List<Payment> findAll() {
        calledMethods.add("findAll");
        return payments;
    }

    public List<Payment> findAllById(Iterable<Long> longs) {
        return null;
    }

    public long count() {
        return payments.size();
    }

    public void deleteById(Long aLong) {

    }

    public void delete(Payment entity) {

    }

    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    public void deleteAll(Iterable<? extends Payment> entities) {

    }

    public void deleteAll() {

    }

    public List<Payment> findAll(Sort sort) {
        return null;
    }

    public Page<Payment> findAll(Pageable pageable) {
        return null;
    }
}
