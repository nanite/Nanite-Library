package dev.nanite.library.core.config.values;

import de.marhali.json5.Json5Primitive;
import dev.nanite.library.core.config.IConfigParent;

public class LongConfigValue extends NumberConfigValue<Long> {
    public LongConfigValue(IConfigParent parent, String key, long defaultValue) {
        super(parent, key, defaultValue);
    }

    @Override
    protected Long readNumber(Json5Primitive primitive) {
        return primitive.getAsLong();
    }
}
