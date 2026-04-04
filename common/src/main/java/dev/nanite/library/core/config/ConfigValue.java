package dev.nanite.library.core.config;

import de.marhali.json5.Json5Element;

import java.util.function.Supplier;

public abstract class ConfigValue<T> implements Supplier<T> {
    private final Config parent;
    private final String key;
    private final T defaultValue;

    private boolean loaded = false;
    private T value;

    public ConfigValue(Config parent, String key, T defaultValue) {
        this.parent = parent;
        this.key = key;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public abstract T deserialize(Json5Element element);

    public abstract Json5Element serialize();

    public void load() {
        if (loaded) {
            return;
        }

        Json5Element value = this.parent.getValue(key);
        if (value != null) {
            try {
                this.value = deserialize(value);
            } catch (Exception e) {
                Config.LOGGER.warn("Failed to load config value for key {}, using default value. Error: {}", key, e.getMessage());
                this.value = defaultValue;
            }
        } else {
            this.value = defaultValue;
        }

        loaded = true;
    }

    public void save() {
        Json5Element element = serialize();
        this.parent.saveValue(key, element);
    }

    public void set(T value) {
        this.value = value;
        save();
    }

    @Override
    public T get() {
        if (!loaded) {
            load();
        }

        if (value == null) {
            return defaultValue;
        }

        return value;
    }
}
