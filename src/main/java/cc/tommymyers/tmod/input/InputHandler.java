package cc.tommymyers.tmod.input;

import cc.tommymyers.tmod.Tmod;
import cc.tommymyers.tmod.gui.screen.ConfigScreen;
import cc.tommymyers.tmod.tweak.Tweak;
import cc.tommymyers.tmod.tweak.hud.ArmourDurability;
import cc.tommymyers.tmod.tweak.hud.PotionTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cc.tommymyers.tmod.input.Key.*;
import static cc.tommymyers.tmod.tweak.hud.ArmourDurability.DisplayType.FRACTION;
import static cc.tommymyers.tmod.tweak.hud.ArmourDurability.DisplayType.PERCENTAGE;
import static cc.tommymyers.tmod.tweak.hud.PotionTime.Position.ABOVE;
import static cc.tommymyers.tmod.tweak.hud.PotionTime.Position.BELOW;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class InputHandler {

    private static final Map<Keybind, KeybindCallback> callbacks = new HashMap<>();

    public static void registerKeyBindings() {
        registerKeybind(new Keybind(M, C), action -> Tmod.mc.openScreen(new ConfigScreen()));
        registerKeybind(new Keybind(M, S), action -> {
            PotionTime potionTime = (PotionTime) Tmod.tweaks.potionTime;
            if (potionTime.position.equals(ABOVE)) {
                potionTime.position = BELOW;
            } else {
                potionTime.position = ABOVE;
            }
            Tmod.config.save();
        });
        registerKeybind(new Keybind(M, D), action -> {
            ArmourDurability armourDurability = (ArmourDurability) Tmod.tweaks.armourDurability;
            if (armourDurability.displayType.equals(FRACTION)) {
                armourDurability.displayType = PERCENTAGE;
            } else {
                armourDurability.displayType = FRACTION;
            }
            Tmod.config.save();
        });
        for (Tweak tweak: Tmod.tweaks.all()) {
            registerKeybind(tweak.getKeybind(), action -> {
                tweak.toggle();
                Tmod.config.save();
            });
        }
    }

    private static void registerKeybind(Keybind keybind, KeybindCallback callback) {
        if (callbacks.containsKey(keybind)) {
            Tmod.logger.error("Could not register keybind", new Exception("Keybind already used"));
        } else {
            callbacks.put(keybind, callback);
        }
    }

    private static final List<Integer> currentSequence = new ArrayList<>();

    public static boolean processKey(int key, int action) {
        if (action == GLFW_PRESS) {
            currentSequence.add(key);
            if (Tmod.mc.currentScreen == null) {
                for (Keybind registeredKeybind: callbacks.keySet()) {
                    List<Integer> keybindKeys = registeredKeybind.getKeys().stream().map(Key::getCode).collect(Collectors.toList());
                    if (currentSequence.equals(keybindKeys)) {
                        callbacks.get(registeredKeybind).onKey(action);
                        return true;
                    }
                }
            }
        } else if (action == GLFW_RELEASE) {
            currentSequence.remove((Integer) key);
        }
        return false;
    }

    @FunctionalInterface
    public interface KeybindCallback {

        void onKey(int action);

    }

}
