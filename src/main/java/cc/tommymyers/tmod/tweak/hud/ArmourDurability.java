package cc.tommymyers.tmod.tweak.hud;

import cc.tommymyers.tmod.tweak.Tweak;

public class ArmourDurability extends Tweak {

    @Configurable(
        name = "Number format",
        description = "Change how the number is displayed (fraction or percentage)"
    )
    public DisplayType displayType = DisplayType.FRACTION;

    public ArmourDurability(String name, String description) {
        super(name, description);
    }

    public enum DisplayType {
        FRACTION,
        PERCENTAGE
    }

}
