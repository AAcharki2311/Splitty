/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package server.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import commons.Quote;
import server.database.QuoteRepository;

public class TestQuoteRepository implements QuoteRepository {

    public final List<Quote> quotes = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }

    /**
     * xhxvgxhcgcvx
     * @return gdhdsh
     */
    @Override
    public List<Quote> findAll() {
        calledMethods.add("findAll");
        return quotes;
    }

    /**
     * hjxgdxhjd
     * @param sort the {@link Sort} specification to sort the results by, can be {@link Sort#unsorted()}, must not be
     *          {@literal null}.
     * @return hjdfshhdfs
     */
    @Override
    public List<Quote> findAll(Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * cxxdfsdfs
     * @param ids must not be {@literal null} nor contain any {@literal null} values.
     * @return ghhjhjhj
     */
    @Override
    public List<Quote> findAllById(Iterable<Long> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * dxdggddf
     * @param entities must not be {@literal null} nor must it contain {@literal null}.
     * @return dgdg
     * @param <S> dfdfg
     */
    @Override
    public <S extends Quote> List<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * xcgfgfgfddg
     */
    @Override
    public void flush() {
        // TODO Auto-generated method stub
    }

    /**
     * gfgfgf
     * @param entity entity to be saved. Must not be {@literal null}.
     * @return gfdf
     * @param <S> gfdf
     */
    @Override
    public <S extends Quote> S saveAndFlush(S entity) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * cvxgfgf
     * @param entities entities to be saved. Must not be {@literal null}.
     * @return gfgfdf
     * @param <S> ghggh
     */
    @Override
    public <S extends Quote> List<S> saveAllAndFlush(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * gffgfdfdf
     * @param entities entities to be deleted. Must not be {@literal null}.
     */
    @Override
    public void deleteAllInBatch(Iterable<Quote> entities) {
        // TODO Auto-generated method stub

    }

    /**
     * cdggdg
     * @param ids the ids of the entities to be deleted. Must not be {@literal null}.
     */
    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {
        // TODO Auto-generated method stub

    }

    /**
     * cgdggdgd
     */
    @Override
    public void deleteAllInBatch() {
        // TODO Auto-generated method stub

    }

    /**
     * cdggggg
     * @param id must not be {@literal null}.
     * @return gggg
     */
    @Override
    public Quote getOne(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * cvffgf
     * @param id must not be {@literal null}.
     * @return bgfgfd
     */
    @Override
    public Quote getById(Long id) {
        call("getById");
        return find(id).get();
    }

    /**
     * fggffdggf
     * @param id must not be {@literal null}.
     * @return gfffgfd
     */
    @Override
    public Quote getReferenceById(Long id) {
        call("getReferenceById");
        return find(id).get();
    }

    /**
     * cgfdffff
     * @param id
     * @return gfddf
     */
    private Optional<Quote> find(Long id) {
        return quotes.stream().filter(q -> q.id == id).findFirst();
    }

    /**
     * gffdfdfff
     * @param example must not be {@literal null}.
     * @return gfdfdf
     * @param <S> gf
     */
    @Override
    public <S extends Quote> List<S> findAll(Example<S> example) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * ffffff
     * @param example must not be {@literal null}.
     * @param sort the {@link Sort} specification to sort the results by, may be {@link Sort#unsorted()}, must not be
     *          {@literal null}.
     * @return gdfg
     * @param <S> dfddg
     */
    @Override
    public <S extends Quote> List<S> findAll(Example<S> example, Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * fhfhfcffx
     * @param pageable the pageable to request a paged result, can be {@link Pageable#unpaged()}, must not be
     *          {@literal null}.
     * @return jdhsj
     */
    @Override
    public Page<Quote> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * ggggg
     * @param entity must not be {@literal null}.
     * @return hdjdgnj
     * @param <S> jgh
     */
    @Override
    public <S extends Quote> S save(S entity) {
        call("save");
        entity.id = (long) quotes.size();
        quotes.add(entity);
        return entity;
    }
    /**
     * Retrieves an optional {@link Quote} by its ID.
     *
     * @param id The ID of the quote to retrieve.
     * @return An {@link Optional} containing the quote if found, otherwise empty.
     */
    @Override
    public Optional<Quote> findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Checks if a quote with the given ID exists.
     *
     * @param id The ID of the quote to check existence for.
     * @return {@code true} if a quote with the given ID exists, otherwise {@code false}.
     */
    @Override
    public boolean existsById(Long id) {
        call("existsById");
        return find(id).isPresent();
    }

    /**
     * Counts the total number of quotes.
     *
     * @return The count of quotes.
     */
    @Override
    public long count() {
        return quotes.size();
    }

    /**
     * Deletes a quote by its ID.
     *
     * @param id The ID of the quote to delete.
     */
    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
    }

    /**
     * Deletes a specific quote.
     *
     * @param entity The quote entity to delete.
     */
    @Override
    public void delete(Quote entity) {
        // TODO Auto-generated method stub
    }

    /**
     * Deletes all quotes with the given IDs.
     *
     * @param ids The IDs of the quotes to delete.
     */
    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        // TODO Auto-generated method stub
    }

    /**
     * Deletes all the given quotes.
     *
     * @param entities The quotes to delete.
     */
    @Override
    public void deleteAll(Iterable<? extends Quote> entities) {
        // TODO Auto-generated method stub
    }

    /**
     * Deletes all quotes.
     */
    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
    }

    /**
     * Retrieves a single instance that matches the provided example.
     *
     * @param example The example to match.
     * @param <S>     The type of the quote.
     * @return An optional quote matching the example, otherwise empty.
     */
    @Override
    public <S extends Quote> Optional<S> findOne(Example<S> example) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Retrieves all instances that match the provided example.
     *
     * @param example  The example to match.
     * @param pageable The pagination information.
     * @param <S>      The type of the quote.
     * @return A page of quotes matching the example.
     */
    @Override
    public <S extends Quote> Page<S> findAll(Example<S> example, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Counts all instances that match the provided example.
     *
     * @param example The example to match.
     * @param <S>     The type of the quote.
     * @return The count of quotes matching the example.
     */
    @Override
    public <S extends Quote> long count(Example<S> example) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Checks if any instances match the provided example.
     *
     * @param example The example to match.
     * @param <S>     The type of the quote.
     * @return {@code true} if any quote matches the example, otherwise {@code false}.
     */
    @Override
    public <S extends Quote> boolean exists(Example<S> example) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Finds quotes by the provided example and applies a query function to it.
     *
     * @param example        The example to match.
     * @param queryFunction  The function to apply to the matched quotes.
     * @param <S>            The type of the quote.
     * @param <R>            The return type of the query function.
     * @return The result of applying the query function to the matched quotes.
     */
    @Override
    public <S extends Quote, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
        // TODO Auto-generated method stub
        return null;
    }

}