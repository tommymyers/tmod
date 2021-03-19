package cc.tommymyers.tmod.gui.screen;

import cc.tommymyers.tmod.Tmod;
import cc.tommymyers.tmod.gui.GuiBase;
import cc.tommymyers.tmod.gui.widget.ListWidget;
import cc.tommymyers.tmod.gui.widget.TextWidget;
import cc.tommymyers.tmod.gui.widget.config.TweakToggleButton;
import cc.tommymyers.tmod.tweak.Tweak;
import net.minecraft.client.util.math.MatrixStack;

public class ConfigScreen extends GuiBase {

    public ConfigScreen() {
        super("Config");
    }

    @Override
    public void init() {
        super.init();
        int y = 34;
        this.addWidget(new ListWidget(
            32,
            32,
            this.width - 32,
            this.height - 32
        ));
/*        this.addWidget(new CheckboxWidget(true, 34 + 16, 34 + 32 + 32, value -> {
            Tmod.logger.info("New Value: "+value);
        }));
        this.addWidget(new TextWidget("Display even if the screen is off?", 34 + 16 + 26, 34 + 32 + 32 + 6, new TextWidget.Tooltip("This is some random description that is just a random sentence.")));*/
        for (Tweak tweak: Tmod.tweaks.all()) {
            int x = 34;
            /*this.addWidget(new CollapsibleWidget(
                x - 4,
                y + 4,
                false,
                isExpanded -> {
                    Tmod.logger.info(tweak.getName() + " configuration " + (isExpanded ? "expanded" : "collapsed"));
                }
            ));*/
            this.addWidget(new TextWidget(
                tweak.getName(),
                x + 16,
                y + 12,
                new TextWidget.Tooltip(tweak.getDescription())
            ));
            this.addWidget(new TweakToggleButton(
                tweak,
                width - 32 - 6 - 11 - 150,
                y + 6,
                150));
            y += 32;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        renderBackground(matrixStack);
        //fill(matrixStack, 32, 32, width - 32 - 6, 64, 0x40ffffff);
        super.render(matrixStack, mouseX, mouseY, delta);
    }

}
