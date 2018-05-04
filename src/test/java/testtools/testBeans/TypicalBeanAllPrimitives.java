package testtools.testBeans;

/**
 * Java Bean Tester library May 2018
 * GitHub "https://github.com/stuartdd/beanUnitTester"
 * @author stuartdd
 */
public class TypicalBeanAllPrimitives {
    private final StringBuilder log = new StringBuilder();
    private String name;
    private byte bytePrimitive;
    private Byte byteObject;
    private char charPrimitive;
    private Character charObject;
    private short shortPrimitive;
    private Short shortObject;
    private int intPrimitive;
    private Integer intObject;
    private long longPrimitive;
    private Long longObject;
    private double doublePrimitive;
    private Double doubleObject;
    private float floatPrimitive;
    private Float floatObject;
    private boolean booleanPrimitive;
    private Boolean booleanObject;


    public String getName() {
        log.append("getName()");
        return name;
    }

    public void setName(String name) {
        log.append("setName()");
        this.name = name;
    }

    public byte getBytePrimitive() {
        log.append("getBytePrimitive()|");
        return bytePrimitive;
    }

    public void setBytePrimitive(byte bytePrimitive) {
        log.append("setBytePrimitive()|");
        this.bytePrimitive = bytePrimitive;
    }

    public Byte getByteObject() {
        log.append("getByteObject()|");
        return byteObject;
    }

    public void setByteObject(Byte byteObject) {
        log.append("setByteObject()|");
        this.byteObject = byteObject;
    }

    public char getCharPrimitive() {
        log.append("getCharPrimitive()|");
        return charPrimitive;
    }

    public void setCharPrimitive(char charPrimitive) {
        log.append("setCharPrimitive()|");
        this.charPrimitive = charPrimitive;
    }

    public Character getCharObject() {
        log.append("getCharObject()|");
        return charObject;
    }

    public void setCharObject(Character charObject) {
        log.append("setCharObject()|");
        this.charObject = charObject;
    }

    public short getShortPrimitive() {
        log.append("getShortPrimitive()|");
        return shortPrimitive;
    }

    public void setShortPrimitive(short shortPrimitive) {
        log.append("setShortPrimitive()|");
        this.shortPrimitive = shortPrimitive;
    }

    public Short getShortObject() {
        log.append("getShortObject()|");
        return shortObject;
    }

    public void setShortObject(Short shortObject) {
        log.append("setShortObject()|");
        this.shortObject = shortObject;
    }

    public int getIntPrimitive() {
        log.append("getIntPrimitive()|");
        return intPrimitive;
    }

    public void setIntPrimitive(int intPrimitive) {
        log.append("setIntPrimitive()|");
        this.intPrimitive = intPrimitive;
    }

    public Integer getIntObject() {
        log.append("getIntObject()|");
        return intObject;
    }

    public void setIntObject(Integer intObject) {
        log.append("setIntObject()|");
        this.intObject = intObject;
    }

    public long getLongPrimitive() {
        log.append("getLongPrimitive()|");
        return longPrimitive;
    }

    public void setLongPrimitive(long longPrimitive) {
        log.append("setLongPrimitive()|");
        this.longPrimitive = longPrimitive;
    }

    public Long getLongObject() {
        log.append("getLongObject()|");
        return longObject;
    }

    public void setLongObject(Long longObject) {
        log.append("setLongObject()|");
        this.longObject = longObject;
    }

    public double getDoublePrimitive() {
        log.append("getDoublePrimitive()|");
        return doublePrimitive;
    }

    public void setDoublePrimitive(double doublePrimitive) {
        log.append("setDoublePrimitive()|");
        this.doublePrimitive = doublePrimitive;
    }

    public Double getDoubleObject() {
        log.append("getDoubleObject()|");
        return doubleObject;
    }

    public void setDoubleObject(Double doubleObject) {
        log.append("setDoubleObject()|");
        this.doubleObject = doubleObject;
    }

    public float getFloatPrimitive() {
        log.append("getFloatPrimitive()|");
        return floatPrimitive;
    }

    public void setFloatPrimitive(float floatPrimitive) {
        log.append("setFloatPrimitive()|");
        this.floatPrimitive = floatPrimitive;
    }

    public Float getFloatObject() {
        log.append("getFloatObject()|");
        return floatObject;
    }

    public void setFloatObject(Float floatObject) {
        log.append("setFloatObject()|");
        this.floatObject = floatObject;
    }

    public boolean isBooleanPrimitive() {
        log.append("isBooleanPrimitive()|");
        return booleanPrimitive;
    }

    public void setBooleanPrimitive(boolean booleanPrimitive) {
        log.append("setBooleanPrimitive()|");
        this.booleanPrimitive = booleanPrimitive;
    }

    public Boolean getBooleanObject() {
        log.append("getBooleanObject()|");
        return booleanObject;
    }

    public void setBooleanObject(Boolean booleanObject) {
        log.append("setBooleanObject()|");
        this.booleanObject = booleanObject;
    }

    @Override
    public String toString() {
        return "TypicalBeanAllPrimitives{" + log + '}';
    }
}
