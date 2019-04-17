package com.app.soa.impl;

import com.app.soa.api.IAuthorDAO;
import com.app.soa.api.ILocalAuthorDAO;
import com.app.soa.api.IRemoteAuthorDAO;
import com.app.soa.api.model.Author;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Singleton
@Remote(IRemoteAuthorDAO.class)
@Local(ILocalAuthorDAO.class)
public class AuthorDAO extends BaseDAO<Author> implements IAuthorDAO {

    @PersistenceContext(unitName = "Lab5")
    private EntityManager entityManager;

    public AuthorDAO() {
        super(Author.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}