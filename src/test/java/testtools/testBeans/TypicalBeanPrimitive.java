package testtools.testBeans;

/**
 * Java Bean Tester library May 2018
 * GitHub "https://github.com/stuartdd/beanUnitTester"
 * @author stuartdd
 */
public class TypicalBeanPrimitive {
    private final StringBuilder log = new StringBuilder();
    private String name;
    private long value = Long.MIN_VALUE;

    public String getName() {
        log.append(name == null ? "null-" : "").append("getName()|");
        return name;
    }

    public void setName(String name) {
        log.append("setName(").append(name == null ? "null-" : "").append(")|");
        this.name = name;
    }

    public long getValue() {
        log.append("getValue()|");
        return value;
    }

    public void setValue(long value) {
        log.append("setValue()|");
        this.value = value;
    }

    @Override
    public String toString() {
        return "TypicalBeanPrimitive{" + log + '}';
    }

}
