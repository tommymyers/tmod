package cc.tommymyers.tmod.gui.widget.config;

import cc.tommymyers.tmod.Tmod;
import cc.tommymyers.tmod.gui.widget.ButtonWidget;
import cc.tommymyers.tmod.gui.widget.CollapsibleWidget;
import cc.tommymyers.tmod.gui.widget.TextWidget;
import cc.tommymyers.tmod.tweak.Tweak;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class TweakEntry extends DrawableHelper {

    private CollapsibleWidget collapsibleWidget;
    private TextWidget textWidget;
    private ButtonWidget buttonWidget;

    public TweakEntry(Tweak tweak) {

        /*collapsibleWidget = new CollapsibleWidget(
                x,
                y,
                false,
                isExpanded -> {
                    Tmod.logger.info(tweak.getName() + " configuration " + (isExpanded ? "expanded" : "collapsed"));
                }
        );
        this.addWidget(new TextWidget(
                tweak.getName(),
                x + 32,
                y + 11,
                new TextWidget.Tooltip(tweak.getDescription())
        ));
        this.addWidget(new ButtonWidget(
                width - 32 - 6 - 11 - 150,
                y + 6,
                150,
                20,
                getColouredStateTextForTweak(tweak),
                button -> {
                    tweak.toggle();
                    button.setText(getColouredStateTextForTweak(tweak));
                }
        ));*/
    }

    public void render(MatrixStack matrixStack, int x, int y, int mouseX, int mouseY, float delta) {
        collapsibleWidget.render(matrixStack, mouseX, mouseY, delta);
        textWidget.render(matrixStack, mouseX, mouseY, delta);
        buttonWidget.render(matrixStack, mouseX, mouseY, delta);

    }

}
