package com.app.soa.impl;

import com.app.soa.api.IBaseDAO;
import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public abstract class BaseDAO<T> implements IBaseDAO<T> {

    private Class<T> entityClass;

    public BaseDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public BaseDAO() {
    }

    protected abstract EntityManager getEntityManager();

    public T persist(T entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    public T edit(T entity) {
        getEntityManager().merge(entity);
        return entity;
    }

    public T remove(T entity) {
        getEntityManager().remove(entity);
        return entity;
    }

    public List<T> findAll() {
        Query q = getEntityManager().createQuery("SELECT e FROM " + entityClass.getName()
                + " e");
        List<T> list = (List<T>) q.getResultList();
        return list;
    }

    public T find(Integer id) {
        T e = getEntityManager().find(entityClass, id);
        return e;
    }

    public List<T> getFromQuery(String query){
        Query q = getEntityManager().createQuery(query);
        List<T> list = (List<T>) q.getResultList();
        return list;
    }

}