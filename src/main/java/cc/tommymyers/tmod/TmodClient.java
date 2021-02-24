package cc.tommymyers.tmod;

import cc.tommymyers.tmod.util.TmodLogger;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class TmodClient implements ClientModInitializer {

    public static String modId = "tmod";
    public static String modVersion;
    public static String modName;

    public static MinecraftClient mc;
    public static TmodLogger logger;

    private static KeyBinding togglePotionTimeKeyBind;
    public static boolean potionTimeEnabled = true;

    @Override
    public void onInitializeClient() {
        mc = MinecraftClient.getInstance();
        ModContainer modContainer = FabricLoader.getInstance().getModContainer(modId).get();
        modName = modContainer.getMetadata().getName();
        modVersion = modContainer.getMetadata().getVersion().getFriendlyString();
        logger = new TmodLogger(modId, modName);

        registerKeyBindings();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            clientTick();
        });
    }

    private void registerKeyBindings() {
        togglePotionTimeKeyBind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.tmod.togglePotionTime",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                "category.tmod"
        ));
    }

    private void clientTick() {
        while (togglePotionTimeKeyBind.wasPressed()) {
            potionTimeEnabled = !potionTimeEnabled;
        }
    }

}
