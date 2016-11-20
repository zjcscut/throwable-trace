package org.throwable.trace.core.exception;

/**
 * @author zjc
 * @version 2016/11/20 12:37
 * @description json解析异常
 */
public class JsonParseException extends RuntimeException {

	public JsonParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public JsonParseException(Throwable cause) {
		super(cause);
	}

	protected JsonParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
