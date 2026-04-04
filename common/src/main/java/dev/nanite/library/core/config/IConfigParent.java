package dev.nanite.library.core.config;

import de.marhali.json5.Json5Element;
import org.jspecify.annotations.Nullable;

public interface IConfigParent {
    ConfigContainer getContainer();

    default @Nullable Json5Element getValue(String key) {
        return getContainer().getValue(key);
    }

    default void saveValue(String key, Json5Element element) {
        getContainer().saveValue(key, element);
    }

    default ConfigValue<Integer> intValue(String key, int defaultValue) {
        return getContainer().intValue(key, defaultValue);
    }

    default ConfigValue<String> stringValue(String key, String defaultValue) {
        return getContainer().stringValue(key, defaultValue);
    }

    default ConfigValueGroup group(String key) {
        return getContainer().group(key);
    }
}
