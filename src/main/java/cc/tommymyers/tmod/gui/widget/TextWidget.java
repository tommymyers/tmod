package cc.tommymyers.tmod.gui.widget;

import cc.tommymyers.tmod.Tmod;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TextWidget extends Widget {

    private Tooltip tooltip = new Tooltip("");
    private final Text text;

    public TextWidget(String text, int x, int y, Tooltip tooltip) {
        this(Text.of(text), x, y, tooltip);
    }

    public TextWidget(Text text, int x, int y, Tooltip tooltip) {
        this(text, x, y);
        this.tooltip = tooltip;
    }

    public TextWidget(Text text, int x, int y) {
        super(x, y, Tmod.mc.textRenderer.getWidth(text), Tmod.mc.textRenderer.fontHeight);
        this.text = text;
    }

    public Tooltip getTooltip() {
        return tooltip;
    }

    public Text getText() {
        return text;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        drawTextWithShadow(matrixStack, Tmod.mc.textRenderer, this.text, this.x, this.y, 0xffffff);
        if (isMouseOver(mouseX, mouseY)) {
            this.tooltip.render(matrixStack, mouseX, mouseY);
        }
    }

    public static class Tooltip {

        private final List<Text> lines;

        public Tooltip(String... tooltip) {
            this(Lists.transform(Arrays.asList(tooltip), Text::of));
        }

        public Tooltip(List<Text> lines) {
            this.lines = lines;
        }

        public void render(MatrixStack matrixStack, int x, int y) {
            if (!this.lines.isEmpty()) {
                int i = 0;
                Iterator var6 = this.lines.iterator();

                while (var6.hasNext()) {
                    OrderedText orderedText = ((Text) var6.next()).asOrderedText();
                    int j = Tmod.mc.textRenderer.getWidth(orderedText);
                    if (j > i) {
                        i = j;
                    }
                }

                int k = x + 12;
                int l = y - 12;
                int n = 8;
                if (this.lines.size() > 1) {
                    n += 2 + (this.lines.size() - 1) * 10;
                }

                if (k + i > Tmod.mc.getWindow().getScaledWidth()) {
                    k -= 28 + i;
                }

                if (l + n + 6 > Tmod.mc.getWindow().getScaledHeight()) {
                    l = Tmod.mc.getWindow().getScaledHeight() - n - 6;
                }

                matrixStack.push();
                int o = -267386864;
                int p = 1347420415;
                int q = 1344798847;
                int r = 1;
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferBuilder = tessellator.getBuffer();
                bufferBuilder.begin(7, VertexFormats.POSITION_COLOR);
                Matrix4f matrix4f = matrixStack.peek().getModel();
                fillGradient(matrix4f, bufferBuilder, k - 3, l - 4, k + i + 3, l - 3, 400, -267386864, -267386864);
                fillGradient(matrix4f, bufferBuilder, k - 3, l + n + 3, k + i + 3, l + n + 4, 400, -267386864, -267386864);
                fillGradient(matrix4f, bufferBuilder, k - 3, l - 3, k + i + 3, l + n + 3, 400, -267386864, -267386864);
                fillGradient(matrix4f, bufferBuilder, k - 4, l - 3, k - 3, l + n + 3, 400, -267386864, -267386864);
                fillGradient(matrix4f, bufferBuilder, k + i + 3, l - 3, k + i + 4, l + n + 3, 400, -267386864, -267386864);
                fillGradient(matrix4f, bufferBuilder, k - 3, l - 3 + 1, k - 3 + 1, l + n + 3 - 1, 400, 1347420415, 1344798847);
                fillGradient(matrix4f, bufferBuilder, k + i + 2, l - 3 + 1, k + i + 3, l + n + 3 - 1, 400, 1347420415, 1344798847);
                fillGradient(matrix4f, bufferBuilder, k - 3, l - 3, k + i + 3, l - 3 + 1, 400, 1347420415, 1347420415);
                fillGradient(matrix4f, bufferBuilder, k - 3, l + n + 2, k + i + 3, l + n + 3, 400, 1344798847, 1344798847);
                RenderSystem.enableDepthTest();
                RenderSystem.disableTexture();
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.shadeModel(7425);
                bufferBuilder.end();
                BufferRenderer.draw(bufferBuilder);
                RenderSystem.shadeModel(7424);
                RenderSystem.disableBlend();
                RenderSystem.enableTexture();
                VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
                matrixStack.translate(0.0D, 0.0D, 400.0D);

                for (int s = 0; s < this.lines.size(); ++s) {
                    OrderedText orderedText2 = this.lines.get(s).asOrderedText();
                    if (orderedText2 != null) {
                        Tmod.mc.textRenderer.draw(orderedText2, (float) k, (float) l, -1, true, matrix4f, immediate, false, 0, 15728880);
                    }

                    if (s == 0) {
                        l += 2;
                    }

                    l += 10;
                }

                immediate.draw();
                matrixStack.pop();
            }
        }

    }

}
