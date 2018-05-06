package testtools;

import testtools.beantester.DefaultDelegate;
import testtools.testBeans.TypicalBean;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import testtools.beantester.BeanTester;
import testtools.beantester.ValueSequence;
import testtools.testBeans.ComplexBean;
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
    Long[] intSeq = new Long[]{2L, 4L};

    DefaultDelegate ddWithAnyClass = DefaultDelegate.with("name", "Stuart");
    DefaultDelegate ddWithDefinedClass = DefaultDelegate.with(TypicalBean.class, "name", "Stuart");

    
    @Test void testLists() {
        
    }
    @Test
    public void testIntSequence() {
        StringBuilder sb = new StringBuilder();
        ComplexBean cb = (ComplexBean) BeanTester.testBean(ComplexBean.class, sb, DefaultDelegate.
                with("typicalBean", BeanTester.testBean(TypicalBean.class, sb)).
                and("typicalBeanPrimitive", BeanTester.testBean(TypicalBeanPrimitive.class, sb,
                        DefaultDelegate.with(new String[] {"value","count","index"}, new ValueSequence(intSeq)))));
                System.out.println(sb);
        System.out.println(sb);

        assertEquals(2, cb.getTypicalBeanPrimitive().getCount());
        assertEquals(4, cb.getTypicalBeanPrimitive().getIndex());
        assertEquals(2, cb.getTypicalBeanPrimitive().getValue());
    }

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
