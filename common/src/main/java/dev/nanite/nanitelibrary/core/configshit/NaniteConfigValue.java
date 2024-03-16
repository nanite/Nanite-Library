package dev.nanite.nanitelibrary.core.configshit;

import com.electronwill.nightconfig.core.CommentedConfig;

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
    protected void populate(T value) {
        this.value = value;
    }
    protected void populate(CommentedConfig.Entry configEntry) {
        this.value = configEntry.getValue();
    }

    public void invalidate() {
        this.invalidated = true;
    }
}
