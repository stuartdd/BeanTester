package com.bt.testtools.testBeans;

public class ReturnIncorrectNameFromGet {
    private String name;
    private String value;

    public String getName() {
        return "ABCDE";
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
