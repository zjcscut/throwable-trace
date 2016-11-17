package org.throwable.trace.core.utils.extend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zjc
 * @version 2016/11/17 23:24
 * @description 资源工具类,直接获取配置文件中的值,适用于Springboot
 * @warn 模式(pathPattern)匹配使用遍历,会有性能问题,尽量在容器初始化阶段调用,不要实时使用
 * @see PathMatchingResourcePatternResolver
 * @see PropertySourceLoader
 */
public final class ResourceUtils {

	private static final Logger log = LoggerFactory.getLogger(ResourceUtils.class);

	private static PathMatchingResourcePatternResolver resolver;

	private static PropertySourceLoader loader;

	/**
	 * propertySourceLoaders缓存
	 * key:pathPattern
	 * value:PropertySourceLoader对象
	 */
	private static final ConcurrentHashMap<String, PropertySourceLoader> propertySourceLoaders = new ConcurrentHashMap<>();

	private static PathMatchingResourcePatternResolver bornResolver() {
		if (resolver == null) {
			return new PathMatchingResourcePatternResolver();
		}
		return resolver;
	}

	/**
	 * 根据ant路径模式查找Resource[]
	 *
	 * @param pathPattern 路径模式
	 * @return Resource[]
	 */
	public static Resource[] getResourcesByPathPattern(String pathPattern) {
		Resource[] resources = null;
		try {
			resources = bornResolver().getResources(pathPattern);
		} catch (IOException e) {
			log.error(String.format("get resources from path:[%s] failed,message:%s", pathPattern, e.getMessage()));
		}
		return resources;
	}

	/**
	 * 根据location查找 Resource
	 *
	 * @param location location
	 * @return Resource
	 */
	public static Resource getResourcesByLocation(String location) {
		Resource resource = null;
		try {
			resource = bornResolver().getResource(location);
		} catch (Exception e) {
			log.error(String.format("get resources from location:[%s] failed,message:%s", location, e.getMessage()));
		}
		return resource;
	}

	/**
	 * 根据location和key查找.propterties Resource中对应的value
	 *
	 * @param location location  e.g  classpath:aa.properties
	 * @param key      key
	 * @return value
	 */
	public static Object getPropValueByLocation(String location, String key) {
		Object value = null;
		loader = propertySourceLoaders.get(location);
		if (loader == null) {
			loader = new PropertiesPropertySourceLoader();
			propertySourceLoaders.putIfAbsent(location, loader);
		}
		Resource resource = getResourcesByLocation(location);
		if (resource != null) {
			try {
				value = loader.load(key, resource, null).getProperty(key);
			} catch (IOException e) {
				log.error(String.format("get resource value from properties file location:[%s] failed,message:%s", location, e.getMessage()));
			}
		}
		return value;
	}

	/**
	 * 根据pathPattern和key查找.properties Resource[]中对应的所有value
	 *
	 * @param pathPattern pathPattern  e.g  classpath:*.properties
	 * @param key         key
	 * @return values
	 */
	public static List<Object> getPropValuesByPathPattern(String pathPattern, String key) {
		List<Object> values = new ArrayList<>();
		loader = propertySourceLoaders.get(pathPattern);
		if (loader == null) {
			loader = new PropertiesPropertySourceLoader();
			propertySourceLoaders.putIfAbsent(pathPattern, loader);
		}
		Resource[] resources = getResourcesByPathPattern(pathPattern);
		for (Resource resource : resources) {
			try {
				PropertySource<?> ps = loader.load(key, resource, null);
				if (ps.containsProperty(key)) {
					values.add(ps.getProperty(key));
				}
			} catch (IOException e) {
				log.error(String.format("get resource values from properties file pathPattern:[%s] failed,message:%s", pathPattern, e.getMessage()));
			}
		}
		return values;
	}

	/**
	 * 根据location和key查找.yaml Resource中对应的value
	 *
	 * @param location location   e.g  classpath:aa.yaml
	 * @param key      key
	 * @return value
	 */
	public static Object getYamlValueByLocation(String location, String key) {
		Object value = null;
		loader = propertySourceLoaders.get(location);
		if (loader == null) {
			loader = new YamlPropertySourceLoader();
			propertySourceLoaders.putIfAbsent(location, loader);
		}
		Resource resource = getResourcesByLocation(location);
		if (resource != null) {
			try {
				value = loader.load(key, resource, null).getProperty(key);
			} catch (IOException e) {
				log.error(String.format("get resource value from yaml file location:[%s] failed,message:%s", location, e.getMessage()));
			}
		}
		return value;
	}

	/**
	 * 根据pathPattern和key查找.yaml Resource[]中对应的所有value
	 *
	 * @param pathPattern pathPattern  e.g  classpath:*.yaml
	 * @param key         key
	 * @return values
	 */
	public static List<Object> getYamlValuesByPathPattern(String pathPattern, String key) {
		List<Object> values = new ArrayList<>();
		loader = propertySourceLoaders.get(pathPattern);
		if (loader == null) {
			loader = new YamlPropertySourceLoader();
			propertySourceLoaders.putIfAbsent(pathPattern, loader);
		}
		Resource[] resources = getResourcesByPathPattern(pathPattern);
		for (Resource resource : resources) {
			try {
				PropertySource<?> ps = loader.load(key, resource, null);
				if (ps.containsProperty(key)) {
					values.add(ps.getProperty(key));
				}
			} catch (IOException e) {
				log.error(String.format("get resource values from yaml file pathPattern:[%s] failed,message:%s", pathPattern, e.getMessage()));
			}
		}
		return values;
	}

}
