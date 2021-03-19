package cc.tommymyers.tmod.gui.widget;

import cc.tommymyers.tmod.Tmod;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CheckboxWidget extends Widget {

    private final ValueChanged valueChanged;
    private static final Identifier TEXTURE = new Identifier("textures/gui/checkbox.png");
    private boolean enabled = true;
    private boolean checked;

    public CheckboxWidget(boolean checked, int x, int y, ValueChanged valueChanged) {
        super(x, y, 20, 20);
        this.checked = checked;
        this.valueChanged = valueChanged;
    }

    public boolean isChecked() {
        return checked;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        Tmod.mc.getTextureManager().bindTexture(TEXTURE);
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
        drawTexture(matrixStack, this.x, this.y, isMouseOver(mouseX, mouseY)? 20.0F: 0.0F, this.checked? 20.0F: 0.0F, 20, this.height, 64, 64);
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        checked = !checked;
        valueChanged.valueChanged(checked);
        return true;
    }

    @FunctionalInterface
    public interface ValueChanged<E> {

        void valueChanged(E value);

    }

}
