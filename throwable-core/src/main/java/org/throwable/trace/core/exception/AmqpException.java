package org.throwable.trace.core.exception;

/**
 * @author zhangjinci
 * @version 2016/11/23 15:30
 * @function Amqp异常
 */
public class AmqpException extends RuntimeException{

    public AmqpException(String message) {
        super(message);
    }

    public AmqpException(String message, Throwable cause) {
        super(message, cause);
    }

    public AmqpException(Throwable cause) {
        super(cause);
    }


}
