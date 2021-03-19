package cc.tommymyers.tmod.tweak;

import cc.tommymyers.tmod.config.ConfigurableOption;
import cc.tommymyers.tmod.input.Key;
import cc.tommymyers.tmod.input.Keybind;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Tweak {

    private final String name;
    private final String description;
    private Keybind keybind;
    private boolean isEnabled;

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Keybind getKeybind() {
        return this.keybind;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setKeybind(Keybind keybind) {
        this.keybind = keybind;
    }

    public void toggle() {
        this.isEnabled = !this.isEnabled;
    }

    public Tweak(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Tweak keybind(Key... keys) {
        this.keybind = new Keybind(keys);
        return this;
    }

    public Tweak enabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    public List<ConfigurableOption> getConfigurableVariables() {
        List<ConfigurableOption> configurableOptions = new ArrayList<>();
        for (Field field: this.getClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(Configurable.class)) {
                continue;
            }
            Configurable annotation = field.getAnnotation(Configurable.class);
            configurableOptions.add(new ConfigurableOption(
                field,
                this,
                field.getName(),
                annotation.name(),
                annotation.description()
            ));
        }
        return configurableOptions;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Configurable {

        String name();

        String description();

    }

}
