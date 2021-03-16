package cc.tommymyers.tmod.tweak;

import cc.tommymyers.tmod.Tmod;
import cc.tommymyers.tmod.config.ConfigurableOption;
import cc.tommymyers.tmod.gui.widget.ButtonWidget;
import cc.tommymyers.tmod.input.TKeybind;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.util.Strings;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tweak {

    private final String name;
    private final String description;
    private TKeybind keybind;
    private boolean isEnabled;

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public TKeybind getKeybind() {
        return this.keybind;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void toggle() { this.isEnabled = !this.isEnabled; }

    public Tweak(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Tweak keybind(TKeybind keybind) {
        this.keybind = keybind;
        return this;
    }

    public Tweak enabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    public List<ConfigurableOption> getConfigurableVariables() {
        List<ConfigurableOption> configurableOptions = new ArrayList<>();
        try {
            for (Field field: this.getClass().getDeclaredFields()) {
                if (!field.isAnnotationPresent(Configurable.class))
                    continue;

                Configurable annotation = field.getAnnotation(Configurable.class);
                ConfigurableOption configurable = new ConfigurableOption(field.getName(), annotation.name(), annotation.description(), field.get(this));
                configurableOptions.add(configurable);
            }
        } catch (ReflectiveOperationException exception) {
            Tmod.logger.error("Java Reflect error occurred", exception);
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
