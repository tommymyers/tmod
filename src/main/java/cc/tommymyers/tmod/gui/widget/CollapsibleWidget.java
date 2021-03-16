package cc.tommymyers.tmod.gui.widget;

import cc.tommymyers.tmod.Tmod;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CollapsibleWidget extends Widget {

    private static final Identifier TEXTURE = new Identifier(Tmod.modId, "textures/gui/folding_icons.png");
    private boolean isExpanded;
    private ClickAction clickAction;

    public CollapsibleWidget(int x, int y, boolean isExpanded, ClickAction clickAction) {
        super(x, y, 16, 16);
        this.isExpanded = isExpanded;
        this.clickAction = clickAction;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        this.isExpanded = !this.isExpanded;
        this.clickAction.click(this.isExpanded);
        return true;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        mc.getTextureManager().bindTexture(TEXTURE);
        RenderSystem.enableDepthTest();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
        matrixStack.push();
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        matrixStack.translate(centerX, centerY, 0);
        matrixStack.scale(0.5f, 0.5f, 0.5f);
        matrixStack.translate(-centerX, -centerY, 0);
        drawTexture(matrixStack, this.x, this.y, this.isExpanded() ? 32 : 0, isMouseOver(mouseX, mouseY) ? 32 : 0, 32, 32, 64, 64);
        matrixStack.pop();
    }

    @FunctionalInterface
    public interface ClickAction {

        void click(boolean isExpanded);

    }

}
