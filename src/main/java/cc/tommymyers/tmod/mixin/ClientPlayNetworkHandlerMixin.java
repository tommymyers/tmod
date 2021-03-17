package cc.tommymyers.tmod.mixin;

import cc.tommymyers.tmod.Tmod;
import cc.tommymyers.tmod.util.Updater;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(
            method = "onGameJoin",
            at = @At("TAIL")
    )
    private void onGameJoin(GameJoinS2CPacket packet, CallbackInfo ci) {
        if (!Updater.hasCheckedForUpdate()) {
            if (Updater.checkForUpdates()) {
                String newVersion = Updater.getLatestVersion().getId();
                String url = Updater.getLatestVersion().getUrl();

                MutableText baseText = new LiteralText("A new ");
                baseText.append(new LiteralText(Tmod.modName).formatted(Formatting.YELLOW, Formatting.BOLD));
                baseText.append(" version is available: ");

                MutableText link = new LiteralText(newVersion).formatted(Formatting.YELLOW, Formatting.UNDERLINE, Formatting.BOLD);
                link.setStyle(link.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url)));
                baseText.append(link);

                Tmod.mc.inGameHud.getChatHud().addMessage(baseText);
            }
        }
    }

}
