package testtools.beantester;

import testtools.beantester.internal.Creator;
import testtools.beantester.internal.BeanTesterException;
import testtools.beantester.internal.MethodData;
import testtools.beantester.internal.BeanTestFailException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
/**
 * Java Bean Tester library May 2018
 * GitHub "https://github.com/stuartdd/beanUnitTester"
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

    private static final Map<String, Integer> managedTypes = new HashMap<>();

    static {
        managedTypes.put(String.class.getName(), TYPE_STRING);
        managedTypes.put(byte.class.getName(), TYPE_BYTE);
        managedTypes.put(Byte.class.getName(), TYPE_BYTE);
        managedTypes.put(char.class.getName(), TYPE_CHAR);
        managedTypes.put(Character.class.getName(), TYPE_CHAR);
        managedTypes.put(short.class.getName(), TYPE_SHORT);
        managedTypes.put(Short.class.getName(), TYPE_SHORT);
        managedTypes.put(int.class.getName(), TYPE_INT);
        managedTypes.put(Integer.class.getName(), TYPE_INT);
        managedTypes.put(long.class.getName(), TYPE_LONG);
        managedTypes.put(Long.class.getName(), TYPE_LONG);
        managedTypes.put(double.class.getName(), TYPE_DOUBLE);
        managedTypes.put(Double.class.getName(), TYPE_DOUBLE);
        managedTypes.put(float.class.getName(), TYPE_FLOAT);
        managedTypes.put(Float.class.getName(), TYPE_FLOAT);
        managedTypes.put(boolean.class.getName(), TYPE_BOOL);
        managedTypes.put(Boolean.class.getName(), TYPE_BOOL);
        managedTypes.put(ArrayList.class.getName(), TYPE_LIST);
        managedTypes.put(List.class.getName(), TYPE_LIST);
        managedTypes.put(java.util.Calendar.class.getName(), TYPE_CALENDAR);
        managedTypes.put(java.util.GregorianCalendar.class.getName(), TYPE_CALENDAR);
        managedTypes.put(java.util.Map.class.getName(), TYPE_MAP);
        managedTypes.put(java.util.HashMap.class.getName(), TYPE_MAP);
        managedTypes.put(java.util.TreeMap.class.getName(), TYPE_TREE_MAP);
    }

    public static int getTypeForClass(Class clazz) {
        if (managedTypes.containsKey(clazz.getName())) {
            return managedTypes.get(clazz.getName());
        }
        return TYPE_UNDEFINED;
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
            Object getParameter;
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
        Integer type = managedTypes.get(parameterClass.getName());
        if (type != null) {
            return createManagedType(type);
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

    public static Object createManagedType(int type) {
        switch (type) {
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
        }
        return null;
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
