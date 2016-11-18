package org.throwable.trace.amqp;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangjinci
 * @version 2016/11/18 9:40
 * @function
 */
@Configuration
@ComponentScan(basePackages = {
        "org.throwable.trace.amqp"
})
public class AmqpAutoConfiguration {
}
