package cc.tommymyers.tmod.modmenu;

import cc.tommymyers.tmod.gui.screen.ConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<ConfigScreen> getModConfigScreenFactory() {
        return parent -> new ConfigScreen();
    }

}
