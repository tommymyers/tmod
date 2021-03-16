package cc.tommymyers.tmod.config;

import cc.tommymyers.tmod.Tmod;
import cc.tommymyers.tmod.input.TKeybind;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Config {

    public File tmodFolder;
    private final Gson gson;
    private final File tweaksFile;

    public Config(File configFolder) throws IOException {
        if (!configFolder.exists()) {
            if (!configFolder.mkdirs()) {
                throw new IOException("Failed to create config folder");
            }
        }
        tmodFolder = new File(configFolder, Tmod.modId);
        if (!tmodFolder.exists()) {
            if (!tmodFolder.mkdirs()) {
                throw new IOException("Failed to create tmod folder");
            }
        }
        gson = new Gson();
        tweaksFile = new File(tmodFolder, "tweaks.json");
    }

    public void load() throws IOException {
        JsonArray tweaksArray = gson.fromJson(FileUtils.readFileToString(tweaksFile, "UTF-8"), JsonArray.class);
        for(JsonElement element: tweaksArray) {
            JsonObject object = element.getAsJsonObject();
            String name = object.get("name").getAsString();
            boolean isEnabled = object.get("isEnabled").getAsBoolean();
            TKeybind keybind = TKeybind.parse(object.get("keybind").getAsString());
            //Tmod.logger.info(String.format("name: %s isEnabled: %s keybind: %s", name, isEnabled, keybind));
        }
    }

}
