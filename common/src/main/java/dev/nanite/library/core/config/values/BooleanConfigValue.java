package dev.nanite.library.core.config.values;

import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Primitive;
import dev.nanite.library.core.config.ConfigValue;
import dev.nanite.library.core.config.IConfigParent;

import java.util.function.Consumer;

public class BooleanConfigValue extends ConfigValue<Boolean> {
    public BooleanConfigValue(IConfigParent parent, String key, boolean defaultValue) {
        super(parent, key, defaultValue);
    }

    @Override
    public BooleanConfigValue comments(String... comments) {
        return (BooleanConfigValue) super.comments(comments);
    }

    @Override
    public boolean isValid(Boolean value, Consumer<String> errorCollector) {
        if (value == null) {
            errorCollector.accept("Value cannot be null");
            return false;
        }
        return true;
    }

    @Override
    public Boolean deserialize(Json5Element element) {
        if (element instanceof Json5Primitive primitive) {
            return primitive.getAsBoolean();
        }
        throw new IllegalArgumentException("Expected boolean for BooleanConfigValue");
    }

    @Override
    public Json5Element serialize() {
        return Json5Primitive.fromBoolean(get());
    }
}
