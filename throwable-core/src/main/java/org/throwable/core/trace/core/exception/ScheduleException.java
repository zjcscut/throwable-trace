package org.throwable.core.trace.core.exception;

/**
 * @author zjc
 * @version 2016/11/6 10:44
 * @description 调度模块异常
 */
public class ScheduleException extends RuntimeException {

	public ScheduleException(String message) {
		super(message);
	}

	public ScheduleException(String message, Throwable cause) {
		super(message, cause);
	}

	public ScheduleException(Throwable cause) {
		super(cause);
	}

	protected ScheduleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
