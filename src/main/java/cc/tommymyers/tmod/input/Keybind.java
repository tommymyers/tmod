package cc.tommymyers.tmod.input;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Keybind {

    private final List<Key> keys;

    public List<Key> getKeys() {
        return this.keys;
    }

    public Keybind(Key... keys) {
        this.keys = new ArrayList<>();
        this.keys.addAll(Arrays.asList(keys));
    }

    @Override
    public String toString() {
        return StringUtils.join(keys, ',');
    }

    public static Keybind parse(String s) {
        Keybind keybind = new Keybind();
        for (String split: s.toUpperCase().split(",")) {
            Key key = Key.getKeyByCharacter(split.charAt(0));
            keybind.keys.add(key);
        }
        return keybind;
    }

}
