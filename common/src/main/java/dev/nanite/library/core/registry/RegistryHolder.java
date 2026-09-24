package dev.nanite.library.core.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

import java.util.function.Supplier;

public interface RegistryHolder<R, T extends R> extends Supplier<T> {
    @Override
    T get();

    Identifier identifier();

    default TagKey<T> createKey(ResourceKey<Registry<T>> registryKey) {
        return TagKey.create(registryKey, identifier());
    }
}
