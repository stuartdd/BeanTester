package testtools.beantester.internal;

/**
 * Java Bean Tester library May 2018
 * GitHub "https://github.com/stuartdd/beanUnitTester"
 * @author stuartdd
 */
public interface Creator {
    Object create(Class parameterType, String beanName, String propertyName);
}
