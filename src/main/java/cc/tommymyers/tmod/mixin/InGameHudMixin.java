package cc.tommymyers.tmod.mixin;

import cc.tommymyers.tmod.Tmod;
import cc.tommymyers.tmod.tweak.hud.ArmourDurability;
import cc.tommymyers.tmod.tweak.hud.PotionTime;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static cc.tommymyers.tmod.tweak.hud.PotionTime.Position.ABOVE;
import static cc.tommymyers.tmod.tweak.hud.PotionTime.Position.BELOW;

@Mixin(InGameHud.class)
public class InGameHudMixin extends DrawableHelper {

    // Potion Time tweak

    @Inject(
        method = "renderStatusEffectOverlay",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/List;add(Ljava/lang/Object;)Z"
        ),
        locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void renderStatusEffectDuration(MatrixStack matrixStack, CallbackInfo callbackInfo, Collection<StatusEffectInstance> collection, int i1, int i2, StatusEffectSpriteManager statusEffectSpriteManager, List<Runnable> list, Iterator var8, StatusEffectInstance statusEffectInstance, StatusEffect statusEffect, int x, int y) {
        if (Tmod.tweaks.potionTime.isEnabled()) {
            list.add(() -> {
                String durationString = StatusEffectUtil.durationToString(statusEffectInstance, 1.0f);
                PotionTime potionTime = (PotionTime) Tmod.tweaks.potionTime;
                float potionDurationTextWidth = potionTime.textWidth;
                float stringWidth = Tmod.mc.textRenderer.getWidth(durationString);
                // Scale the text down to fit in the width of one icon
                float scaleFactor = potionDurationTextWidth / stringWidth;
                float textX = x + 12;
                float textY = y - 9;
                if (potionTime.position == BELOW) {
                    textY += 34;
                }
                matrixStack.push();
                matrixStack.translate(textX, textY, 0f);
                matrixStack.scale(scaleFactor, scaleFactor, 1.0f);
                matrixStack.translate(-textX, -textY, 0f);
                drawCenteredString(matrixStack, Tmod.mc.textRenderer, durationString, (int) textX, (int) textY, 0xffffff);
                matrixStack.pop();
            });
        }
    }

    // This changes how much farther down the "non-beneficial" potion effects are rendered (i.e. to make space for the text)

    @ModifyVariable(
        method = "renderStatusEffectOverlay",
        at = @At(
            value = "CONSTANT",
            args = "intValue=25",
            ordinal = 1
        ),
        ordinal = 3
    )
    private int incrementY(int y) {
        return Tmod.tweaks.potionTime.isEnabled()? (y + 10): y;
    }

    @ModifyConstant(
        method = "renderStatusEffectOverlay",
        constant = @Constant(
            intValue = 1
        )
    )
    private int initialY(int y) {
        if (Tmod.tweaks.potionTime.isEnabled()) {
            if (((PotionTime) Tmod.tweaks.potionTime).position == ABOVE) {
                return y + 9;
            }
        }
        return y;
    }

    // Armour Durability tweak

    @Inject(
        method = "render(Lnet/minecraft/client/util/math/MatrixStack;F)V",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V",
            ordinal = 4
        )
    )
    private void onRender(MatrixStack matrixStack, float partialTicks, CallbackInfo ci) {
        if (Tmod.mc.options.debugEnabled) {
            return;
        }

        if (Tmod.tweaks.armourDurability.isEnabled()) {
            int y = 30;
            for (ItemStack itemStack: Lists.reverse(Tmod.mc.player.inventory.armor)) {
                if (!itemStack.isEmpty()) {
                    int itemHealth = itemStack.getMaxDamage() - itemStack.getDamage();
                    Tmod.mc.getItemRenderer().renderInGui(itemStack, 10, y); // Render item
                    Tmod.mc.getItemRenderer().renderGuiItemOverlay(Tmod.mc.textRenderer, itemStack, 10, y); // Render item durability bar
                    String text;
                    switch (((ArmourDurability) Tmod.tweaks.armourDurability).displayType) {
                        case FRACTION:
                            text = String.format("%d/%d", itemHealth, itemStack.getMaxDamage());
                            break;
                        case PERCENTAGE:
                            float percent = itemHealth / (float) itemStack.getMaxDamage() * 100f;
                            text = String.format("%d (%.2f%%)", itemHealth, percent);
                            break;
                        default:
                            text = "invalid display type";
                            break;
                    }
                    drawStringWithShadow(matrixStack, Tmod.mc.textRenderer, text, 30, y + 5, 0xffffff);
                }
                y += 16;
            }
        }
    }

}
