package dev.nanite.library.core.config.values;

import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Object;
import dev.nanite.library.core.config.Config;
import dev.nanite.library.core.config.ConfigValue;
import dev.nanite.library.core.config.IConfigParent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class MapConfigValue<K, V> extends ConfigValue<Map<K, V>> {
    private final Function<String, K> keyDeserializer;
    private final Function<K, String> keySerializer;
    private final Function<Json5Element, V> valueDeserializer;
    private final Function<V, Json5Element> valueSerializer;

    public MapConfigValue(
            IConfigParent parent,
            String key,
            Map<K, V> defaultValue,
            Function<String, K> keyDeserializer,
            Function<K, String> keySerializer,
            Function<Json5Element, V> valueDeserializer,
            Function<V, Json5Element> valueSerializer
    ) {
        super(parent, key, defaultValue);
        this.keyDeserializer = keyDeserializer;
        this.keySerializer = keySerializer;
        this.valueDeserializer = valueDeserializer;
        this.valueSerializer = valueSerializer;
    }

    @Override
    public MapConfigValue<K, V> comments(String... comments) {
        return (MapConfigValue<K, V>) super.comments(comments);
    }

    @Override
    public boolean isInvalid(Map<K, V> value, Consumer<String> errorCollector) {
        if (value == null) {
            errorCollector.accept("Value cannot be null");
            return true;
        }
        return false;
    }

    @Override
    public Map<K, V> deserialize(Json5Element element) {
        if (!(element instanceof Json5Object obj)) {
            throw new IllegalArgumentException("Expected object for MapConfigValue");
        }

        Map<K, V> result = new HashMap<>();
        for (String key : obj.keySet()) {
            try {
                K deserializedKey = keyDeserializer.apply(key);
                V deserializedValue = valueDeserializer.apply(obj.get(key));
                result.put(deserializedKey, deserializedValue);
            } catch (Exception e) {
                Config.LOGGER.warn("Failed to deserialize map entry for key '{}': {}", key, e.getMessage());
            }
        }

        return result;
    }

    @Override
    public Json5Element serialize() {
        Json5Object obj = new Json5Object();
        for (Map.Entry<K, V> entry : get().entrySet()) {
            try {
                String serializedKey = keySerializer.apply(entry.getKey());
                Json5Element serializedValue = valueSerializer.apply(entry.getValue());
                obj.add(serializedKey, serializedValue);
            } catch (Exception e) {
                Config.LOGGER.warn("Failed to serialize map entry for key '{}': {}", entry.getKey(), e.getMessage());
            }
        }
        return obj;
    }
}
