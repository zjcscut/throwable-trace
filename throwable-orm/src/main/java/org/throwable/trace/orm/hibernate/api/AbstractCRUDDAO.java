package org.throwable.trace.orm.hibernate.api;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhangjinci
 * @version 2016/10/28 11:58
 * @function 抽象CRUD通用DAO
 */
@SuppressWarnings("unchecked")
public abstract class AbstractCRUDDAO<T> {

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    protected abstract Class<T> getEntityClass();

    public Long saveL(T t) {
        return (Long) getCurrentSession().save(t);
    }

    public Integer save(T t) {
        return (Integer) getCurrentSession().save(t);
    }

    public T saveT(T t) {   //这个返回的实例会带上主键id
        getCurrentSession().save(t);
        return t;
    }

    public void saveOrUpdate(T t) {
        getCurrentSession().saveOrUpdate(t);
    }

    public void update(T t) {
        getCurrentSession().update(t);
    }

    public T fetch(Serializable id) {
        return getCurrentSession().get(getEntityClass(), id);
    }

    public List<T> findAll() {
        return getCurrentSession().createCriteria(getEntityClass()).list();
    }
}
