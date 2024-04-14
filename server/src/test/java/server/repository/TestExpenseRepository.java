package server.repository;
import java.util.*;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import commons.Expense;
import server.database.ExpenseRepository;

public class TestExpenseRepository implements ExpenseRepository {
    public final List<Expense> expenses = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();
    private void call(String name) {
        calledMethods.add(name);
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public void flush() {
        // TODO Auto-generated method stub
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Expense> S saveAndFlush(S entity) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Expense> List<S> saveAllAndFlush(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Expense> S save(S entity) {
        call("save");
        // entity.id = (long) participants.size();
        expenses.add(entity);
        return entity;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Expense> List<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public Expense getOne(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public Expense getById(Long id) {
        call("getById");
        return find(id).get();
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public Expense getReferenceById(Long id) {
        call("getReferenceById");
        return find(id).get();
    }

    /**
     * Auto-generated method for a temporary repository
     */
    private Optional<Expense> find(Long id) {
        return expenses.stream().filter(q -> q.getId() == id).findFirst();
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Expense> Optional<S> findOne(Example<S> example) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Expense, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public Optional<Expense> findById(Long id) {
        // TODO Auto-generated method stub
        return find(id);
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public List<Expense> findAll() {
        calledMethods.add("findAll");
        return expenses;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Expense> List<S> findAll(Example<S> example) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Expense> List<S> findAll(Example<S> example, Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Expense> Page<S> findAll(Example<S> example, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public List<Expense> findAll(Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public Page<Expense> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public List<Expense> findAllById(Iterable<Long> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public long count() {
        return expenses.size();
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Expense> long count(Example<S> example) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Expense> boolean exists(Example<S> example) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public boolean existsById(Long id) {
        call("existsById");
        return find(id).isPresent();
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public void delete(Expense entity) {
        // TODO Auto-generated method stub
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        // TODO Auto-generated method stub
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public void deleteAll(Iterable<? extends Expense> entities) {
        // TODO Auto-generated method stub
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public void deleteAllInBatch(Iterable<Expense> entities) {
        // TODO Auto-generated method stub
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {
        // TODO Auto-generated method stub
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public void deleteAllInBatch() {
        // TODO Auto-generated method stub
    }

}