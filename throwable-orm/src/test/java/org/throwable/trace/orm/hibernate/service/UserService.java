package org.throwable.trace.orm.hibernate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.throwable.trace.orm.hibernate.dao.UserDAO;
import org.throwable.trace.orm.hibernate.entity.User;

import java.util.List;

/**
 * @author zhangjinci
 * @version 2016/11/14 11:15
 * @function
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public List<User> selectAll() {
        return userDAO.findAll();
    }

    public User findById(Integer id) {
        return userDAO.fetch(id);
    }

    public void saveUser(User u) {
        userDAO.save(u);
    }

    public void delete(User u) {
        userDAO.delete(u);
    }
}
