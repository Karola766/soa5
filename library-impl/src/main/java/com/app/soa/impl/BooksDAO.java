package com.app.soa.impl;

import com.app.soa.api.IBooksDAO;
import com.app.soa.api.ILocalBooksDAO;
import com.app.soa.api.IRemoteBooksDAO;
import com.app.soa.api.model.Book;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Remote(IRemoteBooksDAO.class)
@Local(ILocalBooksDAO.class)
public class BooksDAO extends BaseDAO<Book> implements IBooksDAO {

    @PersistenceContext(unitName = "Lab5")
    private EntityManager entityManager;

    public BooksDAO() {
        super(Book.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}