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

    default TagKey<R> createTagKey(Registry<R> registry) {
        return TagKey.create(registry.key(), identifier());
    }

    default TagKey<R> createTagKey(ResourceKey<? extends Registry<R>> registryKey) {
        return TagKey.create(registryKey, identifier());
    }

    default ResourceKey<T> createResourceKey(ResourceKey<? extends Registry<T>> registryKey) {
        return ResourceKey.create(registryKey, identifier());
    }

    default ResourceKey<T> createResourceKey(Registry<T> registry) {
        return ResourceKey.create(registry.key(), identifier());
    }
}
