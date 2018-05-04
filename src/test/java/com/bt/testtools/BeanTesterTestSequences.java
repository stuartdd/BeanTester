package com.bt.testtools;

import com.bt.testtools.beantester.ValueSequence;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author stuart
 */
public class BeanTesterTestSequences {

    Object[] objNone = new Object[]{};
    Object[] objOne = new Object[]{"ONE"};
    Object[] objTwo = new Object[]{"ONE", "TWO"};
    Object[] objFour = new Object[]{"ONE", null, "THREE", null};

    @Test
    public void testValueObj3() {
        ValueSequence valueSequence = new ValueSequence(objFour);
        assertEquals("ONE", valueSequence.create(this.getClass(), "Mary", "had"));
        assertNull(valueSequence.create(this.getClass(), "Mary", "had"));
        assertEquals("THREE", valueSequence.create(this.getClass(), "Mary", "had"));
        assertNull(valueSequence.create(this.getClass(), "Mary", "had"));
        assertEquals("ONE", valueSequence.create(this.getClass(), "Mary", "had"));
        assertNull(valueSequence.create(this.getClass(), "Mary", "had"));
        assertEquals("THREE", valueSequence.create(this.getClass(), "Mary", "had"));
        assertNull(valueSequence.create(this.getClass(), "Mary", "had"));
        assertEquals("ONE", valueSequence.create(this.getClass(), "Mary", "had"));
    }

    @Test
    public void testValueObj2() {
        ValueSequence valueSequence = new ValueSequence(objTwo);
        assertEquals("ONE", valueSequence.create(this.getClass(), "Mary", "had"));
        assertEquals("TWO", valueSequence.create(this.getClass(), "Mary", "had"));
        assertEquals("ONE", valueSequence.create(this.getClass(), "Mary", "had"));
        assertEquals("TWO", valueSequence.create(this.getClass(), "Mary", "had"));
        assertEquals("ONE", valueSequence.create(this.getClass(), "Mary", "had"));
    }

    @Test
    public void testValueSequenceSingle() {
        assertNull((new ValueSequence(null)).create(this.getClass(), "Mary", "had"));
        assertNull((new ValueSequence(objNone)).create(this.getClass(), "Mary", "had"));
        assertEquals("ONE", (new ValueSequence(objOne)).create(this.getClass(), "Mary", "had"));
    }
}
