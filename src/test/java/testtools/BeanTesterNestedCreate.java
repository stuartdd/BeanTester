package testtools;

import testtools.beantester.DefaultDelegate;
import testtools.testBeans.TypicalBean;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import testtools.testBeans.TypicalBeanPrimitive;

/**
 * Java Bean Tester library May 2018 GitHub
 * "https://github.com/stuartdd/beanUnitTester"
 *
 * @author stuartdd
 */
public class BeanTesterNestedCreate {

    Object[] objNone = new Object[]{};
    Object[] objOne = new Object[]{"ONE"};
    Object[] objTwo = new Object[]{"ONE", "TWO"};
    Object[] objFour = new Object[]{"ONE", null, "THREE", null};

    DefaultDelegate ddWithAnyClass = DefaultDelegate.with("name", "Stuart");
    DefaultDelegate ddWithDefinedClass = DefaultDelegate.with(TypicalBean.class, "name", "Stuart");

    @Test
    public void testCreateTypicalBean() {
        assertEquals("Stuart", ddWithDefinedClass.create(TypicalBean.class, "name"));
        assertEquals("Stuart", ddWithAnyClass.create(TypicalBeanPrimitive.class, "name"));
        assertNull(ddWithDefinedClass.create(TypicalBeanPrimitive.class, "name"));
        assertNull(ddWithDefinedClass.create(null, "name"));
        assertEquals("Stuart", ddWithAnyClass.create(null, "name"));
    }

    @Test
    public void testCreateNullTwo() {
        assertNull(ddWithDefinedClass.create(null, null));
        assertNull(ddWithDefinedClass.create(TypicalBean.class, null));
    }

    @Test
    public void testCreateNullOne() {
        assertNull(ddWithAnyClass.create(null, null));
        assertNull(ddWithAnyClass.create(TypicalBean.class, null));
    }
}
