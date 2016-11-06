package org.throwable.core.trace.core.exception;

/**
 * @author zjc
 * @version 2016/11/6 10:43
 * @description 数据源相关的异常
 */
public class DataSourceException extends RuntimeException {

	public DataSourceException(String message) {
		super(message);
	}

	public DataSourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataSourceException(Throwable cause) {
		super(cause);
	}

	protected DataSourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
