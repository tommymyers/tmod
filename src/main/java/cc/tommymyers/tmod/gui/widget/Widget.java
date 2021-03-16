package cc.tommymyers.tmod.gui.widget;

import cc.tommymyers.tmod.Tmod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public abstract class Widget extends DrawableHelper implements Drawable {

    protected MinecraftClient mc;
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected Widget(int x, int y, int width, int height) {
        mc = Tmod.mc;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {

    }

    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        return false;
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return x <= mouseX && mouseX <= this.x+this.width && this.y <= mouseY && mouseY <= this.y+this.height;
    }

}
