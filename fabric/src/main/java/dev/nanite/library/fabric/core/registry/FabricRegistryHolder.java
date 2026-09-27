package dev.nanite.library.fabric.core.registry;

import dev.nanite.library.core.registry.RegistryHolder;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

import java.util.function.Supplier;

public class FabricRegistryHolder<R, T extends R> implements RegistryHolder<R, T> {
    private final Identifier identifier;
    private final Supplier<T> factory;
    private T value;

    public FabricRegistryHolder(Identifier identifier, Supplier<T> factory) {
        this.identifier = identifier;
        this.factory = factory;
    }

    void bind(Registry<R> registry) {
        this.value = Registry.register(registry, this.identifier, this.factory.get());
    }

    @Override
    public T get() {
        if (this.value == null) {
            throw new IllegalStateException("Registry entry " + this.identifier + " was accessed before its registry was initialized");
        }

        return this.value;
    }

    @Override
    public Identifier identifier() {
        return identifier;
    }
}
