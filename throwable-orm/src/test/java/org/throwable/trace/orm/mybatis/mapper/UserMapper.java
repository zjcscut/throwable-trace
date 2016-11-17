package org.throwable.trace.orm.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.throwable.trace.orm.mybatis.api.common.CustomCommonMapper;
import org.throwable.trace.orm.mybatis.entity.User;

/**
 * @author zhangjinci
 * @version 2016/11/15 9:44
 * @function
 */
@Mapper
public interface UserMapper extends CustomCommonMapper<User>{


}
