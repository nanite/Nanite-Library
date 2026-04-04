package dev.nanite.library.core.config;

import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Object;
import de.marhali.json5.Json5Primitive;
import dev.nanite.library.core.config.values.*;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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

    public Map<String, ConfigValue<?>> getValues() {
        return values;
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

    public ConfigValue<Boolean> booleanValue(String key, boolean defaultValue) {
        BooleanConfigValue value = new BooleanConfigValue(owner, key, defaultValue);
        values.put(key, value);
        return value;
    }

    public ConfigValue<Float> floatValue(String key, float defaultValue) {
        FloatConfigValue value = new FloatConfigValue(owner, key, defaultValue);
        values.put(key, value);
        return value;
    }

    public ConfigValue<Double> doubleValue(String key, double defaultValue) {
        DoubleConfigValue value = new DoubleConfigValue(owner, key, defaultValue);
        values.put(key, value);
        return value;
    }

    public ConfigValue<Long> longValue(String key, long defaultValue) {
        LongConfigValue value = new LongConfigValue(owner, key, defaultValue);
        values.put(key, value);
        return value;
    }

    public <T> ConfigValue<List<T>> listValue(
            String key,
            List<T> defaultValue,
            Function<Json5Element, T> elementDeserializer,
            Function<T, Json5Element> elementSerializer
    ) {
        ListConfigValue<T> value = new ListConfigValue<>(owner, key, defaultValue, elementDeserializer, elementSerializer);
        values.put(key, value);
        return value;
    }

    public ConfigValue<List<String>> stringListValue(String key, List<String> defaultValue) {
        return listValue(
                key,
                defaultValue,
                element -> {
                    if (element instanceof Json5Primitive primitive) {
                        return primitive.getAsString();
                    }
                    throw new IllegalArgumentException("Expected string");
                },
                Json5Primitive::fromString
        );
    }

    public ConfigValue<List<Integer>> intListValue(String key, List<Integer> defaultValue) {
        return listValue(
                key,
                defaultValue,
                element -> {
                    if (element instanceof Json5Primitive primitive) {
                        return primitive.getAsInt();
                    }
                    throw new IllegalArgumentException("Expected int");
                },
                Json5Primitive::fromNumber
        );
    }

    public ConfigValue<List<Boolean>> booleanListValue(String key, List<Boolean> defaultValue) {
        return listValue(
                key,
                defaultValue,
                element -> {
                    if (element instanceof Json5Primitive primitive) {
                        return primitive.getAsBoolean();
                    }
                    throw new IllegalArgumentException("Expected boolean");
                },
                Json5Primitive::fromBoolean
        );
    }

    public ConfigValue<List<Float>> floatListValue(String key, List<Float> defaultValue) {
        return listValue(
                key,
                defaultValue,
                element -> {
                    if (element instanceof Json5Primitive primitive) {
                        return primitive.getAsFloat();
                    }
                    throw new IllegalArgumentException("Expected float");
                },
                Json5Primitive::fromNumber
        );
    }

    public ConfigValue<List<Double>> doubleListValue(String key, List<Double> defaultValue) {
        return listValue(
                key,
                defaultValue,
                element -> {
                    if (element instanceof Json5Primitive primitive) {
                        return primitive.getAsDouble();
                    }
                    throw new IllegalArgumentException("Expected double");
                },
                Json5Primitive::fromNumber
        );
    }

    public ConfigValue<List<Long>> longListValue(String key, List<Long> defaultValue) {
        return listValue(
                key,
                defaultValue,
                element -> {
                    if (element instanceof Json5Primitive primitive) {
                        return primitive.getAsLong();
                    }
                    throw new IllegalArgumentException("Expected long");
                },
                Json5Primitive::fromNumber
        );
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
