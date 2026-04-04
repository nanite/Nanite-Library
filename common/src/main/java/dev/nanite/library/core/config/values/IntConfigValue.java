package dev.nanite.library.core.config.values;

import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Primitive;
import dev.nanite.library.core.config.ConfigValue;
import dev.nanite.library.core.config.IConfigParent;

public class IntConfigValue extends ConfigValue<Integer> {
    public IntConfigValue(IConfigParent parent, String key, int defaultValue) {
        super(parent, key, defaultValue);
    }

    @Override
    public Integer deserialize(Json5Element element) {
        if (element instanceof Json5Primitive primitive) {
            return primitive.getAsInt();
        }
        throw new IllegalArgumentException("Expected number for IntConfigValue");
    }

    @Override
    public Json5Element serialize() {
        return Json5Primitive.fromNumber(get());
    }
}
