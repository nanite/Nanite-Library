package dev.nanite.library.neo.core.registry;

import dev.nanite.library.core.registry.RegistryHolder;
import net.minecraft.resources.Identifier;

import java.util.function.Supplier;

public class NeoRegistryHolder<T> implements RegistryHolder<T> {
    private final Identifier id;
    private final Supplier<T> value;

    public NeoRegistryHolder(Identifier id, Supplier<T> value) {
        this.value = value;
        this.id = id;
    }

    @Override
    public T get() {
        return value.get();
    }

    @Override
    public Identifier identifier() {
        return id;
    }
}
