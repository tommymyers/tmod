package cc.tommymyers.tmod;

import cc.tommymyers.tmod.config.Config;
import cc.tommymyers.tmod.input.InputHandler;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cc.tommymyers.tmod.input.Key.*;

@Environment(EnvType.CLIENT)
public class Tmod implements ClientModInitializer {

    public static String modId = "tmod";
    public static String modVersion;
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
        config = new Config(FabricLoader.getInstance().getConfigDir().toFile());
        if (!config.load()) {
            config.save();
        }

        InputHandler.registerKeyBindings();
    }

    public static class Tweaks {

        public Tweak potionTime = new PotionTime("Potion Time", "Displays potion wear-off time on the HUD").keybind(M, P).enabled(true);
        public Tweak armourDurability = new ArmourDurability("Armour Durability", "Displays your armour's health on the HUD").keybind(M, A).enabled(true);

        public List<Tweak> all() {
            return new ArrayList<>(tweaksById().values());
        }

        public Map<String, Tweak> tweaksById() {
            Map<String, Tweak> tweakHashMap = new HashMap<>();
            for (Field field: Tweaks.class.getDeclaredFields()) {
                if (field.getType().equals(Tweak.class)) {
                    try {
                        Tweak tweak = (Tweak) field.get(tweaks);
                        tweakHashMap.put(field.getName(), tweak);
                    } catch (Exception exception) {
                        logger.error("Something bizarre happened. Could not get tweak from TweaksList", exception);
                    }
                }
            }
            return tweakHashMap;
        }

    }

}
