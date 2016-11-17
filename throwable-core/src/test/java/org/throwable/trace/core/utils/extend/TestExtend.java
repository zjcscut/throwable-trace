package org.throwable.trace.core.utils.extend;

import org.junit.Test;

/**
 * @author zjc
 * @version 2016/11/18 0:05
 * @description
 */
public class TestExtend {

	@Test
	public void testResource() throws Exception {
		System.out.println(ResourceUtils.getPropValueByLocation("classpath:application.properties",
				"org.throwable.trace.orm.mybatis.active"));
	}

	@Test
	public void testResources() throws Exception {
		System.out.println(ResourceUtils.getPropValuesByPathPattern("classpath:*.properties",
				"org.throwable.trace.orm.mybatis.active"));
	}

	@Test
	public void testYaml() throws Exception {
		System.out.println(ResourceUtils.getYamlValueByLocation("classpath:aa.yaml",
				"org.throwable.trace.orm.mybatis.active"));
	}

	@Test
	public void testYamls() throws Exception {
		System.out.println(ResourceUtils.getYamlValuesByPathPattern("classpath:*.yaml",
				"org.throwable.trace.orm.mybatis.active"));
	}
}
