package org.throwable.trace.core.exception;

/**
 * @author zjc
 * @version 2016/11/14 22:58
 * @description orm 配置异常
 */
public class OrmConfigException extends RuntimeException {

	public OrmConfigException() {
	}

	public OrmConfigException(String message) {
		super(message);
	}

	public OrmConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public OrmConfigException(Throwable cause) {
		super(cause);
	}
}
