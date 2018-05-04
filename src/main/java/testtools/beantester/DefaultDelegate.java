package testtools.beantester;

import testtools.beantester.internal.Creator;
import java.util.HashMap;
import java.util.Map;

public class DefaultDelegate implements Creator {

    private final Map<String, Object> map = new HashMap<>();

    public static DefaultDelegate with(String propertyName, Object valueOrCreator) {
        DefaultDelegate s = new DefaultDelegate();
        return s.addValueOrCreator(null, new String[]{propertyName}, valueOrCreator);
    }

    public static DefaultDelegate with(String beanName, String propertyName, Object valueOrCreator) {
        DefaultDelegate s = new DefaultDelegate();
        return s.addValueOrCreator(beanName, new String[]{propertyName}, valueOrCreator);
    }

    public static DefaultDelegate with(String[] propertyName, Object valueOrCreator) {
        DefaultDelegate s = new DefaultDelegate();
        return s.addValueOrCreator(null, propertyName, valueOrCreator);
    }

    public static DefaultDelegate with(String beanName, String[] propertyName, Object valueOrCreator) {
        DefaultDelegate s = new DefaultDelegate();
        return s.addValueOrCreator(beanName, propertyName, valueOrCreator);
    }

    public DefaultDelegate and(String propertyName, Object valueOrCreator) {
        return addValueOrCreator(null, new String[]{propertyName}, valueOrCreator);
    }

    public DefaultDelegate and(String beanName, String propertyName, Object valueOrCreator) {
        return addValueOrCreator(beanName, new String[]{propertyName}, valueOrCreator);
    }

    public DefaultDelegate and(String[] propertyName, Object valueOrCreator) {
        return addValueOrCreator(null, propertyName, valueOrCreator);
    }

    public DefaultDelegate and(String beanName, String[] propertyName, Object valueOrCreator) {
        return addValueOrCreator(beanName, propertyName, valueOrCreator);
    }

    protected DefaultDelegate addValueOrCreator(String beanName, String[] propertyName, Object valueOrCreator) {
        for (String p : propertyName) {
            map.put((beanName == null ? "" : beanName + '.') + p, valueOrCreator);
        }
        return this;
    }

    @Override
    public Object create(Class parameterType, String beanName, String propertyName) {
        Object valueOrCreator = map.get((beanName == null ? "" : beanName + '.') + propertyName);
        if (valueOrCreator == null) {
            valueOrCreator = map.get(propertyName);
        }
        return getFinalCreatedValue(valueOrCreator, parameterType, beanName, propertyName);
    }

    private Object getFinalCreatedValue(Object valueOrCreator, Class parameterType, String beanName, String propertyName) {
        if (valueOrCreator == null) {
            return null;
        }
        if (valueOrCreator instanceof Creator) {
            return getFinalCreatedValue(((Creator) valueOrCreator).create(parameterType, beanName, propertyName), parameterType, beanName, propertyName);
        }
        return valueOrCreator;
    }
}
