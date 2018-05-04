package testtools.beantester.internal;

import java.lang.reflect.Method;

/**
 * Java Bean Tester library May 2018
 * GitHub "https://github.com/stuartdd/beanUnitTester"
 * @author stuartdd
 */
public class MethodData {
    private final String propertyName;
    private final Method set;
    private final Method get;
    private final Class propertyType;

    public MethodData(Method set, Method get, String propertyName) {
        this.set = set;
        this.get = get;
        this.propertyType = set.getParameterTypes()[0];
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Method getSet() {
        return set;
    }

    public Method getGet() {
        return get;
    }

    public Class getPropertyType() {
        return propertyType;
    }

    @Override
    public String toString() {
        return "MethodPair{" +
                "propertyName='" + propertyName + '\'' +
                ", set=" + set.getName() +
                ", get=" + get.getName() +
                ", propertyType=" + propertyType.getName() +
                '}';
    }
}
