package dev.nanite.library.core.config.values;

import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Primitive;
import dev.nanite.library.core.config.ConfigValue;
import dev.nanite.library.core.config.IConfigParent;

public class FloatConfigValue extends ConfigValue<Float> {
    public FloatConfigValue(IConfigParent parent, String key, float defaultValue) {
        super(parent, key, defaultValue);
    }

    @Override
    public Float deserialize(Json5Element element) {
        if (element instanceof Json5Primitive primitive) {
            return primitive.getAsFloat();
        }
        throw new IllegalArgumentException("Expected number for FloatConfigValue");
    }

    @Override
    public Json5Element serialize() {
        return Json5Primitive.fromNumber(get());
    }
}
