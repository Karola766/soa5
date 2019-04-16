package com.app.soa.impl;

import com.app.soa.api.*;
import com.app.soa.api.model.Rental;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Remote(IRemoteRentalDAO.class)
@Local(ILocalRentalDAO.class)
public class RentalDAO extends BaseDAO<Rental> implements IRentalDAO {

    @PersistenceContext(unitName = "Lab5")
    private EntityManager entityManager;

    public RentalDAO() {
        super(Rental.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}