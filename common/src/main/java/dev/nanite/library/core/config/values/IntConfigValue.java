package dev.nanite.library.core.config.values;

import de.marhali.json5.Json5Primitive;
import dev.nanite.library.core.config.IConfigParent;

public class IntConfigValue extends NumberConfigValue<Integer> {
    public IntConfigValue(IConfigParent parent, String key, int defaultValue) {
        super(parent, key, defaultValue);
    }

    @Override
    protected Integer readNumber(Json5Primitive primitive) {
        return primitive.getAsInt();
    }
}
