package cc.tommymyers.tmod.config;

public class ConfigurableOption {

    private final String propertyName;
    private final String name;
    private final String description;
    private Object value;

    public Object getValue() {
        return value;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ConfigurableOption(String propertyName, String name, String description, Object value) {
        this.propertyName = propertyName;
        this.name = name;
        this.description = description;
        this.value = value;
    }

}
