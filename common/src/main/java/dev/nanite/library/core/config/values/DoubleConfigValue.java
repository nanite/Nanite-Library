package dev.nanite.library.core.config.values;

import de.marhali.json5.Json5Primitive;
import dev.nanite.library.core.config.IConfigParent;

public class DoubleConfigValue extends NumberConfigValue<Double> {
    public DoubleConfigValue(IConfigParent parent, String key, double defaultValue) {
        super(parent, key, defaultValue);
    }

    @Override
    protected Double readNumber(Json5Primitive primitive) {
        return primitive.getAsDouble();
    }
}
