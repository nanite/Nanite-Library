package dev.nanite.library.core.config.values;

import de.marhali.json5.Json5Primitive;
import dev.nanite.library.core.config.IConfigParent;

public class LongConfigValue extends NumberConfigValue<Long> {
    public LongConfigValue(IConfigParent parent, String key, long defaultValue) {
        super(parent, key, defaultValue);
    }

    @Override
    public LongConfigValue comments(String... comments) {
        return (LongConfigValue) super.comments(comments);
    }

    @Override
    public LongConfigValue min(Long min) {
        return (LongConfigValue) super.min(min);
    }

    @Override
    public LongConfigValue max(Long max) {
        return (LongConfigValue) super.max(max);
    }

    @Override
    protected Long readNumber(Json5Primitive primitive) {
        return primitive.getAsLong();
    }
}
