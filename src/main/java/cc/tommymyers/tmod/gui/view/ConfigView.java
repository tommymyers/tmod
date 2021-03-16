package cc.tommymyers.tmod.gui.view;

import cc.tommymyers.tmod.gui.view.LabelView;
import cc.tommymyers.tmod.gui.view.ListView;
import cc.tommymyers.tmod.gui.view.View;
import cc.tommymyers.tmod.gui.view.layout.HStack;
import cc.tommymyers.tmod.gui.view.layout.Spacer;
import cc.tommymyers.tmod.gui.view.layout.ZStack;
import net.minecraft.text.Text;

public class ConfigView extends View {

    public ConfigView() {
        /*this.body = new ZStack(
                new ListView(),
                new LabelView(Text.of("Hello world!"))
        );*/
        //this.body = new LabelView(Text.of("Hello, world!"));
        this.body = new HStack(
                new LabelView("Test"),
                new Spacer(),
                new LabelView("World")
        );
    }

}
