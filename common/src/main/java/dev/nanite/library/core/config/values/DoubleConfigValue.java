package dev.nanite.library.core.config.values;

import de.marhali.json5.Json5Primitive;
import dev.nanite.library.core.config.IConfigParent;

public class DoubleConfigValue extends NumberConfigValue<Double> {
    public DoubleConfigValue(IConfigParent parent, String key, double defaultValue) {
        super(parent, key, defaultValue);
    }

    @Override
    public DoubleConfigValue comments(String... comments) {
        return (DoubleConfigValue) super.comments(comments);
    }

    @Override
    public DoubleConfigValue min(Double min) {
        return (DoubleConfigValue) super.min(min);
    }

    @Override
    public DoubleConfigValue max(Double max) {
        return (DoubleConfigValue) super.max(max);
    }

    @Override
    protected Double readNumber(Json5Primitive primitive) {
        return primitive.getAsDouble();
    }
}
