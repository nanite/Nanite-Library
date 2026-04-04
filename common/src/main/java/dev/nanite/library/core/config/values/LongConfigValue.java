package dev.nanite.library.core.config.values;

import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Primitive;
import dev.nanite.library.core.config.ConfigValue;
import dev.nanite.library.core.config.IConfigParent;

public class LongConfigValue extends ConfigValue<Long> {
    public LongConfigValue(IConfigParent parent, String key, long defaultValue) {
        super(parent, key, defaultValue);
    }

    @Override
    public Long deserialize(Json5Element element) {
        if (element instanceof Json5Primitive primitive) {
            return primitive.getAsLong();
        }
        throw new IllegalArgumentException("Expected number for LongConfigValue");
    }

    @Override
    public Json5Element serialize() {
        return Json5Primitive.fromNumber(get());
    }
}
