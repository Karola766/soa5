package com.app.soa.api;

import java.util.List;

public interface IBaseDAO<T> {

    public T persist(T entity);

    public T edit(T entity);

    public T remove(T entity);

    public List<T> findAll();

    public T find(Integer id);

    public List<T> getFromQuery(String query);
}