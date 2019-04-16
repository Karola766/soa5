package com.app.soa.impl;

import com.app.soa.api.IReaderDAO;
import com.app.soa.api.ILocalReaderDAO;
import com.app.soa.api.IRemoteReaderDAO;
import com.app.soa.api.model.Reader;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Remote(IRemoteReaderDAO.class)
@Local(ILocalReaderDAO.class)
public class ReaderDAO extends BaseDAO<Reader> implements IReaderDAO {

    @PersistenceContext(unitName = "Lab5")
    private EntityManager entityManager;

    public ReaderDAO() {
        super(Reader.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}