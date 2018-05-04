package testtools.beantester.internal;

public class BeanTesterException extends RuntimeException {
    public BeanTesterException(String message) {
        super(message);
    }

    public BeanTesterException(String message, Throwable cause) {
        super(message, cause);
    }
}
