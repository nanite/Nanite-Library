package dev.nanite.library.core.config.values;

import de.marhali.json5.Json5Primitive;
import dev.nanite.library.core.config.IConfigParent;

public class FloatConfigValue extends NumberConfigValue<Float> {
    public FloatConfigValue(IConfigParent parent, String key, float defaultValue) {
        super(parent, key, defaultValue);
    }

    @Override
    public FloatConfigValue comments(String... comments) {
        return (FloatConfigValue) super.comments(comments);
    }

    @Override
    public FloatConfigValue min(Float min) {
        return (FloatConfigValue) super.min(min);
    }

    @Override
    public FloatConfigValue max(Float max) {
        return (FloatConfigValue) super.max(max);
    }

    @Override
    protected Float readNumber(Json5Primitive primitive) {
        return primitive.getAsFloat();
    }
}
