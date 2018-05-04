package com.bt.testtools.testBeans;

public class ComplexBean {
    private final StringBuilder log = new StringBuilder();
    private String name;
    private char character;
    private TypicalBean typicalBean;
    private TypicalBeanPrimitive typicalBeanPrimitive;

    public String getName() {
        log.append(name == null ? "null-" : "").append("getName()|");
        return name;
    }

    public void setName(String name) {
        log.append("setName(").append(name == null ? "null-" : "").append(")|");
        this.name = name;
    }

    public TypicalBean getTypicalBean() {
        log.append(typicalBean == null ? "null-" : "").append("getTypicalBean()|");
        return typicalBean;
    }

    public void setTypicalBean(TypicalBean typicalBean) {
        log.append("setTypicalBean(").append(typicalBean == null ? "null-" : "").append(")|");
        this.typicalBean = typicalBean;
    }

    public TypicalBeanPrimitive getTypicalBeanPrimitive() {
        log.append(typicalBeanPrimitive == null ? "null-" : "").append("getTypicalBeanPrimitive()|");
        return typicalBeanPrimitive;
    }

    public void setTypicalBeanPrimitive(TypicalBeanPrimitive typicalBeanPrimitive) {
        log.append("setTypicalBeanPrimitive(").append(typicalBeanPrimitive == null ? "null-" : "").append(")|");
        this.typicalBeanPrimitive = typicalBeanPrimitive;
    }

    @Override
    public String toString() {
        return "ComplexBean{" + log + "}\n   " + typicalBean.toString() + "\n   " + typicalBeanPrimitive.toString();
    }

}
