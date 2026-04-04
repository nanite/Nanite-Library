package dev.nanite.library.core.config.values;

import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Primitive;
import dev.nanite.library.core.config.ConfigValue;
import dev.nanite.library.core.config.IConfigParent;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;
import java.util.regex.Pattern;

public class StringConfigValue extends ConfigValue<String> {
    @Nullable
    private Pattern validationPattern;

    public StringConfigValue(IConfigParent parent, String key, String defaultValue) {
        super(parent, key, defaultValue);
    }

    public StringConfigValue pattern(String regex) {
        this.validationPattern = Pattern.compile(regex);
        return this;
    }

    @Override
    public StringConfigValue comments(String... comments) {
        return (StringConfigValue) super.comments(comments);
    }

    @Override
    public boolean isInvalid(String value, Consumer<String> errorCollector) {
        if (value == null) {
            errorCollector.accept("Value cannot be null");
            return true;
        }
        if (validationPattern != null && !validationPattern.matcher(value).matches()) {
            errorCollector.accept("Value '" + value + "' does not match required pattern");
            return true;
        }
        return false;
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
