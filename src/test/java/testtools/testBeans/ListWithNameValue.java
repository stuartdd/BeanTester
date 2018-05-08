/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testtools.testBeans;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author stuar
 */
public class ListWithNameValue {
    private String name;
    private long value;
    private List<TypicalBean> beanlist;
    private List<Integer> primitiveList;
    
    private TypicalBean[] beanArray;
    private int[] primitiveArray;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public List<TypicalBean> getBeanlist() {
        return beanlist;
    }

    public void setBeanlist(List<TypicalBean> beanlist) {
        this.beanlist = beanlist;
    }

    public List<Integer> getPrimitiveList() {
        return primitiveList;
    }

    public void setPrimitiveList(List<Integer> primitiveList) {
        this.primitiveList = primitiveList;
    }

    public TypicalBean[] getBeanArray() {
        return beanArray;
    }

    public void setBeanArray(TypicalBean[] beanArray) {
        this.beanArray = beanArray;
    }

    public int[] getPrimitiveArray() {
        return primitiveArray;
    }

    public void setPrimitiveArray(int[] primitiveArray) {
        this.primitiveArray = primitiveArray;
    }

    
}
