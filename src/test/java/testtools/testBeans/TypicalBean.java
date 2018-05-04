package testtools.testBeans;

/**
 * Java Bean Tester library May 2018
 * GitHub "https://github.com/stuartdd/beanUnitTester"
 * @author stuartdd
 */
public class TypicalBean {
    private final StringBuilder log = new StringBuilder();
    private String name;
    private String value;

    public String getName() {
        log.append(name == null ? "null-" : "").append("getName()|");
        return name;
    }

    public void setName(String name) {
        log.append("setName(").append(name == null ? "null-" : "").append(")|");
        this.name = name;
    }

    public String getValue() {
        log.append(value == null ? "null-" : "").append("getValue()|");
        return value;
    }

    public void setValue(String value) {
        log.append("setValue(").append(value == null ? "null-" : "").append(")|");
        this.value = value;
    }

    @Override
    public String toString() {
        return "TypicalBean{" + log + '}';
    }
}
