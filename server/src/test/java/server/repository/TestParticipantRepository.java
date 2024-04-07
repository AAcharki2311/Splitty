package server.api;
import java.util.*;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import commons.Participant;
import server.database.ParticipantRepository;

public class TestParticipantRepository implements ParticipantRepository {
    public final List<Participant> participants = new ArrayList<>();
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
    public <S extends Participant> S saveAndFlush(S entity) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Participant> List<S> saveAllAndFlush(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Participant> S save(S entity) {
        call("save");
        // entity.id = (long) participants.size();
        participants.add(entity);
        return entity;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Participant> List<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public Participant getOne(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public Participant getById(Long id) {
        call("getById");
        return find(id).get();
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public Participant getReferenceById(Long id) {
        call("getReferenceById");
        return find(id).get();
    }

    /**
     * Auto-generated method for a temporary repository
     */
    private Optional<Participant> find(Long id) {
        return participants.stream().filter(q -> q.getId() == id).findFirst();
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Participant> Optional<S> findOne(Example<S> example) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Participant, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public Optional<Participant> findById(Long id) {
        // TODO Auto-generated method stub
        return find(id);
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public List<Participant> findAll() {
        calledMethods.add("findAll");
        return participants;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Participant> List<S> findAll(Example<S> example) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Participant> List<S> findAll(Example<S> example, Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Participant> Page<S> findAll(Example<S> example, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public List<Participant> findAll(Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public Page<Participant> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public List<Participant> findAllById(Iterable<Long> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public long count() {
        return participants.size();
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Participant> long count(Example<S> example) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Auto-generated method for a temporary repository
     */
    @Override
    public <S extends Participant> boolean exists(Example<S> example) {
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
    public void delete(Participant entity) {
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
    public void deleteAll(Iterable<? extends Participant> entities) {
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
    public void deleteAllInBatch(Iterable<Participant> entities) {
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