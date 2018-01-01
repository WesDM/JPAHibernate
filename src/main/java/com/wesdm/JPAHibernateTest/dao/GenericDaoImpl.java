package com.wesdm.JPAHibernateTest.dao;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

public abstract class GenericDaoImpl<T, ID extends Serializable>
    implements GenericDao<T, ID> {

    @PersistenceContext
    protected EntityManager em;

    protected final Class<T> entityClass;

    protected GenericDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    public EntityManager getEntityManager() {
    	return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public T findById(ID id) {
        return findById(id, LockModeType.NONE);
    }

    public T findById(ID id, LockModeType lockModeType) {
        return em.find(entityClass, id, lockModeType);
    }

    public T findReferenceById(ID id) {
        return em.getReference(entityClass, id);
    }

    public List<T> findAll() {
        CriteriaQuery<T> c =
            em.getCriteriaBuilder().createQuery(entityClass);
        c.select(c.from(entityClass));
        return em.createQuery(c).getResultList();
    }

    public Long getCount() {
        CriteriaQuery<Long> c =
           em.getCriteriaBuilder().createQuery(Long.class);
        c.select(em.getCriteriaBuilder().count(c.from(entityClass)));
        return em.createQuery(c).getSingleResult();
    }

    public void makePersistent(T instance) {
        // merge() handles transient AND detached instances
        em.persist(instance);
    }
    
    public T makeUpdate(T instance) {
    	return em.merge(instance);
    }

    public void makeTransient(T instance) {
        em.remove(instance);
    }

    public void checkVersion(T entity, boolean forceUpdate) {
        em.lock(
            entity,
            forceUpdate
                ? LockModeType.OPTIMISTIC_FORCE_INCREMENT
                : LockModeType.OPTIMISTIC
        );
    }
}


