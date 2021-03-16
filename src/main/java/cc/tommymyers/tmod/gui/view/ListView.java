package cc.tommymyers.tmod.gui.view;

import cc.tommymyers.tmod.Tmod;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

public class ListView extends View {

    private List<View> entries;

    public ListView() {
        this(Tmod.mc.getWindow().getScaledWidth()-32, Tmod.mc.getWindow().getScaledHeight()-32);
    }

    public ListView(int width, int height) {
        this.width = width;
        this.height = height;
        this.entries = new ArrayList();
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, int availableWidth, int availableHeight) {
        DrawableHelper.fill(matrixStack, 0, 0, this.width, this.height, 0x80000000);

        // Scrollbar
        DrawableHelper.fill(matrixStack, this.width-6, 0, this.width, this.height-60, 0xffffffff);
    }

}
