package dev.nanite.library.core.config;

import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Object;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ConfigValueGroup extends ConfigValue<ConfigValueGroup> {
    private final Map<String, ConfigValue<?>> values = new HashMap<>();

    public ConfigValueGroup(Config parent, String key) {
        super(parent, key, null);
    }

    @Override
    public ConfigValueGroup deserialize(Json5Element element) {
        if (!(element instanceof Json5Object obj)) {
            throw new IllegalArgumentException("Expected a JSON object for ConfigValueGroup");
        }


    }

    @Override
    public Json5Element serialize() {
        var obj = new Json5Object();
        for (var entry : values.entrySet()) {
            obj.add(entry.getKey(), entry.getValue().serialize());
        }

        return obj;
    }
}
