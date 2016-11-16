package org.throwable.trace.orm.mybatis.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.throwable.trace.orm.mybatis.entity.User;
import org.throwable.trace.orm.mybatis.mapper.UserMapper;
import org.throwable.trace.orm.mybatis.utils.PageBean;

import java.util.List;

/**
 * @author zhangjinci
 * @version 2016/11/15 9:45
 * @function
 */
@Transactional
@Service
public class UserMService {

    @Autowired
    private UserMapper userMapper;

    public PageBean<User> selectAll() {
        PageHelper.startPage(1, 1);
        return new PageBean<>(userMapper.selectAll());
    }

    public User selectById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public void update(User u) {
        userMapper.updateByPrimaryKeySelective(u);
    }
}
