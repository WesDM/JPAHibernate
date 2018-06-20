package com.wesdm.JPAHibernateTest.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.hibernate.Session;

/**
 * An interface shared by all business data access objects.
 * <p>
 * All CRUD (create, read, update, delete) basic data access operations are
 * isolated in this interface and shared across all DAO implementations.
 * The current design is for a state-management oriented persistence layer
 * (for example, there is no UPDATE statement function) which provides
 * automatic transactional dirty checking of business objects in persistent
 * state.
 */
public interface GenericDao<T, ID extends Serializable>
    extends Serializable {

    //void joinTransaction();  //for application managed entity manager
	
	EntityManager getEntityManager();

    T findById(ID id);

    T findById(ID id, LockModeType lockModeType);

    T findReferenceById(ID id);

    List<T> findAll();

    Long getCount();

    void makePersistent(T entity);
    
    T makeUpdate(T entity);

    void makeTransient(T entity);

    void checkVersion(T entity, boolean forceUpdate);
    
    Session unwrapSession();
}
