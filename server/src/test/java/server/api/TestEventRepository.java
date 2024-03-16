package server.api;
import java.util.*;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import commons.Event;
import server.database.EventRepository;

public class TestEventRepository implements EventRepository {
    public final List<Event> events = new ArrayList<>();
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
    public <S extends Event> S saveAndFlush(S entity) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Event> List<S> saveAllAndFlush(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Event> S save(S entity) {
        call("save");
        entity.id = (long) events.size();
        events.add(entity);
        return entity;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Event> List<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public Event getOne(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public Event getById(Long id) {
        call("getById");
        return find(id).get();
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public Event getReferenceById(Long id) {
        call("getReferenceById");
        return find(id).get();
    }

    /**
     * Auto-generated method for a temporary repository
     */
    private Optional<Event> find(Long id) {
        return events.stream().filter(q -> q.id == id).findFirst();
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Event> Optional<S> findOne(Example<S> example) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Event, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public Optional<Event> findById(Long id) {
        // TODO Auto-generated method stub
        return find(id);
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public List<Event> findAll() {
        calledMethods.add("findAll");
        return events;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Event> List<S> findAll(Example<S> example) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Event> List<S> findAll(Example<S> example, Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Event> Page<S> findAll(Example<S> example, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public List<Event> findAll(Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public Page<Event> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public List<Event> findAllById(Iterable<Long> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public long count() {
        return events.size();
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Event> long count(Example<S> example) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Event> boolean exists(Example<S> example) {
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
    public void delete(Event entity) {
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
    public void deleteAll(Iterable<? extends Event> entities) {
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
    public void deleteAllInBatch(Iterable<Event> entities) {
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
