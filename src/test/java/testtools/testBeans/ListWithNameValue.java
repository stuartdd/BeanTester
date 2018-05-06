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
    private List<TypicalBean> list = new ArrayList<>();

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

    public List<TypicalBean> getList() {
        return list;
    }

    public void setList(List<TypicalBean> list) {
        this.list = list;
    }
    
    
}
