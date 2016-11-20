package org.throwable.trace.core.utils.json;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.throwable.trace.core.exception.JsonParseException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author zjc
 * @version 2016/11/20 12:19
 * @description 提供jackson转换功能
 */
public final class JacksonMapper {

	private final static Logger logger = LoggerFactory.getLogger(JacksonMapper.class);

	private ObjectMapper mapper;

	public JacksonMapper(Include inclusion) {
		mapper = new ObjectMapper();
		//设置输出时包含属性的风格
		mapper.setSerializationInclusion(inclusion);
		//禁用未知属性打断序列化
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		//禁止使用int代表Enum的order()來反序列化Enum
		mapper.disable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
	}

	public JacksonMapper setDataFormat(String dataPattern) {
		this.mapper.setDateFormat(new SimpleDateFormat(dataPattern));
		return this;
	}


	/**
	 * 创建输出全部属性到Json字符串的Mapper.
	 */
	public static JacksonMapper buildNormalMapper() {
		return new JacksonMapper(Include.ALWAYS);
	}

	/**
	 * 创建只输出非空属性到Json字符串的Mapper.
	 */
	public static JacksonMapper buildNonNullMapper() {
		return new JacksonMapper(Include.NON_NULL);
	}

	/**
	 * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper.
	 */
	public static JacksonMapper buildNonEmptyMapper() {
		return new JacksonMapper(Include.NON_EMPTY);
	}

	/**
	 * 创建只输出初始值被改变的属性到Json字符串的Mapper.
	 */
	public static JacksonMapper buildNonDefaultMapper() {
		return new JacksonMapper(Include.NON_DEFAULT);
	}

	/**
	 * 如果对象为Null, 返回"null".
	 * 如果集合为空集合, 返回"[]".
	 */
	public String toJson(Object value) {
		try {
			return mapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			logger.error("process object value to json string fail");
			throw new JsonParseException(e);
		}
	}

	/**
	 * 如果JSON字符串为Null或"null"字符串, 返回Null.
	 * 如果JSON字符串为"[]", 返回空集合.
	 * <p>
	 * 如需读取集合如List/Map, 且不是List<String>这种简单类型时,先使用函數constructParametricType构造类型.
	 *
	 * @see com.fasterxml.jackson.databind.type.TypeFactory
	 */
	public <T> T parse(String jsonString, Class<T> clazz) {
		if (StringUtils.isBlank(jsonString)) {
			return null;
		}
		try {
			return mapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			logger.error(String.format("process json string to class <%s> fail", clazz.getTypeName()));
			throw new JsonParseException(e);
		}
	}

	/**
	 * 如果JSON字符串为Null或"null"字符串, 返回Null.
	 * 需读取集合如List/Map, 且不是List<String>这种简单类型时,先使用函數constructParametricType构造类型.
	 */
	public <T> List<T> parseList(String jsonString, Class<T> clazz) {
		return this.parse(jsonString, List.class, clazz);
	}

	/**
	 * 構造泛型的Type如List<MyBean>, 则调用constructParametricType(ArrayList.class,MyBean.class)
	 * Map<String,MyBean>则调用(HashMap.class,String.class, MyBean.class)
	 */
	public JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
		return mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
	}

	@SuppressWarnings("unchecked")
	public <T> T parse(String jsonString, JavaType javaType) {
		if (StringUtils.isBlank(jsonString)) {
			return null;
		}
		try {
			return (T) mapper.readValue(jsonString, javaType);
		} catch (IOException e) {
			throw new JsonParseException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T parse(String jsonString, Class<?> parametrized, Class<?>... parameterClasses) {
		return (T) this.parse(jsonString, constructParametricType(parametrized, parameterClasses));
	}


	public JsonNode parseNode(String json) {
		try {
			return mapper.readValue(json, JsonNode.class);
		} catch (IOException e) {
			throw new JsonParseException(e);
		}
	}

}
