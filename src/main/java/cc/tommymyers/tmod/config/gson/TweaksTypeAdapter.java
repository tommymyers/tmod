package cc.tommymyers.tmod.config.gson;

import cc.tommymyers.tmod.Tmod;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class TweaksTypeAdapter extends TypeAdapter<Tmod.Tweaks> {

    @Override
    public void write(JsonWriter out, Tmod.Tweaks value) throws IOException {
        out.beginObject();
        out.name("name");
    }

    @Override
    public Tmod.Tweaks read(JsonReader in) throws IOException {
        return null;
    }

}
