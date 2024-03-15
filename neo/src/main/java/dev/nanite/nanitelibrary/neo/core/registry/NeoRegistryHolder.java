package dev.nanite.nanitelibrary.neo.core.registry;

import dev.nanite.nanitelibrary.core.registry.RegistryHolder;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class NeoRegistryHolder<T> implements RegistryHolder<T> {
    private final ResourceLocation id;
    private final Supplier<T> value;

    public NeoRegistryHolder(ResourceLocation id, Supplier<T> value) {
        this.value = value;
        this.id = id;
    }

    @Override
    public T get() {
        return value.get();
    }

    @Override
    public ResourceLocation identifier() {
        return id;
    }
}
