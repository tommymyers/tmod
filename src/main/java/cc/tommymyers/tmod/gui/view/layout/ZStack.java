package cc.tommymyers.tmod.gui.view.layout;

import cc.tommymyers.tmod.gui.view.View;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Arrays;
import java.util.List;

public class ZStack extends View {

    private List<View> children;

    public ZStack(View... children) {
        this.children = Arrays.asList(children);
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, int availableWidth, int availableHeight) {
        for (View child: children) {

            child.render(matrixStack, 0, 0, availableWidth, availableHeight);

        }
    }

}
