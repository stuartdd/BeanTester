package testtools.beantester;

import java.lang.reflect.Array;
import testtools.beantester.internal.Creator;
import testtools.beantester.internal.BeanTesterException;
import testtools.beantester.internal.MethodData;
import testtools.beantester.internal.BeanTestFailException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Java Bean Tester library May 2018 GitHub
 * "https://github.com/stuartdd/beanUnitTester"
 *
 * @author stuartdd
 */
public class BeanTester {

    private static final String NL = System.getProperty("line.separator");
    private static final String LINE = "************************************************************";
    private static final String SEP = " --> ";
    private static final String TAB = "  ";
    private static final String TABS = "                                                                                                     ";
    private static final int INDENT = 6;
    private static final String FAIL = SEP + "FAIL" + NL;
    private static final String PASS = SEP + "PASS" + NL;

    private static final int TYPE_UNDEFINED = -1;
    private static final int TYPE_STRING = 0;
    private static final int TYPE_BYTE = 1;
    private static final int TYPE_CHAR = 2;
    private static final int TYPE_SHORT = 3;
    private static final int TYPE_INT = 4;
    private static final int TYPE_LONG = 5;
    private static final int TYPE_DOUBLE = 6;
    private static final int TYPE_FLOAT = 7;
    private static final int TYPE_BOOL = 8;
    private static final int TYPE_LIST = 9;
    private static final int TYPE_CALENDAR = 10;
    private static final int TYPE_MAP = 11;
    private static final int TYPE_TREE_MAP = 12;
    private static final int TYPE_ARRAY = 99;

    private static final Map<String, Integer> MANAGED_TYPES = new HashMap<>();

    static {
        MANAGED_TYPES.put(String.class.getName(), TYPE_STRING);
        MANAGED_TYPES.put(byte.class.getName(), TYPE_BYTE);
        MANAGED_TYPES.put(Byte.class.getName(), TYPE_BYTE);
        MANAGED_TYPES.put(char.class.getName(), TYPE_CHAR);
        MANAGED_TYPES.put(Character.class.getName(), TYPE_CHAR);
        MANAGED_TYPES.put(short.class.getName(), TYPE_SHORT);
        MANAGED_TYPES.put(Short.class.getName(), TYPE_SHORT);
        MANAGED_TYPES.put(int.class.getName(), TYPE_INT);
        MANAGED_TYPES.put(Integer.class.getName(), TYPE_INT);
        MANAGED_TYPES.put(long.class.getName(), TYPE_LONG);
        MANAGED_TYPES.put(Long.class.getName(), TYPE_LONG);
        MANAGED_TYPES.put(double.class.getName(), TYPE_DOUBLE);
        MANAGED_TYPES.put(Double.class.getName(), TYPE_DOUBLE);
        MANAGED_TYPES.put(float.class.getName(), TYPE_FLOAT);
        MANAGED_TYPES.put(Float.class.getName(), TYPE_FLOAT);
        MANAGED_TYPES.put(boolean.class.getName(), TYPE_BOOL);
        MANAGED_TYPES.put(Boolean.class.getName(), TYPE_BOOL);
        MANAGED_TYPES.put(ArrayList.class.getName(), TYPE_LIST);
        MANAGED_TYPES.put(List.class.getName(), TYPE_LIST);
        MANAGED_TYPES.put(java.util.Calendar.class.getName(), TYPE_CALENDAR);
        MANAGED_TYPES.put(java.util.GregorianCalendar.class.getName(), TYPE_CALENDAR);
        MANAGED_TYPES.put(java.util.Map.class.getName(), TYPE_MAP);
        MANAGED_TYPES.put(java.util.HashMap.class.getName(), TYPE_MAP);
        MANAGED_TYPES.put(java.util.TreeMap.class.getName(), TYPE_TREE_MAP);
    }

    public static Object testBean(Class clazz) {
        return testBean(clazz, null, null, 0);
    }

    public static Object testBean(Class clazz, StringBuilder sb) {
        return testBean(clazz, sb, null, 0);
    }

    public static Object testBean(Class clazz, Creator creator) {
        return testBean(clazz, null, creator, 0);
    }

    public static Object testBean(Class clazz, StringBuilder sb, Creator creator) {
        return testBean(clazz, sb, creator, 0);
    }

    private static Object testBean(Class classUnderTest, StringBuilder sb, Creator creator, int indent) {
        if (sb != null) {
            sb.append(lineStart(classUnderTest.getSimpleName())).append(tab(indent)).append("ObjectUnderTest: ").append(classUnderTest.getName()).append(NL);
        }

        Object objectUnderTest = createObject(classUnderTest);
        Map<String, MethodData> map = getPairs(classUnderTest);
        if (map.isEmpty()) {
            if (sb != null) {
                sb.append("No setter/getter methods found:").append(classUnderTest.getName());
            }
            return objectUnderTest;
        }

        for (MethodData mp : map.values()) {
            if (sb != null) {
                sb.append(tab(1)).append(tab(indent)).append("NAME:").append(mp.getPropertyName()).append(SEP).append("TYPE:").append(mp.getPropertyType().getName());
            }

            Object setParameter = createParameter(mp.getPropertyType(), classUnderTest, mp.getPropertyName(), creator, sb, indent);
            if (sb != null) {
                sb.append(SEP).append("VALUE:\"").append(setParameter).append('"');
            }
            try {
                mp.getSet().invoke(objectUnderTest, setParameter);
                if (sb != null) {
                    sb.append(SEP).append("SET");
                }
            } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
                if (sb != null) {
                    sb.append(FAIL);
                }
                throw new BeanTestFailException("Cannot invoke " + mp.getSet().getName() + '(' + mp.getPropertyType() + ") where parameter is :" + setParameter.getClass().getName(), e);
            }

            Object getParameter;
            try {
                getParameter = mp.getGet().invoke(objectUnderTest);
                if (sb != null) {
                    sb.append(SEP).append("GET");
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                if (sb != null) {
                    sb.append(FAIL);
                }
                throw new BeanTesterException("Cannot invoke " + mp.getGet().getName() + "() where return value is :" + setParameter.getClass().getName(), e);
            }
            if (getParameter == null) {
                if (sb != null) {
                    sb.append(FAIL);
                }
                throw new BeanTestFailException("FAIL:Class:" + setParameter.getClass().getName() + ": Method:" + mp.getGet().getName() + "()  returned null. Should return '" + setParameter.toString() + '\'');
            }
            if (!getParameter.getClass().getName().equals(setParameter.getClass().getName())) {
                if (sb != null) {
                    sb.append(FAIL);
                }
                throw new BeanTestFailException("FAIL:Class:" + setParameter.getClass().getName() + ": Method:" + mp.getGet().getName() + "(). Returned " + getParameter.getClass().getName() + ". Should return: " + setParameter.getClass().getName());
            }
            if (!getParameter.toString().equals(setParameter.toString())) {
                if (sb != null) {
                    sb.append(FAIL);
                }
                throw new BeanTestFailException("FAIL:Class:" + setParameter.getClass().getName() + ": Method:" + mp.getGet().getName() + "()  returned '" + getParameter.toString() + "'. Should return '" + setParameter.toString() + '\'');
            }
            if (sb != null) {
                sb.append(PASS);
            }
        }
        if (sb != null) {
            sb.append(lineEnd(classUnderTest.getSimpleName()));
        }

        return objectUnderTest;
    }

    private static Object createParameter(Class parameterClass, Class classUnderTest, String propertyName, Creator creator, StringBuilder sb, int indent) {
        if (creator != null) {
            Object created = creator.create(classUnderTest, propertyName);
            if (created != null) {
                if (sb != null) {
                    sb.append(SEP).append(NL).append(tab(indent + 2)).append("DELEGATE RETURNED:").append(SEP).append(created.getClass().getName());
                }
                return created;
            }
        }
        int type = getTypeForClass(parameterClass);
        if (type != TYPE_UNDEFINED) {
            return createManagedPrimitiveType(type, parameterClass, creator, sb, indent);
        }
        return testBean(parameterClass, sb, creator, indent + 1);
    }

    private static Object createObject(Class clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeanTesterException("Cannot instantiate bean using default constructor:" + clazz.getName(), e);
        }
    }

    public static int getTypeForClassName(String className) {
        if (MANAGED_TYPES.containsKey(className)) {
            return MANAGED_TYPES.get(className);
        }
        return TYPE_UNDEFINED;
    }

    public static int getTypeForClass(Class clazz) {
        if (clazz.isArray()) {
            return TYPE_ARRAY;
        }
        return getTypeForClassName(clazz.getName());
    }

    public static Object createManagedPrimitiveType(int typeId) {
        return createManagedPrimitiveType(typeId, null, null, null, 0);
    }

    public static Object createManagedPrimitiveType(int typeId, Creator creator, StringBuilder sb, int indent) {
        return createManagedPrimitiveType(typeId, null, creator, sb, indent);
    }

    public static Object createManagedPrimitiveType(int typeId, Class subClass, Creator creator, StringBuilder sb, int indent) {
        switch (typeId) {
            case TYPE_STRING:
                return "Str:" + Math.random();
            case TYPE_BYTE:
                return (byte) unSignedIntInRange(Byte.MAX_VALUE);
            case TYPE_CHAR:
                return (char) unSignedIntInRange(Character.MAX_VALUE);
            case TYPE_SHORT:
                return (short) signedIntInRange(Short.MAX_VALUE);
            case TYPE_INT:
                return signedIntInRange(Integer.MAX_VALUE);
            case TYPE_LONG:
                return signedLongInRange(Long.MAX_VALUE);
            case TYPE_DOUBLE:
                return signedDoubleInRange(Double.MAX_VALUE);
            case TYPE_FLOAT:
                return signedFloatInRange(Float.MAX_VALUE);
            case TYPE_BOOL:
                return (Math.random() > 0.5);
            case TYPE_LIST:
                return new ArrayList<>();
            case TYPE_CALENDAR:
                return new GregorianCalendar();
            case TYPE_MAP:
                return new HashMap<>();
            case TYPE_TREE_MAP:
                return new TreeMap<>();
            case TYPE_ARRAY:
                return createArrayOfType(subClass, creator, sb, indent);
        }
        return null;
    }

    public static Object createArrayOfPrimitiveType(int typeId, Class subClass, Creator creator, StringBuilder sb, int indent) {
        switch (typeId) {
            case TYPE_STRING:
                return new String[]{"Str:" + Math.random(), "Str:" + Math.random()};
            case TYPE_BYTE:
                return new byte[]{(byte) unSignedIntInRange(Byte.MAX_VALUE), (byte) unSignedIntInRange(Byte.MAX_VALUE)};
            case TYPE_CHAR:
                return new char[]{(char) unSignedIntInRange(Character.MAX_VALUE), (char) unSignedIntInRange(Character.MAX_VALUE)};
            case TYPE_SHORT:
                return new short[]{(short) signedIntInRange(Short.MAX_VALUE), (short) signedIntInRange(Short.MAX_VALUE)};
            case TYPE_INT:
                return new int[]{signedIntInRange(Integer.MAX_VALUE), signedIntInRange(Integer.MAX_VALUE)};
            case TYPE_LONG:
                return new long[]{signedLongInRange(Long.MAX_VALUE), signedLongInRange(Long.MAX_VALUE)};
            case TYPE_DOUBLE:
                return new double[]{signedDoubleInRange(Double.MAX_VALUE), signedDoubleInRange(Double.MAX_VALUE)};
            case TYPE_FLOAT:
                return new float[]{signedFloatInRange(Float.MAX_VALUE), signedFloatInRange(Float.MAX_VALUE)};
            case TYPE_BOOL:
                return new boolean[]{Math.random() > 0.5, Math.random() > 0.5};
        }
        return null;
    }

    public static Object createArrayOfType(Class clazz, Creator creator, StringBuilder sb, int indent) {
        if (clazz == null) {
            return null;
        }
        Class componentType = clazz.getComponentType();
        int arrayType = getTypeForClass(componentType);
        if (componentType.isPrimitive()) {
            return createArrayOfPrimitiveType(arrayType, componentType, creator, sb, indent);
        }
        Object[] returnArray = (Object[]) Array.newInstance(componentType, 2);
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = testBean(componentType, sb, creator, indent + 1);
        }
        return returnArray;
    }

    private static int unSignedIntInRange(int max) {
        return (int) Math.round(Math.random() * (float) max);
    }

    private static int signedIntInRange(int max) {
        return (int) signedLongInRange(max);
    }

    private static float signedFloatInRange(float max) {
        return (float) signedDoubleInRange(max);
    }

    private static long signedLongInRange(long max) {
        return (long) (Math.random() * (double) max) * (Math.random() > 0.5 ? 1 : -1);
    }

    private static double signedDoubleInRange(double max) {
        return (Math.random() * max) * (Math.random() > 0.5 ? 1 : -1);
    }

    public static Map<String, MethodData> getPairs(Class clazz) {
        Map<String, MethodData> propertyMethods = new TreeMap<>();
        for (Method m : clazz.getMethods()) {
            String methodName = m.getName();
            if ((methodName.startsWith("set") && (m.getParameterTypes().length == 1))) {
                String propertyNameCaps = methodName.substring("set".length());
                String propertyName = propertyNameCaps.substring(0, 1).toLowerCase() + propertyNameCaps.substring(1);
                Method getMethod = findGetMethod(clazz, propertyNameCaps);
                if (getMethod != null) {
                    propertyMethods.put(propertyName, new MethodData(m, getMethod, propertyName));
                }
            }
        }
        return propertyMethods;
    }

    private static Method findGetMethod(Class clazz, String propertyNameCaps) {
        for (Method m : clazz.getMethods()) {
            if ((m.getName().equals("get" + propertyNameCaps)) || (m.getName().equals("is" + propertyNameCaps))) {
                if (m.getParameterTypes().length == 0) {
                    return m;
                }
            }
        }
        return null;
    }

    private static String tab(int indent) {
        return TABS.substring(0, indent * INDENT);
    }

    private static String lineStart(String title) {
        return NL + ("START " + LINE + "  " + title + "  " + LINE).substring(0, LINE.length() * 2) + " START" + NL;
    }

    private static String lineEnd(String title) {
        return ("END   " + LINE + "  " + title + "  " + LINE).substring(0, LINE.length() * 2) + "   END" + NL;
    }

}
