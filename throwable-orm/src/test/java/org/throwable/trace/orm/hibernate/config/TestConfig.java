package org.throwable.trace.orm.hibernate.config;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.junit.Assert.*;

/**
 * @author zjc
 * @version 2016/11/10 0:27
 * @description
 */
public class TestConfig {

	@Test
	public void test1()throws Exception{
		HibernateConfigProperties p = new HibernateConfigProperties();
        p.setMappings_directory_locations("z,a");
		Resource[] cc = p.mappingsDirectoryLocations();
		System.out.println("length:==> " +cc.length);
		assertEquals(cc.length, 2);
	}
}
