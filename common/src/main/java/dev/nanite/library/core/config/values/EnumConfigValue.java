package dev.nanite.library.core.config.values;

import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Primitive;
import dev.nanite.library.core.config.ConfigValue;
import dev.nanite.library.core.config.IConfigParent;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EnumConfigValue<E extends Enum<E>> extends ConfigValue<E> {
    private final Class<E> enumClass;
    private boolean caseInsensitive = false;

    public EnumConfigValue(IConfigParent parent, String key, E defaultValue) {
        super(parent, key, defaultValue);
        @SuppressWarnings("unchecked")
        Class<E> enumType = (Class<E>) defaultValue.getClass();
        this.enumClass = enumType;
    }

    public EnumConfigValue<E> caseInsensitive() {
        this.caseInsensitive = true;
        return this;
    }

    @Override
    public EnumConfigValue<E> comments(String... comments) {
        return (EnumConfigValue<E>) super.comments(comments);
    }

    @Override
    public boolean isInvalid(E value, Consumer<String> errorCollector) {
        if (value == null) {
            errorCollector.accept("Value cannot be null");
            return true;
        }

        return false;
    }

    @Override
    public E deserialize(Json5Element element) {
        if (!(element instanceof Json5Primitive primitive)) {
            throw new IllegalArgumentException("Expected string for EnumConfigValue");
        }

        String enumName = primitive.getAsString();
        
        try {
            if (caseInsensitive) {
                // Case-insensitive lookup
                for (E constant : enumClass.getEnumConstants()) {
                    if (constant.name().equalsIgnoreCase(enumName)) {
                        return constant;
                    }
                }
                throw new IllegalArgumentException(
                    "Invalid enum value '" + enumName + "'. Valid values (case-insensitive): " + 
                    Arrays.stream(enumClass.getEnumConstants())
                        .map(Enum::name)
                        .collect(Collectors.joining(", "))
                );
            } else {
                return Enum.valueOf(enumClass, enumName);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Invalid enum value '" + enumName + "'. Valid values: " + 
                Arrays.stream(enumClass.getEnumConstants())
                    .map(Enum::name)
                    .collect(Collectors.joining(", ")),
                e
            );
        }
    }

    @Override
    public Json5Element serialize() {
        return Json5Primitive.fromString(get().name());
    }

    @Override
    public Json5Element serializeWithComments() {
        Json5Element element = super.serializeWithComments();
        
        // Add valid values to comment
        String validValues = Arrays.stream(enumClass.getEnumConstants())
            .map(Enum::name)
            .collect(Collectors.joining(", "));
        
        String existingComment = element.hasComment() ? element.getComment() + "\n" : "";
        element.setComment(existingComment + "Valid values: " + validValues);
        
        return element;
    }
}
