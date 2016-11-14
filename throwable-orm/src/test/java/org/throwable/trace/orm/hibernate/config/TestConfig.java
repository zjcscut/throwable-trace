package org.throwable.trace.orm.hibernate.config;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.throwable.trace.datasource.druid.props.DataSourceProperties;
import org.throwable.trace.orm.hibernate.service.UserService;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @author zjc
 * @version 2016/11/10 0:27
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
public class TestConfig {

	@Test
	public void test1() throws Exception {
		HibernateConfigProperties p = new HibernateConfigProperties();
		p.setMappings_directory_locations("z,a");
		Resource[] cc = p.mappingsDirectoryLocations();
		System.out.println("length:==> " + cc.length);
		assertEquals(cc.length, 2);
	}

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private DataSourceProperties dataSourceProperties;

	@Autowired
	private UserService userService;

	@Test
	public void test2() throws Exception {
		assertEquals(1, dataSourceProperties.getDatasources().size());
		System.out.println("sessionFactory ---- --" + sessionFactory.getStatistics());
	}

	@Test
	public void test3() throws Exception{
		System.out.println(Arrays.toString(userService.selectAll().toArray()));
		System.out.println("1 user--" + userService.findById(1));
	}

}
