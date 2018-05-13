package testtools;

import testtools.beantester.internal.BeanTestFailException;
import testtools.beantester.BeanTester;
import testtools.beantester.internal.BeanTesterException;
import testtools.beantester.DefaultDelegate;
import testtools.beantester.internal.MethodData;

import java.util.Map;

import testtools.testBeans.*;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.fail;

/**
 * Java Bean Tester library May 2018
 * GitHub "https://github.com/stuartdd/beanUnitTester"
 * @author stuartdd
 */
public class BeanTesterTest {

    @Test
    public void testComplexBeanWithInvalidDelegate() {
        StringBuilder sb = new StringBuilder();
        final TypicalBeanPrimitive typicalBeanPrimitive = new TypicalBeanPrimitive();
        typicalBeanPrimitive.setName("12345");
        typicalBeanPrimitive.setValue(-999999999);

        try {
            BeanTester.testBean(ComplexBean.class, sb,
                    DefaultDelegate.with(TypicalBean.class, "value", "222222222222222").and("typicalBean", typicalBeanPrimitive)
            );
            fail("Must throw an Exception");
        } catch (BeanTestFailException bte) {
            assertEquals("Cannot invoke setTypicalBean(class testtools.testBeans.TypicalBean) where parameter is :testtools.testBeans.TypicalBeanPrimitive", bte.getMessage());
        } catch (Throwable any) {
            fail("Threw an :" + any.getClass().getName() + " exception. Message is :" + any.getMessage());
        }
        System.out.println(sb);
    }

    @Test
    public void testComplexBeanWithDelegate() {
        StringBuilder sb = new StringBuilder();
        final TypicalBeanPrimitive typicalBeanPrimitive = new TypicalBeanPrimitive();
        typicalBeanPrimitive.setName("12345");
        typicalBeanPrimitive.setValue(-999999999);
        ComplexBean complexBean = (ComplexBean) BeanTester.testBean(ComplexBean.class, sb,
                DefaultDelegate.with(TypicalBean.class, "value", "222222222222222").and("typicalBeanPrimitive", typicalBeanPrimitive)
        );
        System.out.println(sb);
        assertEquals("ComplexBean{setName()|getName()|setTypicalBean()|getTypicalBean()|setTypicalBeanPrimitive()|getTypicalBeanPrimitive()|}\n"
                + "   TypicalBean{setName()|getName()|setValue()|getValue()|}\n"
                + "   TypicalBeanPrimitive{setName()|setValue()|}", complexBean.toString());
    }

    @Test
    public void testComplexBean() {
        ComplexBean complexBean = (ComplexBean) BeanTester.testBean(ComplexBean.class);
        assertEquals("ComplexBean{setName()|getName()|setTypicalBean()|getTypicalBean()|setTypicalBeanPrimitive()|getTypicalBeanPrimitive()|}\n"
                + "   TypicalBean{setName()|getName()|setValue()|getValue()|}\n"
                + "   TypicalBeanPrimitive{setCount()|getCount()|setIndex()|getIndex()|setName()|getName()|setValue()|getValue()|}", complexBean.toString());
    }

    @Test
    public void testProblemBooleans() {
        ProblemBooleansWithGetter problemBooleans = (ProblemBooleansWithGetter) BeanTester.testBean(ProblemBooleansWithGetter.class);
        assertEquals("ProblemBooleansWithGetter{setBooleanObject()|getBooleanObject()|setBooleanPrimitive()|getBooleanPrimitive()|}", problemBooleans.toString());
    }

    @Test
    public void testAllPrimitives() {
        TypicalBeanAllPrimitives typicalBeanAllPrimitives = (TypicalBeanAllPrimitives) BeanTester.testBean(TypicalBeanAllPrimitives.class);
        assertEquals("TypicalBeanAllPrimitives{setBooleanObject()|getBooleanObject()|setBooleanPrimitive()|isBooleanPrimitive()|setByteObject()|getByteObject()|setBytePrimitive()|getBytePrimitive()|setCharObject()|getCharObject()|setCharPrimitive()|getCharPrimitive()|setDoubleObject()|getDoubleObject()|setDoublePrimitive()|getDoublePrimitive()|setFloatObject()|getFloatObject()|setFloatPrimitive()|getFloatPrimitive()|setIntObject()|getIntObject()|setIntPrimitive()|getIntPrimitive()|setLongObject()|getLongObject()|setLongPrimitive()|getLongPrimitive()|setName()getName()setShortObject()|getShortObject()|setShortPrimitive()|getShortPrimitive()|}", typicalBeanAllPrimitives.toString());
    }

    @Test
    public void testBeanFail() {
        assertTestFailed(SetMethodNotImplemented.class, "Cannot invoke setName(");
        assertFailed(GetMethodNotImplemented.class, "Cannot invoke getName(");
        assertTestFailed(ReturnIncorrectNameFromGet.class, "returned 'ABCDE'. Should return 'Str:");
        assertTestFailed(ReturnIncorrectTypeFromGet.class, "Returned java.lang.Integer. Should return: java.lang.String");
        assertTestFailed(ReturnNullFromGet.class, "FAIL:Class:java.lang.String: Method:getName()  returned null");
        assertFailed(NotABean.class, "Cannot instantiate bean using default constructor");
    }

    private void assertFailed(Class clazz, String m) {
        try {
            BeanTester.testBean(clazz);
            fail("Must throw an Exception");
        } catch (BeanTesterException bte) {
            if (!bte.getMessage().contains(m)) {
                fail("Exception message does not contain [" + m + "]. It is [" + bte.getMessage() + "]");
            }
        } catch (Throwable any) {
            fail("Threw an :" + any.getClass().getName() + " exception. Message is :" + any.getMessage());
        }
    }

    private void assertTestFailed(Class clazz, String m) {
        try {
            BeanTester.testBean(clazz);
            fail("Must throw an Exception");
        } catch (BeanTestFailException btfe) {
            if (!btfe.getMessage().contains(m)) {
                fail("Exception message does not contain [" + m + "]. It is [" + btfe.getMessage() + "]");
            }
        } catch (Throwable any) {
            fail("Threw an :" + any.getClass().getName() + " exception. Message is :" + any.getMessage());
        }
    }

    @Test
    public void testBeanPass() {
        TypicalBeanPrimitive typicalBeanPrimitive = (TypicalBeanPrimitive) BeanTester.testBean(TypicalBeanPrimitive.class);
        assertEquals("TypicalBeanPrimitive{setCount()|getCount()|setIndex()|getIndex()|setName()|getName()|setValue()|getValue()|}", typicalBeanPrimitive.toString());
        TypicalBean typicalBean = (TypicalBean) BeanTester.testBean(TypicalBean.class);
        assertEquals("TypicalBean{setName()|getName()|setValue()|getValue()|}", typicalBean.toString());
    }

    @Test
    public void testBeanWithObjects() {
        Map<String, MethodData> methods = BeanTester.getPairs(TypicalBean.class);
        for (MethodData mp : methods.values()) {
            echo(mp);
        }
        assertEquals(2, methods.size());
        assertNotNull(methods.get("name"));
        assertNotNull(methods.get("value"));
        assertEquals(String.class.getName(), methods.get("name").getPropertyType().getName());
        assertEquals(String.class.getName(), methods.get("value").getPropertyType().getName());
    }

    @Test
    public void testBeanWithPrimitive() {
        Map<String, MethodData> methods = BeanTester.getPairs(TypicalBeanPrimitive.class);
        for (MethodData mp : methods.values()) {
            echo(mp);
        }
        assertEquals(4, methods.size());
        assertNotNull(methods.get("name"));
        assertNotNull(methods.get("value"));
        assertEquals(String.class.getName(), methods.get("name").getPropertyType().getName());
        assertEquals("long", methods.get("value").getPropertyType().getName());
    }

    @Test
    public void testPrimitive() {
        for (int i = 0; i < 100; i++) {
            echo("String:" + BeanTester.createManagedPrimitiveType(BeanTester.getTypeForClass(String.class)));
            echo("Integer:" + new Integer(BeanTester.createManagedPrimitiveType(BeanTester.getTypeForClass(int.class)).toString()));
            echo("Integer:" + new Integer(BeanTester.createManagedPrimitiveType(BeanTester.getTypeForClass(Integer.class)).toString()));
            echo("Long:" + new Long(BeanTester.createManagedPrimitiveType(BeanTester.getTypeForClass(long.class)).toString()));
            echo("Long:" + new Long(BeanTester.createManagedPrimitiveType(BeanTester.getTypeForClass(Long.class)).toString()));
            echo("Boolean:" + new Boolean(BeanTester.createManagedPrimitiveType(BeanTester.getTypeForClass(boolean.class)).toString()));
            echo("Boolean:" + new Boolean(BeanTester.createManagedPrimitiveType(BeanTester.getTypeForClass(Boolean.class)).toString()));
            echo("Double:" + new Double(BeanTester.createManagedPrimitiveType(BeanTester.getTypeForClass(double.class)).toString()));
            echo("Double:" + new Double(BeanTester.createManagedPrimitiveType(BeanTester.getTypeForClass(Double.class)).toString()));
            echo("Float:" + new Float(BeanTester.createManagedPrimitiveType(BeanTester.getTypeForClass(float.class)).toString()));
            echo("Float:" + new Float(BeanTester.createManagedPrimitiveType(BeanTester.getTypeForClass(Float.class)).toString()));
            echo("Byte:" + new Byte(BeanTester.createManagedPrimitiveType(BeanTester.getTypeForClass(byte.class)).toString()));
            echo("Byte:" + new Byte(BeanTester.createManagedPrimitiveType(BeanTester.getTypeForClass(Byte.class)).toString()));
            echo("Short:" + new Short(BeanTester.createManagedPrimitiveType(BeanTester.getTypeForClass(short.class)).toString()));
            echo("Short:" + new Short(BeanTester.createManagedPrimitiveType(BeanTester.getTypeForClass(Short.class)).toString()));
            echo("Character:" + new Character((char) BeanTester.createManagedPrimitiveType(BeanTester.getTypeForClass(char.class))));
            echo("Character:" + new Character((Character) BeanTester.createManagedPrimitiveType(BeanTester.getTypeForClass(Character.class))));
        }
    }

    private void echo(Object s) {
        //System.out.println(s);
    }
}
