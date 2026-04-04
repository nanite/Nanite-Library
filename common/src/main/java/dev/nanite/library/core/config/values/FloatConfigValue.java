package dev.nanite.library.core.config.values;

import de.marhali.json5.Json5Primitive;
import dev.nanite.library.core.config.IConfigParent;

public class FloatConfigValue extends NumberConfigValue<Float> {
    public FloatConfigValue(IConfigParent parent, String key, float defaultValue) {
        super(parent, key, defaultValue);
    }

    @Override
    protected Float readNumber(Json5Primitive primitive) {
        return primitive.getAsFloat();
    }
}
