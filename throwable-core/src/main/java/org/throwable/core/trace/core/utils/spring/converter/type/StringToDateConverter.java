package org.throwable.core.trace.core.utils.spring.converter.type;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.throwable.core.trace.core.utils.date.XDateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @author zhangjinci
 * @version 2016/10/31 15:50
 * @function 字符串转换为java.util.Date转换器
 */
public class StringToDateConverter implements Converter<String, Date> {

    private static final Logger log = LoggerFactory.getLogger(StringToDateConverter.class);

    private static final String[] dateFormats = {
            "EEE, d MMM yyyy HH:mm:ss z",
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd HH:mm:ss.SSSZ",
            "yyyy-MM-dd HH:mm:ssZ",
            "yyyy-MM-dd HH:mm:ss.SSS",
            "yyyy-MM-dd HH:mm:ss"};

    @Override
    public Date convert(String s) {
        Date date = null;
        if (!StringUtils.isBlank(s)) {
            try {
                if (localTimePatternMatch(s)) {
                    if (matchLocalYearMonth(s)){
                         date = XDateUtils.parse(XDateUtils.LOCAL_LONG_YEARMONTH_PATTERN, s);
                    }else if (matchLocalYearMonthDay(s)){
                        date = XDateUtils.parse(XDateUtils.LOCAL_DATE_PATTERN, s);
                    }else if (matchLocalYearMonthDayTime(s)){
                        date = XDateUtils.parse(XDateUtils.LOCAL_LONG_DATETIME_PATTERN, s);
                    }
                } else {
                    date = DateUtils.parseDate(s, dateFormats);
                }
            } catch (ParseException e) {
                String errMsg = String.format("Failed to convert [%s] to [%s] for value '%s'", String.class.toString(), Date.class.toString(), s);
                log.debug(errMsg);
                throw new IllegalArgumentException(errMsg);
            }
        }
        return date;
    }

    private boolean localTimePatternMatch(String source) {
        return matchLocalYearMonth(source)
                || matchLocalYearMonthDay(source)
                || matchLocalYearMonthDayTime(source);
    }

    private boolean matchLocalYearMonth(String source) {
        return source.matches("^\\d{4}年\\d{1,2}月$");
    }

    private boolean matchLocalYearMonthDay(String source) {
        return source.matches("^\\d{4}年\\d{1,2}月\\d{1,2}日$");
    }

    private  boolean matchLocalYearMonthDayTime(String source) {
        return source.matches("^\\d{4}年\\d{1,2}月\\d{1,2}日\\s\\d{1,2}时\\d{1,2}分\\d{1,2}秒$");
    }

}
