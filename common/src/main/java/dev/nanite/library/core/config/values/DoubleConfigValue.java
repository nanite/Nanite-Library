package dev.nanite.library.core.config.values;

import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Primitive;
import dev.nanite.library.core.config.ConfigValue;
import dev.nanite.library.core.config.IConfigParent;

public class DoubleConfigValue extends ConfigValue<Double> {
    public DoubleConfigValue(IConfigParent parent, String key, double defaultValue) {
        super(parent, key, defaultValue);
    }

    @Override
    public Double deserialize(Json5Element element) {
        if (element instanceof Json5Primitive primitive) {
            return primitive.getAsDouble();
        }
        throw new IllegalArgumentException("Expected number for DoubleConfigValue");
    }

    @Override
    public Json5Element serialize() {
        return Json5Primitive.fromNumber(get());
    }
}
