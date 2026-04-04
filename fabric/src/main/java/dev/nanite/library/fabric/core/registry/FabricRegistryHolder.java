package dev.nanite.library.fabric.core.registry;

import dev.nanite.library.core.registry.RegistryHolder;
import net.minecraft.resources.Identifier;

public class FabricRegistryHolder<T> implements RegistryHolder<T> {
    private final Identifier identifier;
    private final T value;

    public FabricRegistryHolder(Identifier identifier, T value) {
        this.identifier = identifier;
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public Identifier identifier() {
        return identifier;
    }
}
