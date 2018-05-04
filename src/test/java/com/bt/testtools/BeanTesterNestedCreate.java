package com.bt.testtools;

import com.bt.testtools.beantester.DefaultDelegate;
import com.bt.testtools.testBeans.TypicalBean;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author stuart
 */
public class BeanTesterNestedCreate {

    Object[] objNone = new Object[]{};
    Object[] objOne = new Object[]{"ONE"};
    Object[] objTwo = new Object[]{"ONE", "TWO"};
    Object[] objFour = new Object[]{"ONE", null, "THREE", null};

    DefaultDelegate ddOne = DefaultDelegate.with("prOne", "strOne"); 
    DefaultDelegate ddTwo = DefaultDelegate.with(TypicalBean.class.getSimpleName(), "name", "Stuart"); 
    
    @Test
    public void testCreateTypicalBean() {
        assertNull(ddOne.create(null, null, null));
        assertNull(ddOne.create(TypicalBean.class, null, null));
        assertNull(ddOne.create(TypicalBean.class, "fred", null));
        assertNull(ddOne.create(TypicalBean.class, "fred", "name"));
    }    
    
    @Test
    public void testCreateNull() {
        assertNull(ddOne.create(null, null, null));
        assertNull(ddOne.create(TypicalBean.class, null, null));
        assertNull(ddOne.create(TypicalBean.class, "fred", null));
        assertNull(ddOne.create(TypicalBean.class, "fred", "name"));
    }
}
