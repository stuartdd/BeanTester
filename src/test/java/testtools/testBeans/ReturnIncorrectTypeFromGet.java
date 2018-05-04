package testtools.testBeans;

public class ReturnIncorrectTypeFromGet {
    private String name;
    private String value;

    public Integer getName() {
        return new Integer("123");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
