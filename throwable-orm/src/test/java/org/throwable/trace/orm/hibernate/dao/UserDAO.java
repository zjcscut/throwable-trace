package org.throwable.trace.orm.hibernate.dao;

import org.springframework.stereotype.Repository;
import org.throwable.trace.orm.hibernate.api.AbstractCRUDDAO;
import org.throwable.trace.orm.hibernate.entity.User;

/**
 * @author zhangjinci
 * @version 2016/11/14 11:15
 * @function
 */
@Repository
public class UserDAO extends AbstractCRUDDAO<User> {

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
}
