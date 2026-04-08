package dev.nanite.library.core.config;

import com.mojang.datafixers.util.Either;
import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Object;
import de.marhali.json5.Json5Primitive;
import dev.nanite.library.core.config.values.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
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

    public IntConfigValue intValue(String key, int defaultValue) {
        IntConfigValue value = new IntConfigValue(owner, key, defaultValue);
        values.put(key, value);
        return value;
    }

    public StringConfigValue stringValue(String key, String defaultValue) {
        StringConfigValue value = new StringConfigValue(owner, key, defaultValue);
        values.put(key, value);
        return value;
    }

    public BooleanConfigValue booleanValue(String key, boolean defaultValue) {
        BooleanConfigValue value = new BooleanConfigValue(owner, key, defaultValue);
        values.put(key, value);
        return value;
    }

    public FloatConfigValue floatValue(String key, float defaultValue) {
        FloatConfigValue value = new FloatConfigValue(owner, key, defaultValue);
        values.put(key, value);
        return value;
    }

    public DoubleConfigValue doubleValue(String key, double defaultValue) {
        DoubleConfigValue value = new DoubleConfigValue(owner, key, defaultValue);
        values.put(key, value);
        return value;
    }

    public LongConfigValue longValue(String key, long defaultValue) {
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

    public <T> ConfigValue<Either<Identifier, TagKey<T>>> idOrTagValue(String key, ResourceKey<Registry<T>> registry, Either<Identifier, TagKey<T>> defaultValue) {
        return new ConfigValue<>(owner, key, defaultValue) {
            @Override
            public Either<Identifier, TagKey<T>> deserialize(Json5Element element) {
                return deserializeIdOrTag(registry, element);
            }

            @Override
            public Json5Element serialize() {
                return serializeIdOrTag(get());
            }
        };
    }

    public <T> ConfigValue<List<Either<Identifier, TagKey<T>>>> idOrTagListValue(String key, ResourceKey<Registry<T>> registry, List<Either<Identifier, TagKey<T>>> defaultValue) {
        return listValue(
            key,
            defaultValue,
            element -> deserializeIdOrTag(registry, element),
            ConfigContainer::serializeIdOrTag
        );
    }

    private static <T> Either<Identifier, TagKey<T>> deserializeIdOrTag(ResourceKey<Registry<T>> registry, Json5Element element) {
        if (element instanceof Json5Primitive primitive && primitive.isString()) {
            String str = primitive.getAsString();
            if (str.startsWith("#")) {
                String tagLocation = str.substring(1);
                Identifier location = Identifier.tryParse(tagLocation);
                if (location == null) {
                    throw new IllegalArgumentException("Invalid tag identifier: " + tagLocation);
                }

                return Either.right(TagKey.create(registry, location));
            } else {
                Identifier id = Identifier.tryParse(str);
                if (id == null) {
                    throw new IllegalArgumentException("Invalid identifier: " + str);
                }
                return Either.left(id);
            }
        }

        throw new IllegalArgumentException("Expected string value for Identifier or TagKey");
    }

    private static <T> Json5Element serializeIdOrTag(Either<Identifier, TagKey<T>> value) {
        return value.map(
            id -> Json5Primitive.fromString(id.toString()),
            tag -> Json5Primitive.fromString("#" + tag.location())
        );
    }

    public ConfigValueGroup group(String key) {
        ConfigValueGroup group = new ConfigValueGroup(owner, key);
        values.put(key, group);
        return group;
    }

    public <E extends Enum<E>> EnumConfigValue<E> enumValue(String key, E defaultValue) {
        EnumConfigValue<E> value = new EnumConfigValue<>(owner, key, defaultValue);
        values.put(key, value);
        return value;
    }

    /// Generic map factory - keys are always strings, custom value serialization
    public <V> MapConfigValue<String, V> mapValue(
            String key,
            Map<String, V> defaultValue,
            Function<Json5Element, V> valueDeserializer,
            Function<V, Json5Element> valueSerializer
    ) {
        MapConfigValue<String, V> value = new MapConfigValue<>(
            owner, key, defaultValue, 
            s -> s,  // Key is already string
            s -> s,  // Key serializes to itself
            valueDeserializer, 
            valueSerializer
        );
        values.put(key, value);
        return value;
    }

    // Convenience methods for common map types
    public MapConfigValue<String, String> stringMapValue(String key, Map<String, String> defaultValue) {
        return mapValue(
            key,
            defaultValue,
            element -> {
                if (element instanceof Json5Primitive primitive) {
                    return primitive.getAsString();
                }
                throw new IllegalArgumentException("Expected string value in map");
            },
            Json5Primitive::fromString
        );
    }

    public void loadValues() {
        for (var entry : values.entrySet()) {
            entry.getValue().load();
        }
    }
}
