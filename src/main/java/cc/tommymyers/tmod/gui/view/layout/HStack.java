package cc.tommymyers.tmod.gui.view.layout;

import cc.tommymyers.tmod.gui.view.View;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Arrays;
import java.util.List;

public class HStack extends View {

    private List<View> children;

    public HStack(View... children) {
        this.children = Arrays.asList(children);
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, int availableWidth, int availableHeight) {
        for (View child: children) {

            /*matrixStack.push();
            int x = availableWidth / 2 - child.getWidth() / 2;
            int y = availableHeight / 2 - child.getHeight() / 2;
            matrixStack.translate(x, y, 0);
            matrixStack.pop();*/
            child.render(matrixStack, x, y, child.getWidth(), child.getHeight());

        }
    }

}
