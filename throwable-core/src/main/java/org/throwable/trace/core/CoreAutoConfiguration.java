package org.throwable.trace.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zjc
 * @version 2016/11/7 0:30
 * @description 核心模块加载, 扫描模块下所有的包
 */
@Configuration
@ComponentScan(
		basePackages = {"org.throwable.trace.core"}
)
public class CoreAutoConfiguration {
}
