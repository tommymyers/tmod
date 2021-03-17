package cc.tommymyers.tmod;

import cc.tommymyers.tmod.config.Config;
import cc.tommymyers.tmod.config.ConfigurableOption;
import cc.tommymyers.tmod.input.InputHandler;
import cc.tommymyers.tmod.input.TKeybind;
import cc.tommymyers.tmod.tweak.Tweak;
import cc.tommymyers.tmod.tweak.hud.ArmourDurability;
import cc.tommymyers.tmod.tweak.hud.PotionTime;
import cc.tommymyers.tmod.util.TLogger;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

@Environment(EnvType.CLIENT)
public class Tmod implements ClientModInitializer {

    public static String modId = "tmod";
    public static String modVersion = "unknown";
    public static String modName = "tMod";

    public static MinecraftClient mc;
    public static TLogger logger;
    public static Tweaks tweaks;
    public static Config config;

    @Override
    public void onInitializeClient() {
        ModContainer modContainer = FabricLoader.getInstance().getModContainer(modId).get();
        modVersion = modContainer.getMetadata().getVersion().getFriendlyString();
        mc = MinecraftClient.getInstance();
        logger = new TLogger(modId, modName);
        tweaks = new Tweaks();
        try {
            config = new Config(new File(mc.runDirectory, "config"));
            config.load();
        } catch (IOException ioException) {
            logger.error("Could not load config", ioException);
        }

        InputHandler.registerKeyBindings();

        for (Tweak tweak: tweaks.all()) {
            for (ConfigurableOption option: tweak.getConfigurableVariables()) {
                logger.info(tweak.getName() + " has an option called " + option.getName() + " (" + option.getPropertyName() + ") \"" + option.getDescription() + "\" with the value " + option.getValue());
            }
        }
    }

    public static class Tweaks {

        public Tweak potionTime = new PotionTime("Potion Time", "Displays potion wear-off time on the HUD").keybind(new TKeybind(GLFW_KEY_M, GLFW_KEY_P)).enabled(true);
        public Tweak armourDurability = new ArmourDurability("Armour Durability", "Displays your armour's health on the HUD").keybind(new TKeybind(GLFW_KEY_M, GLFW_KEY_A)).enabled(true);

        public List<Tweak> all() {
            List<Tweak> list = new ArrayList<>();
            for (Field field: Tweaks.class.getDeclaredFields()) {
                if (field.getType().equals(Tweak.class)) {
                    try {
                        Tweak tweak = (Tweak) field.get(tweaks);
                        list.add(tweak);
                    } catch (IllegalAccessException illegalAccessException) {
                        logger.error("Could not get tweak from TweaksList", illegalAccessException);
                    }
                }
            }
            return list;
        }

    }

}
