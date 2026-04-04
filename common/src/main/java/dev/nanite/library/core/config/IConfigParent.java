package dev.nanite.library.core.config;

import de.marhali.json5.Json5Element;
import dev.nanite.library.core.config.values.*;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public interface IConfigParent {
    ConfigContainer getContainer();

    default @Nullable Json5Element getValue(String key) {
        return getContainer().getValue(key);
    }

    default void saveValue(String key, Json5Element element) {
        getContainer().saveValue(key, element);
    }

    default IntConfigValue intValue(String key, int defaultValue) {
        return getContainer().intValue(key, defaultValue);
    }

    default StringConfigValue stringValue(String key, String defaultValue) {
        return getContainer().stringValue(key, defaultValue);
    }

    default BooleanConfigValue booleanValue(String key, boolean defaultValue) {
        return getContainer().booleanValue(key, defaultValue);
    }

    default FloatConfigValue floatValue(String key, float defaultValue) {
        return getContainer().floatValue(key, defaultValue);
    }

    default DoubleConfigValue doubleValue(String key, double defaultValue) {
        return getContainer().doubleValue(key, defaultValue);
    }

    default LongConfigValue longValue(String key, long defaultValue) {
        return getContainer().longValue(key, defaultValue);
    }

    // List factory methods
    default <T> ConfigValue<List<T>> listValue(
            String key,
            List<T> defaultValue,
            Function<Json5Element, T> elementDeserializer,
            Function<T, Json5Element> elementSerializer
    ) {
        return getContainer().listValue(key, defaultValue, elementDeserializer, elementSerializer);
    }

    default ConfigValue<List<String>> stringListValue(String key, List<String> defaultValue) {
        return getContainer().stringListValue(key, defaultValue);
    }

    default ConfigValue<List<Integer>> intListValue(String key, List<Integer> defaultValue) {
        return getContainer().intListValue(key, defaultValue);
    }

    default ConfigValue<List<Boolean>> booleanListValue(String key, List<Boolean> defaultValue) {
        return getContainer().booleanListValue(key, defaultValue);
    }

    default ConfigValue<List<Float>> floatListValue(String key, List<Float> defaultValue) {
        return getContainer().floatListValue(key, defaultValue);
    }

    default ConfigValue<List<Double>> doubleListValue(String key, List<Double> defaultValue) {
        return getContainer().doubleListValue(key, defaultValue);
    }

    default ConfigValue<List<Long>> longListValue(String key, List<Long> defaultValue) {
        return getContainer().longListValue(key, defaultValue);
    }

    // Group factory method
    default ConfigValueGroup group(String key) {
        return getContainer().group(key);
    }
}
