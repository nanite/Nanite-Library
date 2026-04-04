package dev.nanite.library.core.config.values;

import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Primitive;
import dev.nanite.library.core.config.ConfigValue;
import dev.nanite.library.core.config.IConfigParent;

public class StringConfigValue extends ConfigValue<String> {
    public StringConfigValue(IConfigParent parent, String key, String defaultValue) {
        super(parent, key, defaultValue);
    }

    @Override
    public String deserialize(Json5Element element) {
        if (element instanceof Json5Primitive primitive) {
            return primitive.getAsString();
        }
        throw new IllegalArgumentException("Expected string for StringConfigValue");
    }

    @Override
    public Json5Element serialize() {
        return Json5Primitive.fromString(get());
    }
}
