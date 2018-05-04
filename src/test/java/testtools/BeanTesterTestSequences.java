package testtools;

import testtools.beantester.ValueSequence;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import testtools.testBeans.TypicalBean;

/**
 * Java Bean Tester library May 2018
 * GitHub "https://github.com/stuartdd/beanUnitTester"
 * @author stuartdd
 */
public class BeanTesterTestSequences {

    Object[] objNone = new Object[]{};
    Object[] objOne = new Object[]{"ONE"};
    Object[] objTwo = new Object[]{"ONE", "TWO"};
    Object[] objFour = new Object[]{"ONE", null, "THREE", null};

    @Test
    public void testValueObj3() {
        ValueSequence valueSequence = new ValueSequence(objFour);
        assertEquals("ONE", valueSequence.create(TypicalBean.class, "had"));
        assertNull(valueSequence.create(TypicalBean.class, "had"));
        assertEquals("THREE", valueSequence.create(TypicalBean.class, "had"));
        assertNull(valueSequence.create(TypicalBean.class, "had"));
        assertEquals("ONE", valueSequence.create(TypicalBean.class, "had"));
        assertNull(valueSequence.create(TypicalBean.class, "had"));
        assertEquals("THREE", valueSequence.create(TypicalBean.class, "had"));
        assertNull(valueSequence.create(TypicalBean.class, "had"));
        assertEquals("ONE", valueSequence.create(TypicalBean.class, "had"));
    }

    @Test
    public void testValueObj2() {
        ValueSequence valueSequence = new ValueSequence(objTwo);
        assertEquals("ONE", valueSequence.create(TypicalBean.class, "had"));
        assertEquals("TWO", valueSequence.create(TypicalBean.class, "had"));
        assertEquals("ONE", valueSequence.create(TypicalBean.class, "had"));
        assertEquals("TWO", valueSequence.create(TypicalBean.class, "had"));
        assertEquals("ONE", valueSequence.create(TypicalBean.class, "had"));
    }

    @Test
    public void testValueSequenceSingle() {
        assertNull((new ValueSequence(null)).create(TypicalBean.class, "had"));
        assertNull((new ValueSequence(objNone)).create(TypicalBean.class, "had"));
        assertEquals("ONE", (new ValueSequence(objOne)).create(TypicalBean.class, "had"));
    }
}
