package dev.nanite.library.core.config;

import de.marhali.json5.Json5Element;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class ConfigValue<T> implements Supplier<T> {
    private final IConfigParent parent;
    private final String key;
    private final T defaultValue;

    private boolean loaded = false;
    private T value;

    @Nullable
    private String[] comments;

    public ConfigValue(IConfigParent parent, String key, T defaultValue) {
        this.parent = parent;
        this.key = key;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public abstract T deserialize(Json5Element element);

    public abstract Json5Element serialize();

    public boolean isInvalid(T element, Consumer<String> errorCollector) {
        return false;
    }

    public ConfigValue<T> comments(String... comments) {
        this.comments = comments;
        return this;
    }

    public Json5Element serializeWithComments() {
        Json5Element element = serialize();
        if (comments != null) {
            element.setComment(String.join("\n", comments));
        }
        return element;
    }

    public void load() {
        Json5Element value = this.parent.getValue(key);
        if (value != null) {
            try {
                this.value = deserialize(value);
                List<String> validationErrors = new ArrayList<>();
                if (isInvalid(this.value, validationErrors::add)) {
                    String errorMsg = validationErrors.isEmpty() 
                        ? "Loaded config value for key " + key + " is invalid"
                        : String.join(", ", validationErrors);

                    Config.LOGGER.warn("Loaded config value for key {} is invalid, using default value. Errors: {}", 
                        key, errorMsg);

                    this.value = defaultValue;
                }
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
        this.parent.saveValue(key, serializeWithComments());
    }

    public void set(T value) {
        List<String> validationErrors = new ArrayList<>();
        if (isInvalid(value, validationErrors::add)) {
            String errorMsg = validationErrors.isEmpty()
                ? "Value " + value + " is not valid for config key '" + key + "'"
                : String.join(", ", validationErrors);
            throw new IllegalArgumentException(errorMsg);
        }
        
        T oldValue = this.value;
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
