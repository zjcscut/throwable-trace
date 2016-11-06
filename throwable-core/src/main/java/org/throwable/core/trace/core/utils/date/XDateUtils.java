package org.throwable.core.trace.core.utils.date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.throwable.core.trace.core.utils.extend.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangjinci
 * @version 2016/11/2 15:20
 * @function 日期工具类,改名为了避免和Apache common-lang的工具类冲突
 */
public class XDateUtils {

	private static final Logger log = LoggerFactory.getLogger(XDateUtils.class);

	public static final String LONG_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String TIME_PATTERN = "HH:mm:ss";
	public static final String LOCAL_LONG_DATETIME_PATTERN = "yyyy年MM月dd日 HH时mm分ss秒";
	public static final String LOCAL_LONG_YEARMONTH_PATTERN = "yyyy年MM月";
	public static final String LOCAL_DATE_PATTERN = "yyyy年MM月dd日";
	public static final String LOCAL_TIME_PATTERN = "HH时mm分ss秒";

	//key为pattern,value为SimpleDateFormat实例
	private static final ConcurrentHashMap<String, SimpleDateFormat> dateFormatCachePool = new ConcurrentHashMap<>();

	public static SimpleDateFormat getInstance(String pattern) {
		SimpleDateFormat dateFormat = dateFormatCachePool.get(pattern);
		if (dateFormat == null) {
			dateFormat = new SimpleDateFormat(pattern);
			dateFormatCachePool.put(pattern, dateFormat);
		}
		return dateFormat;
	}

	public static Date parse(String pattern, String source) {
		Assert.notBlank(source, "source converter to <java.util.Date> must not be blank");
		Date date = null;
		try {
			if (StringUtils.isBlank(pattern)) {
				date = getInstance(LONG_DATETIME_PATTERN).parse(source);
			} else {
				date = getInstance(pattern).parse(source);
			}
		} catch (ParseException e) {
			log.error(String.format("parse %s to java.util.Date with pattern <%s> failed,message:%s", source, pattern, e.getMessage()));

		}
		return date;
	}


}
