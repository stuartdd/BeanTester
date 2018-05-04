package com.bt.testtools.testBeans;

public class GetMethodNotImplemented {
    private String name;
    private String value;

    public String getName()  {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
