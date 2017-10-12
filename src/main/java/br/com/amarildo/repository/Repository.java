package br.com.amarildo.repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class Repository<T> implements IRepository<T> {

    protected final EntityManager manager;
    private final Class<T> clazz;

    @SuppressWarnings("unchecked")
    @Inject
    public Repository(EntityManager manager) {
        this.manager = manager;
        this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public T porId(Long id) {
        return manager.find(clazz, id);
    }

    public T guardar(T entity) {
        return this.manager.merge(entity);
    }

    public void alterar(T entity) {
        this.manager.merge(entity);
    }

    public void remover(T entity) {
        this.manager.remove(entity);
    }

    public List<T> todos() {
        TypedQuery<T> query = manager.createQuery("from " + clazz.getName(), clazz);
        List<T> resultList = query.getResultList();
        return resultList;
    }
    
    public List<T> todas() {
        TypedQuery<T> query = manager.createQuery("from " + clazz.getName(), clazz);
        List<T> resultList = query.getResultList();
        return resultList;
    }

}