package com.app.soa.impl;

import com.app.soa.api.*;
import com.app.soa.api.model.NewBook;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Remote(IRemoteNewBookDAO.class)
@Local(ILocalNewBookDAO.class)
public class NewBookDAO extends BaseDAO<NewBook> implements INewBookDAO {

    @PersistenceContext(unitName = "Lab5")
    private EntityManager entityManager;

    public NewBookDAO() {
        super(NewBook.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}