package cc.tommymyers.tmod.gui.widget.config;

import cc.tommymyers.tmod.gui.widget.ButtonWidget;
import cc.tommymyers.tmod.tweak.Tweak;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import static net.minecraft.util.Formatting.GREEN;
import static net.minecraft.util.Formatting.RED;

public class TweakToggleButton extends ButtonWidget {

    public TweakToggleButton(Tweak tweak, int x, int y, int width) {
        super(
            x,
            y,
            150,
            20,
            getTextForTweak(tweak),
            button -> {
                tweak.toggle();
                button.setText(
                    getTextForTweak(tweak)
                );
            }
        );
    }

    private static Text getTextForTweak(Tweak tweak) {
        return new LiteralText(
            tweak.isEnabled()?
                "Enabled":
                "Disabled"
        ).formatted(
            tweak.isEnabled()?
                GREEN:
                RED
        );
    }

}
