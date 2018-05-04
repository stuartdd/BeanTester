package testtools.beantester;

import testtools.beantester.internal.Creator;
import java.util.HashMap;
import java.util.Map;

/**
 * Java Bean Tester library May 2018
 * GitHub "https://github.com/stuartdd/beanUnitTester"
 * @author stuartdd
 */
public class DefaultDelegate implements Creator {

    private final Map<String, Object> map = new HashMap<>();

    public static DefaultDelegate with(String propertyName, Object valueOrCreator) {
        DefaultDelegate s = new DefaultDelegate();
        return s.addValueOrCreator(null, new String[]{propertyName}, valueOrCreator);
    }

    public static DefaultDelegate with(Class classUnderTest, String propertyName, Object valueOrCreator) {
        DefaultDelegate s = new DefaultDelegate();
        return s.addValueOrCreator(classUnderTest, new String[]{propertyName}, valueOrCreator);
    }

    public static DefaultDelegate with(String[] propertyNameList, Object valueOrCreator) {
        DefaultDelegate s = new DefaultDelegate();
        return s.addValueOrCreator(null, propertyNameList, valueOrCreator);
    }

    public static DefaultDelegate with(Class classUnderTest, String[] propertyNameList, Object valueOrCreator) {
        DefaultDelegate s = new DefaultDelegate();
        return s.addValueOrCreator(classUnderTest, propertyNameList, valueOrCreator);
    }

    public DefaultDelegate and(String propertyName, Object valueOrCreator) {
        return addValueOrCreator(null, new String[]{propertyName}, valueOrCreator);
    }

    public DefaultDelegate and(Class classUnderTest, String propertyName, Object valueOrCreator) {
        return addValueOrCreator(classUnderTest, new String[]{propertyName}, valueOrCreator);
    }

    public DefaultDelegate and(String[] propertyNameList, Object valueOrCreator) {
        return addValueOrCreator(null, propertyNameList, valueOrCreator);
    }

    public DefaultDelegate and(Class classUnderTest, String[] propertyNameList, Object valueOrCreator) {
        return addValueOrCreator(classUnderTest, propertyNameList, valueOrCreator);
    }

    protected DefaultDelegate addValueOrCreator(Class classUnderTest, String[] propertyNameList, Object valueOrCreator) {
        for (String propertyName : propertyNameList) {
            map.put(genPropertyName(classUnderTest, propertyName), valueOrCreator);
        }
        return this;
    }

    @Override
    public Object create(Class classUnderTest, String propertyName) {
        Object valueOrCreator = map.get(genPropertyName(classUnderTest, propertyName));
        if (valueOrCreator == null) {
            valueOrCreator = map.get(propertyName);
        }
        return getFinalCreatedValue(valueOrCreator, classUnderTest, propertyName);
    }

    private Object getFinalCreatedValue(Object valueOrCreator, Class classUnderTest, String propertyName) {
        if (valueOrCreator == null) {
            return null;
        }
        if (valueOrCreator instanceof Creator) {
            return getFinalCreatedValue(((Creator) valueOrCreator).create(classUnderTest, propertyName), classUnderTest, propertyName);
        }
        return valueOrCreator;
    }
    
    private String genPropertyName(Class classUnderTest, String propertyName) {
        return (classUnderTest == null ? "" : classUnderTest.getSimpleName() + (propertyName == null?"":".")) + (propertyName == null?"":propertyName);
    }
}
