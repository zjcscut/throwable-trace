package org.throwable.trace.orm;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangjinci
 * @version 2016/11/15 17:28
 * @function
 */
@Configuration
@ComponentScan(basePackages = {
        "org.throwable.trace.orm"
})
@Mapper
public class OrmAutoConfiguration {
}
