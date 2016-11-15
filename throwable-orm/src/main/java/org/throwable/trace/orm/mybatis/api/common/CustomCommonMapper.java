package org.throwable.trace.orm.mybatis.api.common;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author zhangjinci
 * @version 2016/11/15 9:29
 * @function 自定义的通用mapper,继承于 Mapper接口,具备继承功能
 * 业务mapper可以继承于此mapper
 */
public interface CustomCommonMapper<T> extends Mapper<T> {
}
