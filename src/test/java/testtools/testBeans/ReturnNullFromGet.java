package testtools.testBeans;

public class ReturnNullFromGet {
    private String name;
    private String value;

    public String getName() {
        return null;
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
