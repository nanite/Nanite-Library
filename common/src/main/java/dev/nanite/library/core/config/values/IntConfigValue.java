package dev.nanite.library.core.config.values;

import de.marhali.json5.Json5Primitive;
import dev.nanite.library.core.config.IConfigParent;

public class IntConfigValue extends NumberConfigValue<Integer> {
    public IntConfigValue(IConfigParent parent, String key, int defaultValue) {
        super(parent, key, defaultValue);
    }

    @Override
    public IntConfigValue comments(String... comments) {
        return (IntConfigValue) super.comments(comments);
    }

    @Override
    public IntConfigValue min(Integer min) {
        return (IntConfigValue) super.min(min);
    }

    @Override
    public IntConfigValue max(Integer max) {
        return (IntConfigValue) super.max(max);
    }

    @Override
    protected Integer readNumber(Json5Primitive primitive) {
        return primitive.getAsInt();
    }
}
