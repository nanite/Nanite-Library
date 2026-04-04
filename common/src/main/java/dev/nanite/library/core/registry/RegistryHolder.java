package dev.nanite.library.core.registry;

import net.minecraft.resources.Identifier;

import java.util.function.Supplier;

public interface RegistryHolder<R, T extends R> extends Supplier<T> {
    @Override
    T get();

    Identifier identifier();
}
