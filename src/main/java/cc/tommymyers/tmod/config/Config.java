package cc.tommymyers.tmod.config;

import cc.tommymyers.tmod.Tmod;
import cc.tommymyers.tmod.input.Keybind;
import cc.tommymyers.tmod.tweak.Tweak;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class Config {

    private final String ENCODING = "UTF-8";

    private final File tmodFolder;
    private final File tweaksFile;

    public Config(File configFolder) {
        if (!configFolder.exists()) {
            configFolder.mkdirs();
        }
        tmodFolder = new File(configFolder, Tmod.modId);
        if (!tmodFolder.exists()) {
            tmodFolder.mkdirs();
        }
        tweaksFile = new File(tmodFolder, "tweaks.json");
    }

    public void loadTweaks(Gson gson) throws IOException, ReflectiveOperationException {
        JsonObject root = gson.fromJson(FileUtils.readFileToString(tweaksFile, ENCODING), JsonObject.class);
        for (Map.Entry<String, JsonElement> entry: root.entrySet()) {
            JsonObject tweakJsonObject = entry.getValue().getAsJsonObject();
            Tweak tweak = Tmod.tweaks.tweaksById().get(entry.getKey());
            tweak.setEnabled(tweakJsonObject.get("enabled").getAsBoolean());
            tweak.setKeybind(Keybind.parse(tweakJsonObject.get("keybind").getAsString()));
            if (!tweakJsonObject.has("options")) {
                continue;
            }
            JsonObject tweakOptions = tweakJsonObject.get("options").getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry1: tweakOptions.entrySet()) {
                Optional<ConfigurableOption> optional = tweak.getConfigurableVariables()
                    .stream()
                    .filter(c -> c.getPropertyName().equals(entry1.getKey()))
                    .findFirst();
                if (optional.isPresent()) {
                    ConfigurableOption co = optional.get();
                    Optional<ConfigurableOption.Reader> jsonHandlerOptional = co.getJsonReaderHandler();
                    if (jsonHandlerOptional.isPresent()) {
                        co.setValue(jsonHandlerOptional.get().handle(entry1.getValue()));
                    }
                }
            }
        }
    }

    private void saveTweaks(Gson gson) throws IOException, ReflectiveOperationException {
        JsonObject root = new JsonObject();
        for (String tweakId: Tmod.tweaks.tweaksById().keySet()) {
            Tweak tweak = Tmod.tweaks.tweaksById().get(tweakId);
            JsonObject tweakJson = new JsonObject();
            tweakJson.addProperty("enabled", tweak.isEnabled());
            tweakJson.addProperty("keybind", tweak.getKeybind().toString());
            if (tweak.getConfigurableVariables().size() > 0) {
                JsonObject userConfigurableOptions = new JsonObject();
                for (ConfigurableOption co: tweak.getConfigurableVariables()) {
                    Object value = co.getValue();
                    Optional<ConfigurableOption.Writer> jsonOptional = co.getJsonWriterHandler();
                    if (jsonOptional.isPresent()) {
                        userConfigurableOptions.add(co.getPropertyName(), jsonOptional.get().toElement(value));
                    }
                }
                tweakJson.add("options", userConfigurableOptions);
            }
            root.add(tweakId, tweakJson);
        }
        FileUtils.writeStringToFile(tweaksFile, gson.toJson(root), ENCODING);
    }

    public void save() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            saveTweaks(gson);
        } catch (Exception exception) {
            Tmod.logger.error("Could not save config", exception);
        }
    }

    public boolean load() {
        try {
            Gson gson = new Gson();
            loadTweaks(gson);
            return true;
        } catch (Exception exception) {
            Tmod.logger.error("Could not load config", exception);
        }
        return false;
    }

}
