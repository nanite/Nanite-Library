package dev.nanite.library.core.config.values;

import de.marhali.json5.Json5Array;
import de.marhali.json5.Json5Element;
import dev.nanite.library.core.config.Config;
import dev.nanite.library.core.config.ConfigValue;
import dev.nanite.library.core.config.IConfigParent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ListConfigValue<T> extends ConfigValue<List<T>> {
    private final Function<Json5Element, T> elementDeserializer;
    private final Function<T, Json5Element> elementSerializer;

    public ListConfigValue(
            IConfigParent parent,
            String key,
            List<T> defaultValue,
            Function<Json5Element, T> elementDeserializer,
            Function<T, Json5Element> elementSerializer
    ) {
        super(parent, key, defaultValue);
        this.elementDeserializer = elementDeserializer;
        this.elementSerializer = elementSerializer;
    }

    @Override
    public List<T> deserialize(Json5Element element) {
        if (!(element instanceof Json5Array array)) {
            throw new IllegalArgumentException("Expected array for ListConfigValue");
        }

        List<T> result = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            try {
                T value = elementDeserializer.apply(array.get(i));
                result.add(value);
            } catch (Exception e) {
                Config.LOGGER.warn("Failed to deserialize list element at index {}: {}", i, e.getMessage());
            }
        }

        return result;
    }

    @Override
    public Json5Element serialize() {
        Json5Array array = new Json5Array();
        for (T element : get()) {
            try {
                array.add(elementSerializer.apply(element));
            } catch (Exception e) {
                Config.LOGGER.warn("Failed to serialize list element: {}", e.getMessage());
            }
        }
        return array;
    }
}
