package dev.nanite.library.core.registry;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public interface RegistryHolder<T> extends Supplier<T> {
    @Override
    T get();

    ResourceLocation identifier();
}
