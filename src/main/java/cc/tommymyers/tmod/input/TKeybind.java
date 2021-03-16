package cc.tommymyers.tmod.input;

import cc.tommymyers.tmod.Tmod;
import org.apache.logging.log4j.util.Strings;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TKeybind {

    private final List<Integer> keys;

    public List<Integer> getKeys() {
        return this.keys;
    }

    public TKeybind(int... keys) {
        this.keys = new ArrayList<>();
        Arrays.stream(keys).forEach(key -> this.keys.add(key));
    }

    public static TKeybind parse(String keys) {
        String[] components = keys.split(",");
        List<Integer> keysList = new ArrayList<>();
        for (String keyString: components) {
            String glfwKey = "GLFW_KEY_"+keyString.toUpperCase();
            try {
                Field field = GLFW.class.getDeclaredField(glfwKey);
                int keycode = (int) field.get(null);
                keysList.add(keycode);
            } catch (NoSuchFieldException exception) {
                Tmod.logger.error("Could not find key '"+glfwKey+"'", exception);
            } catch (IllegalAccessException exception) {
                Tmod.logger.error("Could not access field '"+glfwKey+"' in GLFW class", exception);
            }
        }
        return new TKeybind(keysList.stream().mapToInt(Integer::intValue).toArray());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> keyNames = keys.stream()
                .map(String::valueOf)
                //.map(keycode -> GLFW.glfwGetKeyName(keycode, GLFW.glfwGetKeyScancode(keycode)) )
                .collect(Collectors.toList());
        stringBuilder.append("TKeybind{keys=");
        stringBuilder.append(Strings.join(keyNames, ','));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
