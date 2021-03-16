package cc.tommymyers.tmod.gui.widget;

import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

public class ListWidget extends Widget  {

    private List<Entry> entries;
    private Scrollbar scrollbar;

    public ListWidget(int x, int y, int x1, int y1) {
        super(x, y, x1-x, y1-y);
        entries = new ArrayList<>();
        scrollbar = new Scrollbar(x1-6, y, this.height);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        // Background
        fill(matrixStack, this.x, this.y, this.x+this.width, this.y+this.height, 0x80000000);

        scrollbar.render(matrixStack, mouseX, mouseY, delta);
    }

    private class Scrollbar extends Widget {

        protected Scrollbar(int x, int y, int height) {
            super(x, y, 6, height);
        }

        @Override
        public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
            fill(matrixStack, this.x+this.width-6, this.y, this.x+this.width, this.y+this.height, 0xffffffff);
        }

    }

    public class Entry extends Widget {

        protected Entry(int width) {
            super(0, 0, width, 26);
        }

        @Override
        public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {

        }

    }

}
