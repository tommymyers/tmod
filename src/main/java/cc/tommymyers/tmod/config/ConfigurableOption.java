package cc.tommymyers.tmod.config;

import cc.tommymyers.tmod.tweak.Tweak;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConfigurableOption {

    @FunctionalInterface
    public interface Reader {
        Object handle(JsonElement e);
    }

    @FunctionalInterface
    public interface Writer {
        JsonElement toElement(Object obj);
    }

    private static final Map<Class, Reader> deserializeHandler = new HashMap<>();
    private static final Map<Class, Writer> serializeHandler = new HashMap<>();

    static {
        deserializeHandler.put(String.class, JsonElement::getAsString);
        deserializeHandler.put(Float.TYPE, JsonElement::getAsFloat);
        deserializeHandler.put(Integer.TYPE, JsonElement::getAsInt);
        deserializeHandler.put(Boolean.TYPE, JsonElement::getAsBoolean);
        serializeHandler.put(String.class, obj -> new JsonPrimitive(obj.toString()));
        serializeHandler.put(Float.TYPE, obj -> new JsonPrimitive(Float.parseFloat(obj.toString())));
        serializeHandler.put(Integer.TYPE, obj -> new JsonPrimitive(Integer.parseInt(obj.toString())));
        serializeHandler.put(Boolean.TYPE, obj -> new JsonPrimitive(Boolean.parseBoolean(obj.toString())));
    }

    public Optional<Reader> getJsonReaderHandler() {
        if (getType().isEnum()) {
            return Optional.of(e -> {
                try {
                    Method valueOf = field.getType().getMethod("valueOf", String.class);
                    Enum enumm = (Enum) valueOf.invoke(null, e.getAsString());
                    return enumm;
                } catch (Exception exception) {
                    return Optional.empty();
                }
            });
        }
        return Optional.ofNullable(deserializeHandler.get(getType()));
    }

    public Optional<Writer> getJsonWriterHandler() {
        if (getType().isEnum()) {
            return Optional.of(e -> new JsonPrimitive(e.toString()));
        }
        return Optional.ofNullable(serializeHandler.get(getType()));
    }

    private final Field field;
    private final Tweak tweak;
    private final String propertyName;
    private final String name;
    private final String description;

    public Object getValue() throws ReflectiveOperationException {
        return field.get(tweak);
    }

    public Class getType() {
        return field.getType();
    }

    public void setValue(Object value) throws ReflectiveOperationException {
        field.set(tweak, value);
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

    public ConfigurableOption(Field field, Tweak tweak, String propertyName, String name, String description) {
        this.field = field;
        this.tweak = tweak;
        this.propertyName = propertyName;
        this.name = name;
        this.description = description;
    }

}
