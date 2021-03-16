package cc.tommymyers.tmod.gui.widget.config;

import cc.tommymyers.tmod.gui.widget.ButtonWidget;
import com.google.common.collect.Lists;
import net.minecraft.text.LiteralText;

import java.lang.reflect.Method;
import java.util.List;

public class EnumMultiChoiceButton extends ButtonWidget {

    public EnumMultiChoiceButton(Enum enumm, int x, int y, int width) {
        super(
            x,
            y,
            150,
            20,
            new LiteralText(""),
            button -> {

            }
        );
    }

    private static List getValuesForEnum(Enum enumm) {
        try {
            Method getValuesFunction = enumm.getDeclaringClass().getDeclaredMethod("values");
        } catch (NoSuchMethodException e) {
            return Lists.newArrayList("ERROR");
        }
        return Lists.newArrayList();
    }

}
