package dev.nanite.nanitelibrary.fabric.core.registry;

import dev.nanite.nanitelibrary.core.registry.RegistryHolder;
import net.minecraft.resources.ResourceLocation;

public class FabricRegistryHolder<T> implements RegistryHolder<T> {
    private final ResourceLocation identifier;
    private final T value;

    public FabricRegistryHolder(ResourceLocation identifier, T value) {
        this.identifier = identifier;
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public ResourceLocation identifier() {
        return identifier;
    }
}
