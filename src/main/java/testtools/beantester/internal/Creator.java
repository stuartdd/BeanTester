package testtools.beantester.internal;

public interface Creator {
    Object create(Class parameterType, String beanName, String propertyName);
}
