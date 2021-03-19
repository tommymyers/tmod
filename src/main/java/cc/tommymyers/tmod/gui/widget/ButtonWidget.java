package cc.tommymyers.tmod.gui.widget;

import cc.tommymyers.tmod.Tmod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ButtonWidget extends Widget {

    private final Identifier TEXTURE = new Identifier(Tmod.modId, "textures/gui/widgets.png");
    private Text text;
    private final PressAction pressAction;

    public ButtonWidget(int x, int y, int width, int height, Text text, PressAction pressAction) {
        super(x, y, width, height);
        this.text = text;
        this.pressAction = pressAction;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = new LiteralText(text);
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        pressAction.onClick(this);
        return true;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        Tmod.mc.getTextureManager().bindTexture(TEXTURE);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        int i = isMouseOver(mouseX, mouseY)? 2: 1;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.drawTexture(matrixStack, this.x, this.y, 0, i * 20, this.width / 2, this.height);
        this.drawTexture(matrixStack, this.x + this.width / 2, this.y, 200 - this.width / 2, i * 20, this.width / 2, this.height);
        drawCenteredText(matrixStack, this.mc.textRenderer, this.text, this.x + this.width / 2, this.y + (this.height - 8) / 2, 0xffffff);
    }

    @FunctionalInterface
    public interface PressAction {

        void onClick(ButtonWidget button);

    }

}
