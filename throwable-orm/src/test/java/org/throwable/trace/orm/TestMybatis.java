package org.throwable.trace.orm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.throwable.trace.orm.main.Application;
import org.throwable.trace.orm.mybatis.entity.User;
import org.throwable.trace.orm.mybatis.service.UserMService;

import java.util.Arrays;

/**
 * @author zhangjinci
 * @version 2016/11/15 9:50
 * @function
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
public class TestMybatis {

    @Autowired
    private UserMService userMService;

    @Test
    public void Test1() {
        System.out.println("users--> " + Arrays.toString(userMService.selectAll().toArray()));
        User u = userMService.selectById(1);
        u.setEmail("55545455");
        userMService.update(u);
    }
}
