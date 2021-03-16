package cc.tommymyers.tmod.gui.view;

import net.minecraft.client.util.math.MatrixStack;

public class View {

    protected View body;
    protected int width;
    protected int height;

    public View() {
        this.width = 0;
        this.height = 0;
    }

    public void render(MatrixStack matrixStack, int x, int y, int availableWidth, int availableHeight) {
        //this.body.render(matrixStack, availableWidth, availableHeight);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
