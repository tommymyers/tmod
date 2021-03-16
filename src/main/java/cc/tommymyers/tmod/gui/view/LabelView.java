package cc.tommymyers.tmod.gui.view;

import cc.tommymyers.tmod.Tmod;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class LabelView extends View {

    private final TextRenderer textRenderer;
    private Text text;

    public LabelView(String text) {
        this(Text.of(text));
    }

    public LabelView(Text text) {
        this.textRenderer = Tmod.mc.textRenderer;
        this.text = text;
        this.body = this;
    }

    @Override
    public int getWidth() {
        return this.textRenderer.getWidth(this.text);
    }

    @Override
    public int getHeight() {
        return this.textRenderer.fontHeight;
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, int availableWidth, int availableHeight) {
        this.textRenderer.drawWithShadow(matrixStack, this.text, x, y, Formatting.WHITE.getColorValue());
    }

}
