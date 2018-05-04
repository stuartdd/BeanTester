package testtools.beantester.internal;

/**
 * Java Bean Tester library May 2018
 * GitHub "https://github.com/stuartdd/beanUnitTester"
 * @author stuartdd
 */
public class BeanTesterException extends RuntimeException {
    public BeanTesterException(String message) {
        super(message);
    }

    public BeanTesterException(String message, Throwable cause) {
        super(message, cause);
    }
}
