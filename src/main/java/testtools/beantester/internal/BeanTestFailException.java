package testtools.beantester.internal;

/**
 * Java Bean Tester library May 2018
 * GitHub "https://github.com/stuartdd/beanUnitTester"
 * @author stuartdd
 */
public class BeanTestFailException extends RuntimeException {
    public BeanTestFailException(String message) {
        super(message);
    }

    public BeanTestFailException(String message, Throwable cause) {
        super(message, cause);
    }
}
