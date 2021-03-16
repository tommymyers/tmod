package cc.tommymyers.tmod.gui;

import cc.tommymyers.tmod.gui.widget.Widget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.util.ArrayList;
import java.util.List;

public class GuiBase extends Screen {

    private final List<Widget> widgets = new ArrayList<>();

    public GuiBase(String title) {
        super(new LiteralText(title));
    }

    @Override
    protected void init() {
        this.widgets.clear();
    }

    public void addWidget(Widget widget) {
        if (!this.widgets.contains(widget)) {
            this.widgets.add(widget);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Widget widget: this.widgets) {
            if (widget.isMouseOver((int) mouseX, (int) mouseY) &&
                    widget.mouseClicked((int) mouseX, (int) mouseY, button)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return false;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        for (Widget widget: this.widgets) {
            widget.render(matrices, mouseX, mouseY, delta);
        }
    }

    public void drawStringWithShadow(MatrixStack matrixStack, String text, int x, int y, int colour) {
        drawTextWithShadow(matrixStack, super.textRenderer, new LiteralText(text), x, y, colour);
    }

}
