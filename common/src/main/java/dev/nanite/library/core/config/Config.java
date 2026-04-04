package dev.nanite.library.core.config;

import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Object;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Config {
    static Logger LOGGER = LoggerFactory.getLogger(Config.class);

    private final String modId;

    @Nullable
    private final String type;
    private final Map<String, ConfigValue<?>> values;

    public Config(String modId, @Nullable String type) {
        this.modId = modId;
        this.type = type;
        this.values = new HashMap<>();
    }

    public Config(String modId) {
        this(modId, null);
    }

    public void load() {
        // TODO: Load from disk.
        // Load in a json5Object.
        var configData = new Json5Object();

        for (var entry : values.entrySet()) {
            entry.getValue().load();
        }
    }

    public void save() {
        // TODO: Write to disk.
    }

    @Nullable
    public Json5Element getValue(String key) {
        throw new UnsupportedOperationException("Config does not support getting values by key");
    }

    public void saveValue(String key, Json5Element element) {
        throw new UnsupportedOperationException("Config does not support saving values by key");
    }
}
