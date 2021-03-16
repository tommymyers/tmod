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

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;

public class InputHandler {

    private static final Map<TKeybind, KeybindCallback> callbacks = new HashMap<>();

    public static void registerKeyBindings() {
        InputHandler.registerKeybind(new TKeybind(GLFW_KEY_M, GLFW_KEY_C), action -> {
            Tmod.mc.openScreen(new ConfigScreen());
        });
        InputHandler.registerKeybind(new TKeybind(GLFW_KEY_M, GLFW_KEY_S), action -> {
            PotionTime potionTime = (PotionTime) Tmod.tweaks.potionTime;
            if (potionTime.position.equals(PotionTime.Position.ABOVE)) {
                potionTime.position = PotionTime.Position.BELOW;
            } else {
                potionTime.position = PotionTime.Position.ABOVE;
            }
        });
        InputHandler.registerKeybind(new TKeybind(GLFW_KEY_M, GLFW_KEY_D), action -> {
            ArmourDurability armourDurability = (ArmourDurability) Tmod.tweaks.armourDurability;
            if (armourDurability.displayType.equals(ArmourDurability.DisplayType.FRACTION)) {
                armourDurability.displayType = ArmourDurability.DisplayType.PERCENTAGE;
            } else {
                armourDurability.displayType = ArmourDurability.DisplayType.FRACTION;
            }
        });
        for (Tweak tweak: Tmod.tweaks.all()) {
            InputHandler.registerKeybind(tweak.getKeybind(), action -> tweak.toggle());
        }
    }

    private static void registerKeybind(TKeybind keybind, KeybindCallback callback) {
        if (callbacks.containsKey(keybind)) {
            Tmod.logger.error("Could not register keybind", new Exception("Keybind already used"));
        } else {
            callbacks.put(keybind, callback);
        }
    }

    private static List<Integer> currentSequence = new ArrayList<>();

    public static boolean processKey(int key, int action) {
        if (action == GLFW_PRESS) {
            currentSequence.add(key);
            if (Tmod.mc.currentScreen == null) {
                for (TKeybind registeredKeybind: callbacks.keySet()) {
                    List<Integer> keybindKeys = registeredKeybind.getKeys();
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
