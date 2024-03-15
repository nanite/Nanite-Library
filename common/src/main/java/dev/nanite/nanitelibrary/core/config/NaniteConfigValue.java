package dev.nanite.nanitelibrary.core.config;

import com.google.gson.JsonElement;

import java.util.function.Supplier;

/**
 * Holder for a config value
 */
public class NaniteConfigValue<T> implements Supplier<T> {
    private final NaniteConfigSpec container;

    private final String name;
    private final T defaultValue;
    private final String[] comments;

    private boolean invalidated;
    private T value;

    public NaniteConfigValue(NaniteConfigSpec container, String name, T defaultValue, String[] comments) {
        this.container = container;
        this.name = name;
        this.defaultValue = defaultValue;
        this.comments = comments;
    }

    @Override
    public T get() {
        if (this.invalidated) {
            this.value = this.container.get(this.name, this.defaultValue);
            this.invalidated = false;
        }

        return this.value;
    }

    /**
     * Allows the config system to populate the value upon load
     * @param value The value to populate with
     */
    protected void populate(JsonElement value) {
        this.value = null;
    }

    public void invalidate() {
        this.invalidated = false;
    }
}
