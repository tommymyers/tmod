package cc.tommymyers.tmod.mixin;

import cc.tommymyers.tmod.TmodClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Mixin(InGameHud.class)
public class InGameHudMixin extends DrawableHelper {

    private final int potionDurationTextWidth = 21;

    @Inject(
            method="renderStatusEffectOverlay",
            at=@At(
                value="INVOKE",
                target="Ljava/util/List;add(Ljava/lang/Object;)Z"
            ),
            locals=LocalCapture.CAPTURE_FAILSOFT
    )
    private void drawStatusEffectIcon(MatrixStack matrixStack, CallbackInfo callbackInfo, Collection<StatusEffectInstance> collection, int i1, int i2, StatusEffectSpriteManager statusEffectSpriteManager, List<Runnable> list, Iterator var8, StatusEffectInstance statusEffectInstance, StatusEffect statusEffect, int x, int y) {
        if (TmodClient.potionTimeEnabled) {
            list.add(() -> {
                String durationString = StatusEffectUtil.durationToString(statusEffectInstance, 1.0f);
                int stringWidth = TmodClient.mc.textRenderer.getWidth(durationString);
                // Scale the text down to fit in the width of one icon
                float scaleFactor = potionDurationTextWidth / (float) stringWidth;
                float textX = x + 12;
                float textY = y + 25;
                // TODO add more customisable options like text position (top and bottom)
                matrixStack.push();
                matrixStack.scale(scaleFactor, scaleFactor, 1.0f);
                scaleFactor = (float) stringWidth / (float) potionDurationTextWidth;
                matrixStack.translate(textX*scaleFactor, textY*scaleFactor, 0f);
                drawCenteredString(matrixStack, TmodClient.mc.textRenderer, durationString, 0, 0, 0xffffff);
                matrixStack.pop();
            });
        }
    }

    @ModifyVariable(
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/entity/effect/StatusEffect;isBeneficial()Z"
                    )
            ),
            method = "renderStatusEffectOverlay",
            at = @At(
                    value = "CONSTANT",
                    args = "intValue=25",
                    ordinal = 1
            ),
            ordinal = 3
    )
    private int incrementY(int y) {
        return TmodClient.potionTimeEnabled ? (y + 8) : y;
    }


}
