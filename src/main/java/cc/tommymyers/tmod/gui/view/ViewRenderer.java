package cc.tommymyers.tmod.gui.view;

import cc.tommymyers.tmod.Tmod;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class ViewRenderer extends Screen {

    private View view;

    public ViewRenderer(View view) {
        super(LiteralText.EMPTY);
        this.view = view;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        matrixStack.push();
        int screenWidth = Tmod.mc.getWindow().getScaledWidth();
        int screenHeight = Tmod.mc.getWindow().getScaledHeight();
        int x = screenWidth / 2 - view.getWidth() / 2;
        int y = screenHeight / 2 - view.getHeight() / 2;
        matrixStack.translate(x, y, 0);
        view.render(matrixStack, 0, 0, view.getWidth(), view.getHeight());
        matrixStack.pop();
    }

}
