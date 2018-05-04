package com.bt.testtools.beantester.internal;

public class BeanTestFailException extends RuntimeException {
    public BeanTestFailException(String message) {
        super(message);
    }

    public BeanTestFailException(String message, Throwable cause) {
        super(message, cause);
    }
}
