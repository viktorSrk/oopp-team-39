package server.api;

import commons.List;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.ListRepository;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class TestListRepository implements ListRepository {

    public final java.util.List<List> lists = new ArrayList<>();
    public final java.util.List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }

    private Optional<List> find(Long id){
        return lists.stream().filter(q -> q.getId().equals(id)).findFirst();
    }

    @Override
    public java.util.List<List> findAll() {
        call("findAll");
        return lists;
    }

    @Override
    public java.util.List<List> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<List> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public java.util.List<List> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(List entity) {
        call("delete");
        lists.remove(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends List> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends List> S save(S entity) {
        call("save");
        entity.setId((long) lists.size());
        if (!lists.contains(entity)) lists.add(entity);
        return entity;
    }

    @Override
    public <S extends List> java.util.List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    public Optional<List> findById(Long id) {
        call("findById");
        List res = null;
        for(List l : lists){
            if(Objects.equals(l.getId(), id)){
                res = l;
                break;
            }
        }
        return Optional.ofNullable(res);
    }

    @Override
    public boolean existsById(Long id) {
        call("existsById");
        return find(id).isPresent();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends List> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends List> java.util.List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<List> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public List getOne(Long aLong) {
        return null;
    }

    @Override
    public List getById(Long id) {
        call("getById");
        return find(id).isPresent() ? find(id).get() : null;
    }

    @Override
    public <S extends List> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends List> java.util.List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends List> java.util.List<S> findAll(Example<S> example, Sort sort) {
        call("findAll");
        return (java.util.List<S>) lists;
    }

    @Override
    public <S extends List> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends List> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends List> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends List, R> R findBy(Example<S> example,
                        Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
