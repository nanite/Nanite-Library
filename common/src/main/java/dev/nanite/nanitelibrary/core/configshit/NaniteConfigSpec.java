package dev.nanite.nanitelibrary.core.configshit;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.electronwill.nightconfig.toml.TomlParser;
import com.electronwill.nightconfig.toml.TomlWriter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import dev.nanite.nanitelibrary.core.config.NaniteConfigManager;
import dev.nanite.nanitelibrary.platform.Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * A config spec that holds a collection of config values and provides a way to load and save them
 */
public class NaniteConfigSpec {
    private static final Gson GSON = new Gson().newBuilder().setPrettyPrinting().create();
    private static final TomlFormat TOML = TomlFormat.instance();

    // We care about the order of the config so we use a linked hashmap
    private final String name;
    private final HashMap<String, NaniteConfigValue<?>> valueMap = new LinkedHashMap<>();

    public NaniteConfigSpec(String name) {
        this.name = name;
    }
    public <T> NaniteConfigValue<T> define(String name, T defaultValue, String... comments) {
        NaniteConfigValue<T> value = new NaniteConfigValue<>(this, name, defaultValue, comments);
        valueMap.put(name, value);
        return value;
    }

    @SuppressWarnings("unchecked") // We know that the value is of type T
    protected <T> T get(String name, T defaultValue) {
        if (valueMap.containsKey(name)) {
            return (T) valueMap.get(name).get();
        } else {
            return defaultValue;
        }
    }

    /**
     * Invalidate all values in the config and causes their cached values to be recalculated upon next access
     */
    public void invalidate() {
        valueMap.values().forEach(NaniteConfigValue::invalidate);
    }

    public void load() throws IOException {
        var configManager = NaniteConfigManager.INSTANCE;
        var location = Services.PLATFORM.gamePath().resolve("config/" + name + ".toml"); // Not sure about json yet, likely won't be json

        if(!Files.exists(location)){
            invalidate();
            save();
            return;
        }

        // Load the config from the file
        String configData = Files.readString(location);

        TomlParser parser = TOML.createParser();
        CommentedConfig parsedConfig = parser.parse(configData);
        Set<? extends CommentedConfig.Entry> entries = parsedConfig.entrySet();
//        // Parse the config data and populate the values
//        var data = GSON.fromJson(configData, JsonArray.class);
//
//        // Because we write in a nested fashion but read in a flat fashion, we need to flatten the config as we read it
//        var flatConfig = walkJsonStructure(data);
//
//        // Populate the values
        for (var entry : entries) {
            var key = entry.getKey();
            if (valueMap.containsKey(key)) {
                valueMap.get(key).populate(entry);
            }
        }
    }

    /**
     * Walks the json structure and flattens the into a dot-separated key-value map. We always start at a json array and
     * then nest into objects and arrays as we go.
     *
     * @param data The json data to walk
     * @return The flattened key-value map
     */
    private HashMap<String, JsonElement> walkJsonStructure(JsonArray data) {
        var flatConfig = new HashMap<String, JsonElement>();

        String parentKey = "";
        for (int i = 0; i < data.size(); i++) {
            var element = data.get(i);
            if (element.isJsonObject()) {
                var object = element.getAsJsonObject();
                for (var entry : object.entrySet()) {
                    var key = entry.getKey();
                    var value = entry.getValue();
                    if (value.isJsonArray()) {
                        // We need to walk the array
                        var array = value.getAsJsonArray();
                        var nested = walkJsonStructure(array);
                        for (var nestedEntry : nested.entrySet()) {
                            flatConfig.put(parentKey + "." + key + "." + nestedEntry.getKey(), nestedEntry.getValue());
                        }
                    } else {
                        flatConfig.put(parentKey + "." + key, value);
                    }
                }
            } else if (element.isJsonArray()) {
                // We need to walk the array
                var array = element.getAsJsonArray();
                var nested = walkJsonStructure(array);
                for (var nestedEntry : nested.entrySet()) {
                    flatConfig.put(parentKey + "." + nestedEntry.getKey(), nestedEntry.getValue());
                }
            } else {
                flatConfig.put(parentKey, element);
            }
        }

        return flatConfig;
    }

    public void save() throws IOException {
        Config c = Config.inMemory();
        for (Map.Entry<String, NaniteConfigValue<?>> valueEntry : valueMap.entrySet()) {
            c.set(valueEntry.getKey(), valueEntry.getValue().get());
        }
        TomlWriter tomlWriter = TOML.createWriter();
        String outputConfig = tomlWriter.writeToString(c);
        var location = Services.PLATFORM.gamePath().resolve("config/" + name + ".toml");
        Files.writeString(location, outputConfig, StandardOpenOption.WRITE);
    }
}

