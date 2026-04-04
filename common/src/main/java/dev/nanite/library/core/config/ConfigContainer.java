package dev.nanite.library.core.config;

import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Object;
import dev.nanite.library.core.config.values.IntConfigValue;
import dev.nanite.library.core.config.values.StringConfigValue;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ConfigContainer {
    private final IConfigParent owner;
    private final Json5Object data = new Json5Object();
    private final Map<String, ConfigValue<?>> values = new HashMap<>();

    public ConfigContainer(IConfigParent owner) {
        this.owner = owner;
    }

    public Json5Object getData() {
        return data;
    }

    public @Nullable Json5Element getValue(String key) {
        return data.get(key);
    }

    public void saveValue(String key, Json5Element element) {
        data.add(key, element);
    }

    public ConfigValue<Integer> intValue(String key, int defaultValue) {
        IntConfigValue value = new IntConfigValue(owner, key, defaultValue);
        values.put(key, value);
        return value;
    }

    public ConfigValue<String> stringValue(String key, String defaultValue) {
        StringConfigValue value = new StringConfigValue(owner, key, defaultValue);
        values.put(key, value);
        return value;
    }

    public ConfigValueGroup group(String key) {
        ConfigValueGroup group = new ConfigValueGroup(owner, key);
        values.put(key, group);
        return group;
    }

    public void loadValues() {
        for (var entry : values.entrySet()) {
            entry.getValue().load();
        }
    }
}
