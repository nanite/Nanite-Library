package dev.nanite.library.core.config.values;

import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Primitive;
import dev.nanite.library.core.config.ConfigValue;
import dev.nanite.library.core.config.IConfigParent;
import dev.nanite.library.utils.RegistryReference;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Consumer;

/**
 * A config value for a single registry reference (e.g., item, block, fluid).
 * Supports both direct registry entries ("minecraft:stone") and tags ("#minecraft:logs").
 * 
 * <p>Internally uses Minecraft's {@link net.minecraft.resources.Identifier} for direct entries
 * and {@link net.minecraft.tags.TagKey} for tag references.
 * 
 * @param <T> The registry type
 */
public class RegistryReferenceConfigValue<T> extends ConfigValue<RegistryReference<T>> {
    private final ResourceKey<? extends Registry<T>> registryKey;

    public RegistryReferenceConfigValue(
            IConfigParent parent, 
            String key, 
            RegistryReference<T> defaultValue,
            ResourceKey<? extends Registry<T>> registryKey
    ) {
        super(parent, key, defaultValue);
        this.registryKey = registryKey;
    }

    @Override
    public RegistryReferenceConfigValue<T> comments(String... comments) {
        return (RegistryReferenceConfigValue<T>) super.comments(comments);
    }

    @Override
    public boolean isInvalid(RegistryReference<T> value, Consumer<String> errorCollector) {
        if (value == null) {
            errorCollector.accept("Registry reference cannot be null");
            return true;
        }
        return false;
    }

    @Override
    public RegistryReference<T> deserialize(Json5Element element) {
        if (!(element instanceof Json5Primitive primitive)) {
            throw new IllegalArgumentException("Expected string for RegistryReferenceConfigValue");
        }
        return RegistryReference.parse(primitive.getAsString(), registryKey);
    }

    @Override
    public Json5Element serialize() {
        return Json5Primitive.fromString(get().toString());
    }
}
