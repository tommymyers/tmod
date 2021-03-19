package cc.tommymyers.tmod.tweak.hud;

import cc.tommymyers.tmod.tweak.Tweak;

public class PotionTime extends Tweak {

    @Configurable(
        name = "Font size",
        description = "Adjust the duration text font size"
    )
    public float textWidth = 21;

    @Configurable(
        name = "Text position",
        description = "Position of text relative to the effect icon"
    )
    public Position position = Position.ABOVE;

    public PotionTime(String name, String description) {
        super(name, description);
    }

    public enum Position {
        BELOW,
        ABOVE
    }

}
