package com.bt.testtools.beantester.internal;

import java.lang.reflect.Method;

public class MethodPair {
    private String propertyName;
    private Method set;
    private Method get;
    private Class propertyType;

    public MethodPair(Method set, Method get, String propertyName) {
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
