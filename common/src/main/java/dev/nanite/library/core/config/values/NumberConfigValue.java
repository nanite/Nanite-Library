package dev.nanite.library.core.config.values;

import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Primitive;
import dev.nanite.library.core.config.ConfigValue;
import dev.nanite.library.core.config.IConfigParent;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public abstract class NumberConfigValue<T extends Number & Comparable<T>> extends ConfigValue<T> {
    @Nullable
    private T minValue;

    @Nullable
    private T maxValue;

    public NumberConfigValue(IConfigParent parent, String key, T defaultValue) {
        super(parent, key, defaultValue);
    }

    protected abstract T readNumber(Json5Primitive primitive);

    @Override
    public NumberConfigValue<T> comments(String... comments) {
        return (NumberConfigValue<T>) super.comments(comments);
    }

    public NumberConfigValue<T> min(T min) {
        this.minValue = min;
        return this;
    }

    public NumberConfigValue<T> max(T max) {
        this.maxValue = max;
        return this;
    }

    @Override
    public boolean isValid(T value, Consumer<String> errorCollector) {
        if (value == null) {
            errorCollector.accept("Value cannot be null");
            return false;
        }
        
        boolean valid = true;
        if (minValue != null && value.compareTo(minValue) < 0) {
            errorCollector.accept("Value " + value + " is below minimum " + minValue);
            valid = false;
        }
        if (maxValue != null && value.compareTo(maxValue) > 0) {
            errorCollector.accept("Value " + value + " exceeds maximum " + maxValue);
            valid = false;
        }
        return valid;
    }

    @Override
    public T deserialize(Json5Element element) {
        if (element instanceof Json5Primitive primitive) {
            return readNumber(primitive);
        }
        throw new IllegalArgumentException("Expected number for " + getClass().getSimpleName());
    }

    @Override
    public Json5Element serializeWithComments() {
        Json5Element json5Element = super.serializeWithComments();
        String comment = json5Element.hasComment() ? json5Element.getComment() + "\n" : "";
        if (minValue != null && maxValue != null) {
            comment += "Value must be between " + minValue + " and " + maxValue;
        } else if (minValue != null) {
            comment += "Value must be at least " + minValue;
        } else if (maxValue != null) {
            comment += "Value must be at most " + maxValue;
        }
        if (!comment.isEmpty()) {
            json5Element.setComment(comment);
        }
        return json5Element;
    }

    @Override
    public Json5Element serialize() {
        return Json5Primitive.fromNumber(get());
    }
}
